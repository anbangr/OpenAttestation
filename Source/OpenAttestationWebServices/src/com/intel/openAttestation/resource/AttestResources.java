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

import com.intel.openAttestation.util.ActionConverter;
import com.intel.openAttestation.util.ActionDelay.Action;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;
import com.intel.openAttestation.bean.AttestationResponseFault;
import com.intel.openAttestation.bean.ReqAttestationBean;
import com.intel.openAttestation.bean.RespAsyncBean;
import com.intel.openAttestation.bean.RespSyncBean;
import com.intel.openAttestation.hibernate.dao.AttestDao;
import com.intel.openAttestation.hibernate.domain.AttestRequest;
import com.intel.openAttestation.resource.AttestResources;
import com.intel.openAttestation.resource.AttestService;
import com.intel.openAttestation.resource.CheckAttestThread;
import com.intel.openAttestation.util.AttestUtil;
import com.intel.openAttestation.util.ResultConverter;
import com.intel.openAttestation.util.ResultConverter.AttestResult;
import com.intel.openAttestation.bean.ReqAsyncStatusBean;

@Path("/V1.0")
public class AttestResources {
	private static Logger logger = Logger.getLogger("OpenAttestation");
	/**
	 * synchronous attest model: client sends hosts and pcrmask to be attested, server attest these hosts and return specific PCR values.
	 * in this model, the client will always wait the response of server 
	 * @param Xauthuser
	 * @param Xauthpasswd
	 * @param reqAttestation
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("/PollHosts")
	@Consumes("application/json")
	@Produces("application/json")
	public Response  pollHosts(@HeaderParam("Auth_blob") String Authblob, ReqAttestationBean reqAttestation,
			 @Context UriInfo uriInfo, @Context javax.servlet.http.HttpServletRequest request){
		UriBuilder b = uriInfo.getBaseUriBuilder();
	    b = b.path(AttestResources.class);
	    Response.Status status = Response.Status.OK;
	    AttestDao dao = new AttestDao();
	    String requestHost = request.getRemoteHost();
	    
	    long timeThreshold = reqAttestation.getTimeThreshold() == null ? 0 :reqAttestation.getTimeThreshold();
	    long validateInterval = 0;
	    
	    try{
	    	if (AttestService.ISV_Autherntication_module()){
	    		logger.debug("Request Host:" +requestHost +" Authentication successfully!");
	    		
	    		String requestId = AttestService.addRequests(reqAttestation, requestHost, true);
	    		List<AttestRequest> reqs= AttestService.getRequestsByReqId(requestId);
	    		if (timeThreshold != 0 ){
	    			logger.info("timeThreshold:" +timeThreshold);
	    			for (AttestRequest req: reqs){
	    				AttestRequest lastReq = dao.getLastAttestedRequest(req.getHostName());
	    				long lastValidateTime = lastReq.getId()== null? 0: lastReq.getValidateTime().getTime();
	    				validateInterval = System.currentTimeMillis() - lastValidateTime;
	    				logger.info("validateInterval:" +validateInterval);
	    				if (validateInterval < timeThreshold && lastValidateTime !=0 ){
	    					System.out.println("obtain the trustworthiness of last record");
	    					req.setAuditLog(lastReq.getAuditLog());
	    					req.setResult(lastReq.getResult());
	    					req.setValidateTime(lastReq.getValidateTime());
	    				}
	    				else{
	    					req.setNextAction(ActionConverter.getIntFromAction(Action.SEND_REPORT));
	    					logger.debug("Next Action:" +req.getNextAction());
	    				}
	    				dao.updateRequest(req);
	    			}
	    			//start a thread to attest the pending request
	    			if (!AttestService.isAllAttested(requestId)){
	    				logger.info("requestId:" +requestId +"is not attested.");
			    		CheckAttestThread checkAttestThread = new CheckAttestThread(requestId);
			     		checkAttestThread.start();
	    			}
		     		
	    		}
	    		else{// timeThreshold is null
		    		do{  //loop until all hosts are finished
		    			for (AttestRequest req: reqs){
		    				//load the request again because its status may be changed for each loop
		    				AttestRequest reqnew = AttestService.loadRequest(req.getId());
		    				if (reqnew.getResult() == null){
		    					long timeUsed = System.currentTimeMillis() - req.getRequestTime().getTime();
		    					if (req.getMachineCert() == null ){
		    						req.setResult(ResultConverter.getIntFromResult(AttestResult.UN_KNOWN));
		    						req.setValidateTime(new Date());
		    						dao.updateRequest(req);
		    					}
		    					else if (timeUsed > AttestUtil.getDefaultAttestTimeout()){
		    						req.setResult(ResultConverter.getIntFromResult(AttestResult.TIME_OUT));
		    						req.setValidateTime(new Date());
		    						dao.updateRequest(req);
		    					} 
		    				}
		    			}
		    			Thread.sleep(10000/reqs.size()); //@TO DO: better calculation?
		    		}while(!AttestService.isAllAttested(requestId));
		    		logger.info("requestId:" +requestId +" has attested");
	    		}
	    		
	    		RespSyncBean syncResult = AttestService.getRespSyncResult(requestId);
	    		logger.info("requestId:" +requestId +" has returned the attested result");
    			return Response.status(status).header("Location", b.build()).entity(syncResult)
    							.build();
	     	}
	     	else{
	     		status=Response.Status.UNAUTHORIZED;
	     		AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultName.FAULT_UNAUTH);
				fault.setMessage("Authentication failed.");
				fault.setDetail("your authentication information is wrong");
				logger.error("Request Host:" +requestHost +" " +fault.getMessage() +" Detail:" +fault.getDetail());
	     		return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
	     	}
	    }catch(Exception e){
	    	status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultName.FAULT_ATTEST_ERROR);
			fault.setMessage("poll hosts failed.");
			fault.setDetail("Exception:" + e.toString());
			logger.fatal(fault.getMessage(), e);
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
	    }
	}
	
	/**
	 * asynchronous attest model: client sends hosts and pcrmask to be attested, server response a requestId.
	 * This method returns a requestId immediately.
	 * @param Xauthuser
	 * @param Xauthpasswd
	 * @param uriInfo
	 * @param reqAttestation
	 * @return requestId 
	 */
	@POST
	@Path("/PostHosts")
	@Consumes("application/json")
	@Produces("application/json")
	public Response postHosts(@HeaderParam("Auth_blob") String Authblob, ReqAttestationBean reqAttestation,
			@Context UriInfo uriInfo, @Context javax.servlet.http.HttpServletRequest request){
		UriBuilder b = uriInfo.getBaseUriBuilder();
	    b = b.path(AttestResources.class);
	    Response.Status status = Response.Status.OK;
	    String requestHost = request.getRemoteHost();
	    try{
	    	if (AttestService.ISV_Autherntication_module()){
	    		logger.debug("Request Host:" +requestHost +" Authentication successfully!");
	     		String requestId = AttestService.addRequests(reqAttestation, requestHost, false);
	     		logger.info("requestId:" +requestId +"has recorded in DB" );
	     		String requestIdJson = "{\"" +"RequestId\":" +"\"" +requestId +"\"}";
	     		CheckAttestThread checkAttestThread = new CheckAttestThread(requestId);
	     		checkAttestThread.start();
	     		return Response.status(status).header("Location", b.build()).entity(requestIdJson)
						.build();
	     	}
	     	else{
	     		status = Response.Status.UNAUTHORIZED;
	     		AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultName.FAULT_UNAUTH);
				fault.setMessage("Authentication failed.");
				fault.setDetail("your authentication information is wrong");
				logger.error("Request Host:" +requestHost +" " +fault.getMessage() +" Detail:" +fault.getDetail());
	     		return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
	     	}
	    }catch(Exception e){
	    	status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultName.FAULT_ATTEST_ERROR);
			fault.setMessage("post hosts failed.");
			fault.setDetail("Exception:" + e.toString());
			logger.fatal(fault.getMessage(), e);
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
	    }
	}

	/**
	 * get posted value from DB by requestId.
	 * @param Xauthuser
	 * @param Xauthpasswd
	 * @param requestId
	 * @param uriInfo
	 * @return
	 */
	@GET
	@Path("/PostedHosts")
	@Consumes("application/json")
	@Produces("application/json")
	public Response postedHosts(@HeaderParam("Auth_blob") String Authblob,@QueryParam("requestId") String requestId, 
			@Context UriInfo uriInfo, @Context javax.servlet.http.HttpServletRequest request) {
		UriBuilder b = uriInfo.getBaseUriBuilder();
	    b = b.path(AttestResources.class);
	    Response.Status status = Response.Status.OK;
	    String requestHost = request.getRemoteHost();
	    try{
	    	if (AttestService.ISV_Autherntication_module()){
	    		logger.debug("Request Host:" +requestHost +" Authentication successfully!");
				RespAsyncBean asyncResult = AttestService.getRespAsyncResult(requestId);
				logger.info("requestId:" +requestId +"has returned the attested result");
				return Response.status(status).header("Location", b.build()).entity(asyncResult)
						.build();
	     	}
	     	else{
	     		status = Response.Status.UNAUTHORIZED;
	     		AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultName.FAULT_UNAUTH);
				fault.setMessage("Authentication failed.");
				fault.setDetail("your authentication information is wrong");
				logger.error("Request Host:" +requestHost +" " +fault.getMessage() +" Detail:" +fault.getDetail());
	     		return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
	     	}
	     		
	    }catch(Exception e){
	    	status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultName.FAULT_ATTEST_ERROR);
			fault.setMessage("posted hosts failed.");
			fault.setDetail("Exception:" + e.toString());
			logger.fatal(fault.getMessage(), e);
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
	    }
	}
	
	/**
	 * get sdk's version
	 * @param Xauthuser
	 * @param Xauthpasswd
	 * @param uriInfo
	 * @return
	 */
	@GET
	@Path("/Version")
	@Consumes("application/json")
	@Produces("application/json")
	public Response version(@HeaderParam("Auth_blob") String Authblob, @Context UriInfo uriInfo,
			@Context javax.servlet.http.HttpServletRequest request){
		UriBuilder b = uriInfo.getBaseUriBuilder();
	    b = b.path(AttestResources.class);
	    Response.Status status = Response.Status.OK;
	    String requestHost = request.getRemoteHost();
	    try{
	    	if (AttestService.ISV_Autherntication_module()){
	    		logger.debug("Request Host:" +requestHost +" Authentication successfully!");
	     		String version = "SDKversion : v1.0";
	    		return Response.status(status).header("Location", b.build()).entity(version)
						.build();
	     	}
	     	else{
	     		AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultName.FAULT_UNAUTH);
				fault.setMessage("Authentication failed.");
				fault.setDetail("your authentication information is wrong");
				logger.error("Request Host:" +requestHost +" " +fault.getMessage() +" Detail:" +fault.getDetail());
	     		return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
	     	}
	    }catch(Exception e){
	    	status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultName.FAULT_ATTEST_ERROR);
			fault.setMessage("get version failed.");
			fault.setDetail("Exception:" + e.toString());
			logger.fatal(fault.getMessage(), e);
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
	    }
	}
	
	/**
	 * get sdk's services
	 * @param Xauthuser
	 * @param Xauthpasswd
	 * @param uriInfo
	 * @return
	 */
	@GET
	@Path("/Services")
	@Consumes("application/json")
	@Produces("application/json")
	public Response services(@HeaderParam("Auth_blob") String Authblob, @Context UriInfo uriInfo, 
			@Context javax.servlet.http.HttpServletRequest request){
		UriBuilder b = uriInfo.getBaseUriBuilder();
	    b = b.path(AttestResources.class);
	    Response.Status status = Response.Status.OK;
	    String requestHost = request.getRemoteHost();
	    System.out.println("requesthost=" +requestHost);
	    try{
	    	if (AttestService.ISV_Autherntication_module()){
	    		logger.debug("Request Host:" +requestHost +" Authentication successfully!");
	     		String services = "1.Get current SDK version; 2.Query hosts trustworthiness; " +
	     			             "3.Retrieve previously posted /PostHosts result; 4.Post and wait for hosts trustworthiness";
	    		return Response.status(status).header("Location", b.build()).entity(services)
						.build();
	     	}
	     	else{
	     		AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultName.FAULT_UNAUTH);
				fault.setMessage("Authentication failed.");
				fault.setDetail("your authentication information is wrong");
				logger.error("Request Host:" +requestHost +" " +fault.getMessage() +" Detail:" +fault.getDetail());
	     		return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
	     	}
	    }catch(Exception e){
	    	status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultName.FAULT_ATTEST_ERROR);
			fault.setMessage("get services failed.");
			fault.setDetail("Exception:" + e.toString());
			logger.fatal(fault.getMessage(), e);
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
	    }
	}
	
	@GET
	@Path("/AllRequests")
	@Consumes("application/json")
	@Produces("application/json")
	public Response  getRequestsAsync(@HeaderParam("Auth_blob") String Authblob, @Context UriInfo uriInfo,
			@Context javax.servlet.http.HttpServletRequest request){
		UriBuilder b = uriInfo.getBaseUriBuilder();
	    b = b.path(AttestResources.class);
	    Response.Status status = Response.Status.OK;
	    String requestHost = request.getRemoteHost();
	    try{
	    	if (AttestService.ISV_Autherntication_module()){
	    		logger.debug("Request Host:" +requestHost +" Authentication successfully!");
                        List<ReqAsyncStatusBean> reqs =  AttestService.getRequestsAsync();
               	        GenericEntity<List<ReqAsyncStatusBean>> entity = new GenericEntity<List<ReqAsyncStatusBean>>(reqs) {};
	    		return Response.status(status).header("Location", b.build()).entity(entity)
						.build();
	     	}
	     	else{
	     		AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultName.FAULT_UNAUTH);
				fault.setMessage("Authentication failed.");
				fault.setDetail("your authentication information is wrong");
				logger.error("Request Host:" +requestHost +" " +fault.getMessage() +" Detail:" +fault.getDetail());
	     		return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
	     	}
	    }catch(Exception e){
	    	status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultName.FAULT_ATTEST_ERROR);
			fault.setMessage("get services failed.");
			fault.setDetail("Exception:" + e.toString());
			logger.fatal(fault.getMessage(), e);
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
	    }
		
	}
}
