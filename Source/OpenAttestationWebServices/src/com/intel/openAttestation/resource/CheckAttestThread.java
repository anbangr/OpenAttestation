/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.intel.openAttestation.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.intel.openAttestation.util.AttestUtil;
import com.intel.openAttestation.util.ResultConverter;
import com.intel.openAttestation.util.ResultConverter.AttestResult;

import com.intel.openAttestation.hibernate.dao.AttestDao;
import com.intel.openAttestation.hibernate.domain.AttestRequest;
import com.intel.openAttestation.resource.AttestService;

public class CheckAttestThread extends Thread {
	
	private String requestId;
	private boolean running = true;
	
	private static Logger logger = Logger.getLogger("OpenAttestation");
	
	
	public CheckAttestThread(String requestId){
		this.requestId = requestId;
	}
	
	@Override
	public void run() {
		try {
			while (running) { 
				sleep(AttestUtil.getDefaultAttestTimeout());
				checkAttest(requestId);
			}
			
		} catch (InterruptedException e) {
//			e.printStackTrace();
			logger.fatal("Exception:", e);
		}
	}
	
	
	public void checkAttest(String requestId){
		AttestDao dao = new AttestDao();
		List<AttestRequest> reqs= new ArrayList<AttestRequest>();
		reqs = AttestService.getRequestsByReqId(requestId);
		for (AttestRequest req: reqs){
			AttestRequest reqnew = AttestService.loadRequest(req.getId());
			if (reqnew.getResult() == null){
				long timeUsed = System.currentTimeMillis() - req.getRequestTime().getTime();
				if (req.getMachineCert() == null ){
					logger.warn("Host:" +req.getHostName() +" Machine Cert is null");
					req.setResult(ResultConverter.getIntFromResult(AttestResult.UN_KNOWN));
					req.setValidateTime(new Date());
					dao.updateRequest(req);
				}
				else if (timeUsed > AttestUtil.getDefaultAttestTimeout()){
					logger.warn("Host:" +req.getHostName() +" time is out");
					req.setResult(ResultConverter.getIntFromResult(AttestResult.TIME_OUT));
					req.setValidateTime(new Date());
					dao.updateRequest(req);
				}
				//AttestResult.KNOWN is written by HisAppraiser
			}
		}
		if (AttestService.isAllAttested(requestId)){
			running = false;
			logger.info("requestId:" +requestId +"is all attested");
		}
	}
}
