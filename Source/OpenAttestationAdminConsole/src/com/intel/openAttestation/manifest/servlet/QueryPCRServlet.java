/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.intel.openAttestation.manifest.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intel.openAttestation.manifest.hibernate.domain.PCRManifest;
import com.intel.openAttestation.manifest.util.AttestUtil;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class QueryPCRServlet  extends HttpServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String index = req.getParameter("pcrIndex");
	    String pcrNumber = req.getParameter("pcrNumber");
	    String pcrDesc = req.getParameter("pcrDesc");
	    req.setAttribute("pcrIndex", index);
	    req.setAttribute("pcrNumber", pcrNumber);
	    req.setAttribute("pcrDesc", pcrDesc);
	    List<PCRManifest> pcrs = null;
	    
	    try{
		    WebResource webResource = AttestUtil.getClient(AttestUtil.getManifestWebServiceURL());
		    if (index.length() != 0){
		    	//query by index
			    pcrs = webResource.queryParam("index", index).accept("application/json")
		                .get(new GenericType<List<PCRManifest>>(){});

		    }else if ( pcrNumber.length() != 0 && pcrDesc.length() == 0){
		    	//query by Number
			    pcrs = webResource.queryParam("PCRNumber", pcrNumber).accept("application/json")
		                .get(new GenericType<List<PCRManifest>>(){});
		    }else if ( pcrNumber.length() == 0 && pcrDesc.length() != 0){
		    	//query by Desc
			    pcrs = webResource.queryParam("PCRDesc", pcrDesc).accept("application/json")
		                .get(new GenericType<List<PCRManifest>>(){});
		    }else if( pcrNumber.length() != 0 && pcrDesc.length() != 0){
		    	//query by Number + Desc
			    pcrs = webResource.queryParam("PCRNumber", pcrNumber)
			    		.queryParam("PCRDesc", pcrDesc).accept("application/json")
		                .get(new GenericType<List<PCRManifest>>(){});
		    }
		    req.setAttribute("pcrs", pcrs);
		    if (pcrs == null || pcrs.size() < 1)
		    	req.setAttribute("query_message", "No such entry.");
	    }catch(Exception e){
	    	req.setAttribute("query_message", "Error: " + e.getMessage());
	    }
	    req.getRequestDispatcher("query_pcrs.jsp").forward(req, resp);
	}
		    


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	} 

}
