/*
 * (copyright) 2012 United States Government, as represented by the 
 * Secretary of Defense.  All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright 
 * notice, this list of conditions and the following disclaimer. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the name of the U.S. Government nor the names of its 
 * contributors may be used to endorse or promote products derived from 
 * this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS 
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY 
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.niarl.hisAppraiser.util;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * This class is used to store and retrieve information about which alerts  
 * to generate
 * @author syelama
 * @version Crossbow
 *
 */
public class AlertConfiguration {
	private static Logger logger = Logger.getLogger(AlertConfiguration.class);
	boolean allAlerts;
	boolean[] pcrAlerts = new boolean[24];
	boolean signatureAlerts;

	/**
	 * This constructor parses the string which configures the alerts.
	 * @param property The comma separated string with alert configuration.
	 */
	public AlertConfiguration(String property) {
		property = property.trim();

		allAlerts = false;
		for (int i = 0; i < pcrAlerts.length; i++) {
			pcrAlerts[i] = false;
		}
		signatureAlerts = false;

		if (property == null || property.length() < 1) {
			allAlerts = true;
			for (int i = 0; i < pcrAlerts.length; i++) {
				pcrAlerts[i] = true;
			}
			signatureAlerts = true;
		} else {
			StringTokenizer stringTokenizer = new StringTokenizer(property, ",");
			while (stringTokenizer.hasMoreElements()) {
				String string = (String) stringTokenizer.nextElement();

				int pcr = -1;
				try {
					pcr = Integer.parseInt(string);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if ("signature".equalsIgnoreCase(string)) {
					if (signatureAlerts) {
						logger.warn("Duplicate entry in alert configuration: " + string);
					} else {
						signatureAlerts = true;
					}
				} else if (pcr >= 0 && pcr <= 23) {
					if (pcrAlerts[pcr]) {
						logger.warn("Duplicate entry in alert configuration: " + string);
					} else {
						pcrAlerts[pcr] = true;
					}
				} else {
					logger.warn("Unknown entry in alert configuration: " + string);
				}
			}
		}
		//			printSummary();
	}

	/**
	 * Whether or not to ignore this alert configuration and generate all 
	 * alerts.
	 * @return Whether or not to generate all alerts regardless of configuration.
	 */
	public boolean getAllAlerts() {
		return allAlerts;
	}

	/**
	 * Determines whether to generate alerts for a PCR based on PCR number. 
	 * @param i PCR number
	 * @return True if alerts should be generated for this PCR.    
	 */
	public boolean getPcrAlerts(int i) {
		return pcrAlerts[i];
	}

	/**
	 * Determines if signature alerts should be generated.             
	 * @return True if signature alerts should be generated.           
	 */
	public boolean getSignatureAlerts() {
		return signatureAlerts;
	}

	/**
	 * Prints an alert generation summary using the logger created for this 
	 * class.
	 */
	public void printSummary() {
		logger.info("-----------------------------------------------------------------------------");
		logger.info("Printing HIS web services alert configuration summary:");
		logger.info("-----------------------------------------------------------------------------");
		boolean noAlertConfiguration = true;
		if (getAllAlerts()) {
			logger.info("Alerts will be generated for all errors.");
			noAlertConfiguration = false;
		} else {
			if (getSignatureAlerts()) {
				logger.info("Alerts will be generated for signature errors.");
				noAlertConfiguration = false;
			}
			for (int i = 0; i < pcrAlerts.length; i++) {
				if (getPcrAlerts(i)) {
					logger.info("Alerts will be generated for PCR number:" + i);
					noAlertConfiguration = false;
				}
			}
		}
		if (noAlertConfiguration) {
			logger.warn("WARNING: No alert configuration found. No alerts will be generated.");
		}
		logger.info("-----------------------------------------------------------------------------");
	}
}
