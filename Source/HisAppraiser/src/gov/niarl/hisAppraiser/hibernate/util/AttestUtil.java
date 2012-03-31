/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package gov.niarl.hisAppraiser.hibernate.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import gov.niarl.hisAppraiser.Constants;
import gov.niarl.hisAppraiser.integrityReport.HisReportValidator;
import gov.niarl.hisAppraiser.util.AlertConfiguration;
import gov.niarl.hisAppraiser.util.HisUtil;

public class AttestUtil {
	
	private static String manifestWSURL ="";
	private static String trustStorePath = "";
	
	private static String PROPERTIES_NAME = "OpenAttestation.properties";
	private static Properties attestationProperties = new Properties();

	public static void  loadProp(){
		FileInputStream attestationPropertyFile = null;
	       try {
	    	   String path = AttestUtil.class.getClassLoader().getResource("/").getPath();
	    	   attestationPropertyFile = new FileInputStream(path +PROPERTIES_NAME);
	    	   attestationProperties.load(attestationPropertyFile);
	    	   String manifesturl = attestationProperties.getProperty("ManifestWebServicesUrl");
	    	   String truststore = attestationProperties.getProperty("TrustStore");
	    	   manifestWSURL = manifesturl == null ? "":manifesturl;
	    	   trustStorePath =truststore == null? "":truststore;
			} 
	       catch (IOException e) {
               e.printStackTrace();
	       }
	       finally{
	    	   try {
	    		   if (attestationPropertyFile != null)
	    			   attestationPropertyFile.close();
				
				} catch (IOException e) {
					 e.printStackTrace();
				}
	       }
	       
	}
	
	public static String getManifestWebServicesUrl(){
		AttestUtil.loadProp();
		return manifestWSURL;
	}
	
	public static String getTrustStore(){
		return trustStorePath;
	}
	
	public static WebResource getClient(String url){
		if (url.startsWith("https")){
			System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		}
		ClientConfig config = new DefaultClientConfig();
	    WebResource resource = Client.create(config).resource(url);
		return resource;
	}
	
	
	public static synchronized String generateRequestId(String label){
		byte[] nonce = HisUtil.generateSecureRandom(16);
		return label+ HisUtil.hexString(nonce);
	}
	
	
	/**
	 * get validated pcrs from HIS.properties
	 * @return
	 */
	public static ArrayList<Integer> generatePcrValidatedPositions(){
		List<Integer> pcrs = new ArrayList<Integer>();
		AlertConfiguration alertConfiguration = Constants.ALERT_CONFIGURATION;
		for (int i = 0; i < 24; i++) {
			if (alertConfiguration.getPcrAlerts(i)) {
				pcrs.add(i);
			}
		}
		return (ArrayList<Integer>) pcrs;
	}
	
	public static ArrayList<Integer> generatePcrSelectedPositions(String PCRMask){
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		PCRMask = PCRMask.length() %2 !=0 ? "0" + PCRMask : PCRMask;
		byte[] bytes= HisUtil.unHexString(PCRMask);
		for (int i = 0; i < bytes.length; i++) {
			for (Integer integer : AttestUtil.getSelectedPCR(bytes[bytes.length -i-1])) {
				arrayList.add(integer + (i * 8));
			}
		}
		return arrayList;
	}
	
	
	/**
	 * Get the sorted selected positions in a byte with the right most
	 * position as zero.
	 * @param input Byte to be evaluated
	 * @return Array of integer positions.
	 */
	public static SortedSet<Integer> getSelectedPCR(byte input) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		byte mask = 0x01;
		for (int i = 0; i <= 7; i++) {
			int value = (input >>> i) & mask;
			if (value == 1) {
				arrayList.add(i);
			}
		}
		Collections.sort(arrayList);
		return Collections.unmodifiableSortedSet(new TreeSet<Integer>(arrayList));
	}

}
