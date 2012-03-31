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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.intel.openAttestation.bean.AttestationResponseFault;
import com.intel.openAttestation.bean.ReqAsyncStatusBean;
import com.intel.openAttestation.hibernate.domain.AttestRequest;
import com.intel.openAttestation.util.AttestUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class ListAsyncRequest  extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    List<AttestRequest> attestRequests = new ArrayList<AttestRequest>();
		WebResource resource = AttestUtil.getClient(AttestUtil.getAttestationWebServicesUrl());
		ClientResponse allRequestsResponse = resource.path("/AllRequests").type("application/json").
				header("Auth_blob", "").accept("application/json").get(ClientResponse.class);
		if (allRequestsResponse.getStatus()== ClientResponse.Status.OK.getStatusCode()){
			attestRequests =  allRequestsResponse.getEntity(new GenericType<List<AttestRequest>>(){});
			 if (attestRequests.size()>0){
				
				 req.setAttribute("all_requests",getReqAsyncStatusBean(attestRequests));
			 }
			 else{
				 req.setAttribute("message", "no requests");
			 }
		}
		else
			req.setAttribute("message", allRequestsResponse.getEntity(AttestationResponseFault.class));
		
		req.getRequestDispatcher("view_async_request.jsp").forward(req, resp);

	}

	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	public List<ReqAsyncStatusBean> getReqAsyncStatusBean(List<AttestRequest> reqs){
		 List<ReqAsyncStatusBean> requests = new ArrayList<ReqAsyncStatusBean>();
	     	HashMap<String,ReqAsyncStatusBean> map = new HashMap<String,ReqAsyncStatusBean>();
     		for (AttestRequest req: reqs){
     			if (!map.containsKey(req.getRequestId())){
     				ReqAsyncStatusBean request = new ReqAsyncStatusBean();
     				request.setRequestId(req.getRequestId());
     				request.setHosts(req.getHostName());
     				request.setPCRMask(req.getPCRMask());
     				request.setRequestTime(req.getRequestTime());
     				request.setCount(req.getCount());
     				request.setResult(true);
     				if (req.getResult()==null)
     					request.setResult(false);
     				map.put(req.getRequestId(), request);
     			}
     			else{
     				ReqAsyncStatusBean request =map.get(req.getRequestId());
     				if (req.getResult()==null)
     					request.setResult(false);
     			    String newhosts= request.getHosts() + ',' +req.getHostName();
     			    request.setHosts(newhosts);
     			}
     		}
	     		
     		Iterator<Entry<String, ReqAsyncStatusBean>>  iterator = map.entrySet().iterator();
     		while (iterator.hasNext()){
     			ReqAsyncStatusBean request = new ReqAsyncStatusBean();
     			Map.Entry<String, ReqAsyncStatusBean> entry = iterator.next();
     			request = (ReqAsyncStatusBean) entry.getValue();
     			if (!request.getResult())
     				request.setStatus("In Progress");
     			else
     				request.setStatus("Completed");
     			requests.add(request);
     		}
     		return requests;
	}
}
