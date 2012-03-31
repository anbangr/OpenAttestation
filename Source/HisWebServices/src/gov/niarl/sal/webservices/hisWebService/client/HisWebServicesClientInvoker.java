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
package gov.niarl.sal.webservices.hisWebService.client;

import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisEnrollmentWebService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisEnrollmentWebServiceService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisPollingWebService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisPollingWebServiceService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisWebService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisWebServiceFactoryService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisWebServiceFactoryServiceService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisWebServiceService;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * Convenience class for clients to create web service objects.
 * @author syelama
 * @version Crossbow
 *
 */
public class HisWebServicesClientInvoker {
	HisWebService hisWebService;

	/**
	 * Creates a stateful HisWebService object.
	 * @param url The URL of the web application.
	 * @return A stateful HisWebService instance.
	 */
	public static HisWebService getHisWebService(String url) {
		try {
			HisWebServiceFactoryServiceService hisWebServiceFactoryServiceService = new HisWebServiceFactoryServiceService(new URL(url + "/hisWebServiceFactoryService?wsdl"), new QName("http://server.hisWebService.webservices.sal.niarl.gov/", "HisWebServiceFactoryServiceService"));
			HisWebServiceFactoryService hisWebServiceFactoryService = hisWebServiceFactoryServiceService.getHisWebServiceFactoryServicePort();
			HisWebServiceService hisWebServiceService = new HisWebServiceService(new URL(url + "/hisWebService?wsdl"), new QName("http://server.hisWebService.webservices.sal.niarl.gov/", "HisWebServiceService"));
			return hisWebServiceService.getPort(hisWebServiceFactoryService.getHisWebService(), HisWebService.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a HisEnrollmentWebService instance.
	 * @param url The URL of the web application.
	 * @return A HisEnrollmentWebService instance.
	 */
	public static HisEnrollmentWebService getHisEnrollmentWebService(String url) {
		try {
			HisEnrollmentWebServiceService hisEnrollmentWebServiceService = new HisEnrollmentWebServiceService(new URL(url + "/hisEnrollmentWebService?wsdl"), new QName("http://server.hisWebService.webservices.sal.niarl.gov/", "HisEnrollmentWebServiceService"));
			return hisEnrollmentWebServiceService.getHisEnrollmentWebServicePort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a HisPollingWebService instance.
	 * @param url The URL of the web application.
	 * @return A HisPollingWebService instance.
	 */
	public static HisPollingWebService getHisPollingWebService(String url) {
		try {
			HisPollingWebServiceService hisPollingWebService = new HisPollingWebServiceService(new URL(url + "/hisPollingWebService?wsdl"), new QName("http://server.hisWebService.webservices.sal.niarl.gov/", "HisPollingWebServiceService"));
			return hisPollingWebService.getHisPollingWebServicePort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
