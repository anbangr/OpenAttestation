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
package gov.niarl.sal.webservices.hisWebService.server;

import gov.niarl.hisAppraiser.Constants;
import gov.niarl.hisAppraiser.hibernate.util.HibernateUtilHis;
import gov.niarl.hisAppraiser.integrityReport.HisReportUtil;
import gov.niarl.hisAppraiser.util.HisUtil;
import gov.niarl.sal.webservices.hisWebService.server.domain.NonceSelect;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.log4j.Logger;

import com.sun.xml.ws.developer.Stateful;
import com.sun.xml.ws.developer.StatefulWebServiceManager;

/**
 * Stateful web service to request and consume integrity reports.
 * enables integrity reports to be received and validated from the client.  
 * @author syelama
 * @version Crossbow
 *
 */
@Stateful
@WebService
@Addressing
public class HisWebService {
	private static Logger logger = Logger.getLogger(HisWebService.class);
	/**
	 * Needed for stateful web services. StatefulWebServiceManager javadoc
	 */
	public static StatefulWebServiceManager<HisWebService> manager;

	String machineName;
	String userName;
	NonceSelect nonceSelect;

	/**
	 * Generates information needed by the clients to generate reports.
	 * @param machineName Name of the machine.
	 * @param userName User's ID or name.
	 * @return Information needed by the clients to submit an integrity
	 * report.
	 */
	@WebResult(name = "nonceSelect")
	public NonceSelect getNonce(@WebParam(name = "machineName") String machineName, @WebParam(name = "userName") String userName) {
		logger.debug("getNonce called with machine name:" + machineName + " userName:" + userName);
		this.machineName = machineName;
		this.userName = userName;
		nonceSelect = new NonceSelect();
		nonceSelect.setNonce(HisUtil.generateSecureRandom(20));
		nonceSelect.setSelect(HisUtil.unHexString(Constants.PCR_SELECT));
		nonceSelect.setQuote(NonceSelect.Quote.QUOTE1);
		return nonceSelect;
	}

	/**
	 * Receives and processes an integrity report.
	 * @param integrityReport XML integrity report sent by the clients.
	 */
	public void postIntegrityReport(@WebParam(name = "integrityReport") String integrityReport) {
		logger.debug("postIntegrityReport called with integrityReport:" + integrityReport);
		HibernateUtilHis.beginTransaction();
		try {

			HisReportUtil.submitReport(userName, integrityReport, nonceSelect.getNonce(), nonceSelect.getSelect(), machineName);

			HibernateUtilHis.commitTransaction();
		} catch (Exception exception) {
			HibernateUtilHis.rollbackTransaction();
			exception.printStackTrace();
			throw new RuntimeException(exception);
		} finally {
			HibernateUtilHis.closeSession();
			manager.unexport(this);
		}
	}

	/**
	 * Needed for stateful web services.
	 * @return Related StatefulWebServiceManager.
	 */
	@WebMethod(exclude = true)
	public static StatefulWebServiceManager<HisWebService> getManager() {
		return manager;
	}

	/**
	 * Needed for stateful web services.
	 * @param manager Related StatefulWebServiceManager.
	 */
	@WebMethod(exclude = true)
	public static void setManager(StatefulWebServiceManager<HisWebService> manager) {
		HisWebService.manager = manager;
	}
}
