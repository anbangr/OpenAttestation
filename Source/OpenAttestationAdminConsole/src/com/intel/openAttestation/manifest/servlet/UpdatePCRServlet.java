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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intel.openAttestation.manifest.bean.AttestationResponseFault;
import com.intel.openAttestation.manifest.hibernate.domain.PCRManifest;
import com.intel.openAttestation.manifest.util.AttestUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UpdatePCRServlet extends HttpServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//	    if (req.getSession().getAttribute("user_session") == null ){
//	    	resp.sendRedirect("pcr_manifest_login.jsp?returnPage=UpdatePCRServlet");
//		}else{
//		    User user =  (User)req.getSession().getAttribute("user_session");
			WebResource webResource = AttestUtil.getClient(AttestUtil.getManifestWebServiceURL());

			SimpleDateFormat sdf = new SimpleDateFormat(AttestUtil.getDateTimePattern());
			PCRManifest pcr = new PCRManifest();
		    pcr.setIndex(Long.valueOf(req.getParameter("index")));
		    pcr.setPCRNumber(Integer.valueOf(req.getParameter("pcrNumber")));
		    pcr.setPCRValue(req.getParameter("pcrValue"));
		    pcr.setPCRDesc(req.getParameter("pcrDesc"));
		    try{
		    	
		    	pcr.setCreateTime(sdf.parse(req.getParameter("pcrCreateTime")));
		    }catch(ParseException e){
		    	pcr.setCreateTime(new Date());
		    }
//		    if ( ! req.getParameter("pcrCreateRequestHost").equals("")){
//			    User createUser = new User();
//			    createUser.setId(Long.valueOf(req.getParameter("pcrCreateUser")));
//		    }
		    
		    ClientResponse res = webResource.type("application/json").
					accept("application/json").post(ClientResponse.class,pcr);
			if (res.getStatus() == ClientResponse.Status.ACCEPTED.getStatusCode()){
				req.setAttribute("update_pcr", pcr);
				req.setAttribute("message", "Update successfully");
			}
			else{
				AttestationResponseFault fault = res.getEntity(AttestationResponseFault.class);
				req.setAttribute("update_pcr", pcr);
				req.setAttribute("message", fault.getDetail());
			}
		    req.getRequestDispatcher("update_pcr.jsp").forward(req, resp);
		}

//	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	} 

}
