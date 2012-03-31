/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.manifest.resource;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import com.intel.openAttestation.manifest.bean.AttestationResponseFault;
import com.intel.openAttestation.manifest.hibernate.dao.PCRManifestDAO;
import com.intel.openAttestation.manifest.hibernate.domain.PCRManifest;
import com.intel.openAttestation.manifest.resource.PCRManifestResource;

/**
 * RESTful web service interface to work with PCR Manifest DB.
 * @author xmei1
 *
 */

@Path("/V1.0/PCR")
public class PCRManifestResource {
	
	@GET
	@Produces("application/json")
	public List<PCRManifest> getPCREntry(@QueryParam("index") String index,
			@QueryParam("PCRNumber") String number,@QueryParam("PCRDesc") String desc){
		PCRManifestDAO dao = new PCRManifestDAO();
		if (index == null && number == null && desc == null)
			return dao.getAllPCREntries();
		else if ( index != null)
			return dao.queryPCREntryByIndex(Long.valueOf(index).longValue());
		else if (number != null && desc == null)
			return dao.queryPCREntry(Integer.valueOf(number).intValue());
		else if (number == null && desc != null)
			return dao.queryPCREntry(desc);
		else
			return dao.queryPCREntry(Integer.valueOf(number).intValue(), desc);
	}
	

	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPCREntry(@Context UriInfo uriInfo, PCRManifest pcr,
			@Context javax.servlet.http.HttpServletRequest request){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(PCRManifestResource.class);
		System.out.println("uri:"+uriInfo.getPath());
		Response.Status status = Response.Status.CREATED;
		String requestHost = request.getRemoteHost();
		pcr.setCreateRequestHost(requestHost);
		pcr.setLastUpdateRequestHost(requestHost);
        try{
			PCRManifestDAO dao = new PCRManifestDAO();
			if (dao.isPCRExisted(pcr.getPCRNumber(), pcr.getPCRValue())){
				status = Response.Status.CONFLICT;
				AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultCode.FAULT_400);
				fault.setMessage("Add PCR entry failed.");
				fault.setDetail("PCRNumber:" + pcr.getPCRNumber() +", PCRValue: " 
						+ pcr.getPCRValue()+" already exist in DB.");
				return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
			}
			dao.addPCREntry(pcr);
	        return Response.status(status).header("Location", b.build()).entity(pcr)
	        		.build();
		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultCode.FAULT_500);
			fault.setMessage("Add PCR entry failed.");
			fault.setDetail("Exception:" + e.getMessage()); 
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
		}

	}

	@DELETE
	public Response delPCREntry(@QueryParam("index") String index, @Context UriInfo uriInfo){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(PCRManifestResource.class);
		Response.Status status = Response.Status.ACCEPTED;

        try{
			PCRManifestDAO dao = new PCRManifestDAO();
			if (dao.isPCRExisted(Long.valueOf(index).longValue())){
				//PCRManifest pcr = new PCRManifest(Long.valueOf(index).longValue());
				dao.deletePCREntry(Long.valueOf(index));
				return Response.status(status).build();
			}
			status = Response.Status.BAD_REQUEST;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultCode.FAULT_404);
			fault.setMessage("Delete PCR entry failed.");
			fault.setDetail("PCR Index:" + index + " doesn't exist in DB.");
			return Response.status(status).entity(fault)
					.build();

		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultCode.FAULT_500);
			fault.setMessage("Delete PCR entry failed.");
			fault.setDetail("Exception:" + e.toString()); 
			return Response.status(status).entity(fault)
					.build();

		}
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatePCREntry(@Context UriInfo uriInfo, PCRManifest pcr,
			@Context javax.servlet.http.HttpServletRequest request){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(PCRManifestResource.class);
		System.out.println("index:"+pcr.getIndex());
		Response.Status status = Response.Status.ACCEPTED;
		String requestHost = request.getRemoteHost();
        try{
			PCRManifestDAO dao = new PCRManifestDAO();
			if (! dao.isPCRExisted(pcr.getIndex())){
				status = Response.Status.NOT_FOUND;
				AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultCode.FAULT_404);
				fault.setMessage("Update PCR entry failed.");
				fault.setDetail("PCR Index:" + pcr.getIndex() + " doesn't exist in DB.");
				return Response.status(status)//.header("Location", b.build())
						.entity(fault).build();
			}
			if (dao.isPCRExisted(pcr.getPCRNumber(), pcr.getPCRValue(), pcr.getIndex())){
				status = Response.Status.CONFLICT;
				AttestationResponseFault fault = new AttestationResponseFault(
						AttestationResponseFault.FaultCode.FAULT_400);
				fault.setMessage("Update PCR entry failed.");
				fault.setDetail("PCRNumber:" + pcr.getPCRNumber() +", PCRValue: " 
						+ pcr.getPCRValue()+" already exist in DB.");
				return Response.status(status)//.header("Location", b.build())
						.entity(fault).build();

			}
			pcr.setLastUpdateRequestHost(requestHost);
			dao.updatePCREntry(pcr);
	        return Response.status(status)//.header("Location", b.build())
	        		.entity(pcr).build();
		}catch (Exception e){
			e.printStackTrace();
			status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultCode.FAULT_500);
			fault.setMessage("Update PCR entry failed.");
			fault.setDetail("Exception:" + e.getMessage()); 
			return Response.status(status)//.header("Location", b.build())
					.entity(fault).build();

		}
	}
	
	
	@POST
	@Path("/Validate")
	@Consumes("application/json")
	public Response validatePCR(@Context UriInfo uriInfo, 
			List<PCRManifest> pcrs){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(PCRManifestResource.class);
		System.out.println("uri:"+uriInfo.getPath());

		Response.Status status = Response.Status.ACCEPTED;
        try{
			PCRManifestDAO dao = new PCRManifestDAO();
			StringBuffer sb = new StringBuffer();
			String failedPCRNumber = "";
			boolean flag = true;
			for (PCRManifest pcr : pcrs){
				Integer pcrNumber = pcr.getPCRNumber();
				String pcrValue = pcr.getPCRValue();
				if ( ! dao.validatePCR(pcrNumber, pcrValue)){
					if (flag){
						failedPCRNumber = sb.append(String.valueOf(pcrNumber)).toString();
						flag = false;
					}else
						failedPCRNumber = sb.append("|").append(String.valueOf(pcrNumber)).toString();
				}
			}
			
	        return Response.status(status)//.header("Location", b.build())
	        		.entity(failedPCRNumber).build();
		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			AttestationResponseFault fault = new AttestationResponseFault(
					AttestationResponseFault.FaultCode.FAULT_500);
			fault.setMessage("Validate PCR failed.");
			fault.setDetail("Exception:" + e.getMessage()); 
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();

		}

	}
}
