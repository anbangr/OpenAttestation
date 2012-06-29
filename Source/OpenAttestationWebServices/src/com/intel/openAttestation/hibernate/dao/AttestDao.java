/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.hibernate.dao;

import gov.niarl.hisAppraiser.hibernate.domain.AuditLog;
import gov.niarl.hisAppraiser.hibernate.domain.MachineCert;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.intel.openAttestation.util.HibernateUtilHis;
import com.intel.openAttestation.hibernate.domain.AttestRequest;

public class AttestDao {
	
	public AttestDao() {
	}
	
	
	/**
	 * save a request for given request
	 * @param req
	 */
	public  void saveRequest(AttestRequest req){
		try {
			HibernateUtilHis.beginTransaction();
			HibernateUtilHis.getSession().save(req);
			HibernateUtilHis.commitTransaction();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
	
	
	/**
	 * get request by id
	 * @param id
	 * @return
	 */
	public AttestRequest getRequestById(Long id){
		AttestRequest req = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.id = :id");
			query.setLong("id", id);
			List list = query.list();
			if (list.size() < 1) {
				req = new AttestRequest();
			} else {
				req =  (AttestRequest) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return req;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
			
	}

	/**
	 * get requests by requestId
	 * @param requestId
	 * @return
	 */
	public List<AttestRequest> getRequestsByRequestId(String requestId){
		List<AttestRequest> reqs = null;
		try{
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where  a.requestId= :requestId");
			query.setString("requestId", requestId);
			List list = query.list();
			if (list.size() < 1) {
				reqs =  new ArrayList<AttestRequest>();
			} else {
				reqs =  (List<AttestRequest>) list;
			}
			HibernateUtilHis.commitTransaction();
			return reqs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
			
	}
	
	
	/**
	 * get all async requests
	 * @return
	 */
	public List<AttestRequest> getAllRequestsAsync(){
		List<AttestRequest> reqs = null;
		try{
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.isSync=:isSync order by a.requestTime desc");
			query.setBoolean("isSync", false);
			List list = query.list();
			if (list.size() < 1) {
				reqs =  new ArrayList<AttestRequest>();
			} else {
				reqs = (List<AttestRequest>) list;
				System.out.println("zlj:" +reqs.get(0).getRequestId() +reqs.get(0).getMachineCert());
			}
			HibernateUtilHis.commitTransaction();
			return reqs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
			
	}

	
	/**
	 * get auditLog by auditId
	 * @param id
	 * @return
	 */
	public AuditLog getAuditLogById(long id){
		AuditLog auditLog = null;
		try{
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from AuditLog a where a.id = :id");
			query.setLong("id", id);
			List list = query.list();
			if (list.size() < 1) {
				auditLog = null;
			} else {
				auditLog = (AuditLog) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return auditLog;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
			
	}
	
	
	/*
	 * update the request row for a given request
	 * @Param req of the request of interest.
	 * 
	 */
	public AttestRequest updateRequest(AttestRequest req){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			session.update(req);
			HibernateUtilHis.commitTransaction();
			return  (AttestRequest)session.get(AttestRequest.class, req.getId());
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
	
	/*
	 * obtain the last attested attestRequest row for a given host name.
	 * @Param hostName Name of the machine of interest.
	 * @Return The AttestRequest entry
	 */
	public AttestRequest getLastAttestedRequest(String hostName){
		AttestRequest req = null;
		try {
			HibernateUtilHis.beginTransaction();
			hostName = hostName.toLowerCase();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.hostName = :hostName and" +
					                " a.result is not null order by a.requestTime desc");
			query.setString("hostName", hostName);
			List list = query.list();
			if (list.size() < 1) {
				req = new AttestRequest();
			} else {
				req = (AttestRequest) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return req;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
	
	/**
	 * Obtain the active MachineCert row for a given machine name.
	 * @param machineName Name of the machine of interest.
	 * @return The MachineCert entry or null if the machine name has no
	 * active registrations
	 */
	public MachineCert getMachineCert(String machineName) {
		machineName = machineName.toLowerCase();
		MachineCert cert = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from MachineCert m where m.machineName = :machineName and m.active = :active");
			query.setString("machineName", machineName);
			query.setBoolean("active", true);
			List list = query.list();
			if (list.size() < 1) {
				cert = null;
			} else {
				cert = (MachineCert) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return cert;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
		
	}
}
