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

import gov.niarl.hisAppraiser.Constants;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class is a central location for dealing with email functionality. 
 * @author syelama
 * @version Crossbow
 *
 */
public class Emailer {
	/**
	 * Extracts properties for mail configuration.
	 * @param completeProperties 
	 * @return A subset of properties containing mail configuration.
	 */
	public static Properties parseMailServerProperties(Properties completeProperties) {
		Properties mailServerProperties = new Properties();
		for (Entry<Object, Object> entry : completeProperties.entrySet()) {
			if (((String) entry.getKey()).trim().toLowerCase().startsWith("mail.")) {
				mailServerProperties.put(entry.getKey(), entry.getValue());
			}
		}
		return mailServerProperties;
	}

	/**
	 * Constructs an array of InternetAddress from a property containing a  
	 * comma separated list. 
	 * @param defaultAlertMessageTo Comma separated list of email "to" addresses.
	 * @return An array of InternetAddress to be used to send email.
	 */
	public static InternetAddress[] parseDefaultAlertMessageTo(String defaultAlertMessageTo) {
		InternetAddress[] internetAddresses;
		try {
			StringTokenizer stringTokenizer = new StringTokenizer(defaultAlertMessageTo, ",");
			internetAddresses = new InternetAddress[stringTokenizer.countTokens()];
			for (int i = 0; i < internetAddresses.length; i++) {
				if (stringTokenizer.hasMoreElements()){
					internetAddresses[i] = new InternetAddress((String) stringTokenizer.nextElement());
				}
			}
		} catch (AddressException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return internetAddresses;
	}

	/**
	 * Send a general purpose email set from text properties. 
	 */
	public static void sendDefaultAlertEmail() {
		Session session = Session.getDefaultInstance(Constants.MAIL_SERVER_PROPERTIES, null);
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			//"mail.from" set in the properties
			//message.setFrom(new InternetAddress(fromEmailAddr));
			for (int i = 0; i < Constants.ALERT_MESSAGE_TO.length; i++) {
				mimeMessage.addRecipient(Message.RecipientType.TO, Constants.ALERT_MESSAGE_TO[i]);
			}
			mimeMessage.setSubject(Constants.ALERT_MESSAGE_SUBJECT);
			mimeMessage.setContent(Constants.ALERT_MESSAGE_BODY, "text/html; charset=ISO-8859-1");
			Transport.send(mimeMessage);
		} catch (MessagingException ex) {
			System.err.println("Cannot send email. " + ex);
			ex.printStackTrace();
		}
	}
}
