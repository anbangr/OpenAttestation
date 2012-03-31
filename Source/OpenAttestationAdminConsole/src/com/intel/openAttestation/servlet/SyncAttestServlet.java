/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.intel.openAttestation.servlet;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.intel.openAttestation.bean.AttestationResponseFault;
import com.intel.openAttestation.bean.ReqAttestationBean;
import com.intel.openAttestation.bean.RespSyncBean;
import com.intel.openAttestation.util.AttestUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SyncAttestServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	    ReqAttestationBean reqAttest = new ReqAttestationBean();
	    List<String> hosts= new LinkedList<String> ();
	    String[] host= null;
	    host=req.getParameterValues("host");
	    if (host!=null && host.length > 0){
	    	for (int i=0; i<host.length; i++){
	    		hosts.add(host[i]);
	    	}
	    }
	    
	    reqAttest.setPCRmask(req.getParameter("PCRmask"));
	    reqAttest.setCount(Long.parseLong(String.valueOf(hosts.size())));
	    reqAttest.setHosts(hosts);
	    String timeThreshold = req.getParameter("timeThreshold");
	    if (timeThreshold != null && timeThreshold.length() != 0)
	    	reqAttest.setTimeThreshold(Long.parseLong(timeThreshold));
	    WebResource resource =AttestUtil.getClient(AttestUtil.getAttestationWebServicesUrl());
    	ClientResponse res = resource.path("/PollHosts").header("Auth_blob", "").type("application/json").
    	              accept("application/json").post(ClientResponse.class,reqAttest);
    	if (res.getStatus() == ClientResponse.Status.OK.getStatusCode()){
	    	req.setAttribute("syncBean", res.getEntity(RespSyncBean.class));
	    	req.setAttribute("PCRmask", req.getParameter("PCRmask"));
	    	req.getRequestDispatcher("sync_request_result.jsp").forward(req, resp);
    	}
    	else{
    		String fault=res.getEntity((AttestationResponseFault.class)).getDetail();
    		req.setAttribute("sync_message", fault);
    		req.getRequestDispatcher("sync_request.jsp").forward(req, resp);
    	}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
