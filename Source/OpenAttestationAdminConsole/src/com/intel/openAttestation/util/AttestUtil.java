/*
Copyright (c) <YEAR>, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class AttestUtil {
	
	private static String attestationWSURL;
	private static String trustStorePath;
	private static String clientKeyStorePath;
	private static String clientTrustStorePassword;
	private static String clientKeyStorePassword;
	
	private static String PROPERTIES_NAME = "OpenAttestation.properties";
	private static Properties attestationProperties = new Properties();

	public static void setPropertiesFileName(String name){
		PROPERTIES_NAME = name;
	}
	
	public static void  loadProp(){
		FileInputStream attestationPropertyFile = null;
	       try {
	    	   String path = AttestUtil.class.getClassLoader().getResource("/").getPath();
	    	   attestationPropertyFile = new FileInputStream(path +PROPERTIES_NAME);
	    	   attestationProperties.load(attestationPropertyFile);
	    	   attestationWSURL = attestationProperties.getProperty("AttestationWebServicesUrl");
	    	   trustStorePath = attestationProperties.getProperty("TrustStore");
			   clientKeyStorePath = attestationProperties.getProperty("keystore_path");
			   clientTrustStorePassword = attestationProperties.getProperty("trust_store_password");
			   clientKeyStorePassword = attestationProperties.getProperty("key_store_password");
			   
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
	
	public static String getAttestationWebServicesUrl(){
		return attestationWSURL;
	}
	
	public static WebResource getClient(String url){
		if (url.startsWith("https")){
			System.setProperty("javax.net.ssl.trustStore", trustStorePath);
			
			//Two-Way SSL authentication support
            if (clientKeyStorePath != null && !clientKeyStorePath.isEmpty())
            {
				System.setProperty("javax.net.ssl.trustStorePassword", clientTrustStorePassword); 
				System.setProperty("javax.net.ssl.keyStore", clientKeyStorePath); 
				System.setProperty("javax.net.ssl.keyStorePassword",clientKeyStorePassword);
				System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            }
			
		}
		ClientConfig config = new DefaultClientConfig();
	    WebResource resource = Client.create(config).resource(url);
		return resource;
	}
	
}
