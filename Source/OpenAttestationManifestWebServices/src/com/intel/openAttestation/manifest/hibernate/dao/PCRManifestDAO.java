/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.manifest.hibernate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.intel.openAttestation.manifest.hibernate.domain.PCRManifest;
import com.intel.openAttestation.manifest.hibernate.util.HibernateUtilHis;

/**
 * This class serves as a central location for updates and queries against 
 * the PCR_manifest table
 * @author intel
 * @version OpenAttestation
 *
 */
public class PCRManifestDAO {

	/**
	 * Constructor to start a hibernate transaction in case one has not
	 * already been started 
	 */
	public PCRManifestDAO() {
	}
	
	public List<PCRManifest> getAllPCREntries(){
		try{
			HibernateUtilHis.beginTransaction();
			ArrayList<PCRManifest> PCRList = new ArrayList<PCRManifest>();
			Query query = HibernateUtilHis.getSession().createQuery("from PCRManifest pcrmanifest");
			System.out.println("query:"+query.toString());
			List list = query.list();
			for (int i=0;i<list.size();i++){
				PCRList.add((PCRManifest)list.get(i));
			}
			HibernateUtilHis.commitTransaction();
			return PCRList;
		}catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}
	
	public PCRManifest addPCREntry(PCRManifest pcr){
		try {
			HibernateUtilHis.beginTransaction();
			pcr.setCreateTime(new Date());
			HibernateUtilHis.getSession().save(pcr);
			HibernateUtilHis.commitTransaction();
			return pcr;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}

	}
	
	public PCRManifest updatePCREntry (PCRManifest pcr){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			pcr.setLastUpdateTime(new Date());
			
			Query query = session.createQuery("from PCRManifest a where a.index = :index");
			query.setLong("index", pcr.getIndex());
			List list = query.list();
			if (list.size() < 1){
				throw new Exception ("Object not found");
			}
			PCRManifest pcrOld = (PCRManifest)list.get(0);
			pcrOld.setLastUpdateTime(pcr.getLastUpdateTime());
			pcrOld.setLastUpdateRequestHost(pcr.getLastUpdateRequestHost());
			pcrOld.setPCRDesc(pcr.getPCRDesc());
			pcrOld.setPCRNumber(pcr.getPCRNumber());
			pcrOld.setPCRValue(pcr.getPCRValue());
			HibernateUtilHis.commitTransaction();
			return pcr;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

	public void deletePCREntry (PCRManifest pcr){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			pcr = (PCRManifest)session.load(PCRManifest.class, pcr.getIndex());
			session.delete(pcr);
			HibernateUtilHis.commitTransaction();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}
	public void deletePCREntry (Long index){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			Query query = session.createQuery("from PCRManifest a where a.index = :index");
			query.setLong("index", index);
			List list = query.list();
			if (list.size() < 1){
				throw new Exception ("Object not found");
			}
			PCRManifest pcr = (PCRManifest)list.get(0);
			session.delete(pcr);
			HibernateUtilHis.commitTransaction();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

	public List<PCRManifest> queryPCREntryByIndex (long index){
		List<PCRManifest> pcrs = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from PCRManifest a where a.index = :index");
			query.setLong("index", index);
			List list = query.list();
		
			if (list.size() < 1) {
				pcrs = new ArrayList<PCRManifest>();
			} else {
				pcrs = (List<PCRManifest>)list;
			}
			HibernateUtilHis.commitTransaction();
			return pcrs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

	public List<PCRManifest> queryPCREntry (int PCRNumber){
		List<PCRManifest> pcrs = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from PCRManifest a where a.PCRNumber = :number");
			query.setLong("number", PCRNumber);
			List list = query.list();
			if (list.size() < 1) {
				pcrs = new ArrayList<PCRManifest>();
			} else {
				pcrs = (List<PCRManifest>)list;
			}
			HibernateUtilHis.commitTransaction();
			return pcrs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

	public List<PCRManifest> queryPCREntry (String PCRDesc){
		List<PCRManifest> pcrs = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().
					createQuery("from PCRManifest a where a.PCRDesc like '%"+PCRDesc+"%'");
			List list = query.list();
			if (list.size() < 1) {
				pcrs = new ArrayList<PCRManifest>();
			} else {
				pcrs = (List<PCRManifest>)list;
			}
			HibernateUtilHis.commitTransaction();
			return pcrs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

	public List<PCRManifest> queryPCREntry (int PCRNumber, String PCRDesc){
		List<PCRManifest> pcrs = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().
					createQuery("from PCRManifest a where a.PCRNumber=:number and a.PCRDesc like '%"+PCRDesc+"%'");
			query.setLong("number", PCRNumber);
			List list = query.list();
			if (list.size() < 1) {
				pcrs = new ArrayList<PCRManifest>();
			} else {
				pcrs = (List<PCRManifest>)list;
			}
			HibernateUtilHis.commitTransaction();
			return pcrs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}
	
	public boolean validatePCR (int PCRNumber, String PCRValue){
		boolean flag =false;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().
					createQuery("from PCRManifest a where a.PCRNumber=:number and a.PCRValue=:value");
			query.setLong("number", PCRNumber);
			query.setString("value", PCRValue);
			List list = query.list();
			if (list.size() < 1) {
				flag =  false;
			} else {
				flag = true;
			}
			HibernateUtilHis.commitTransaction();
			return flag;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

	public boolean isPCRExisted(long index){
		boolean flag =false;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from PCRManifest a where a.index = :index");
			query.setLong("index", index);
			List list = query.list();
		
			if (list.size() < 1) {
				flag =  false;
			} else {
				flag = true;
			}
			HibernateUtilHis.commitTransaction();
			return flag;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		

	}
	
	public boolean isPCRExisted(int pcrNumber, String pcrValue){
		boolean flag =false;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from PCRManifest a where a.PCRNumber = :number and a.PCRValue = :value");
			query.setInteger("number", pcrNumber);
			query.setString("value", pcrValue);
			List list = query.list();
		
			if (list.size() < 1) {
				flag =  false;
			} else {
				flag = true;
			}
			HibernateUtilHis.commitTransaction();
			return flag;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}

	public boolean isPCRExisted(int pcrNumber, String pcrValue, long exceptIndex){
		boolean flag = false;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from PCRManifest a " +
					"where a.PCRNumber = :number and a.PCRValue = :value and a.index <> :exceptIndex");
			query.setInteger("number", pcrNumber);
			query.setString("value", pcrValue);
			query.setLong("exceptIndex", exceptIndex);
			List list = query.list();
		
			if (list.size() < 1) {
				flag =  false;
			} else {
				flag = true;
			}
			HibernateUtilHis.commitTransaction();
			return flag;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}

}
