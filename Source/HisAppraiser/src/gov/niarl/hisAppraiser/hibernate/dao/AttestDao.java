/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package gov.niarl.hisAppraiser.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import gov.niarl.hisAppraiser.hibernate.domain.AttestRequest;
import gov.niarl.hisAppraiser.hibernate.util.HibernateUtilHis;

public class AttestDao {
	
	public AttestDao(){
		HibernateUtilHis.beginTransaction();
	}

	/*
	 * update the request row for a given request
	 * @Param req of the request of interest.
	 * 
	 */
	public AttestRequest updateRequest(AttestRequest req){
		try {
			Session session = HibernateUtilHis.getSession();
			session.update(req);
			session.flush();
			return  (AttestRequest)session.get(AttestRequest.class, req.getId());
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}
	}

	
	/*
	 * obtain the first attestRequest row for a given host name.
	 * @Param hostName Name of the machine of interest.
	 * @Return The AttestRequest entry
	 */
	public AttestRequest getLatestPolledRequest(String hostName){
		hostName = hostName.toLowerCase();
		Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.hostName = :hostName and" +
				                " a.isConsumedByPollingWS = :isConsumedByPollingWS and a.auditLog is null and a.result is null order by a.requestTime asc");
		query.setString("hostName", hostName);
		query.setBoolean("isConsumedByPollingWS", true);
		List list = query.list();
		if (list.size() < 1) {
			return new AttestRequest();
		} else {
			return (AttestRequest) list.iterator().next();
		}
	}
	
	/**
	 * get the earliest request attest for given host  
	 * @param hostName
	 * @return
	 */
	public AttestRequest getFirstRequest(String hostName){
		hostName = hostName.toLowerCase();
		Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.hostName = :hostName " +
				"and a.isConsumedByPollingWS = :isConsumedByPollingWS and a.auditLog is null and a.result is null order by a.requestTime asc");
		query.setString("hostName", hostName);
		query.setBoolean("isConsumedByPollingWS", false);
		List list = query.list();
		if (list.size() < 1) {
			return new AttestRequest();
		} else {
			return (AttestRequest) list.iterator().next();
		}
	}
}
