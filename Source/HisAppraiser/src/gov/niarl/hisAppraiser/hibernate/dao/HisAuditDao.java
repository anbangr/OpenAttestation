/*
 * (copyright) 2012 United States Government, as represented by the 
 * Secretary of Defense.  All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright 
 * notice, this list of conditions and the following disclaimer. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the name of the U.S. Government nor the names of its 
 * contributors may be used to endorse or promote products derived from 
 * this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS 
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY 
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.niarl.hisAppraiser.hibernate.dao;

import gov.niarl.hisAppraiser.hibernate.domain.Alerts;
import gov.niarl.hisAppraiser.hibernate.domain.AuditLog;
import gov.niarl.hisAppraiser.hibernate.util.HibernateUtilHis;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

/**
 * This class serves as a central location for updates and queries against 
 * the AuditLog table
 * @author syelama
 * @version Crossbow
 *
 */
public class HisAuditDao {

	/**
	 * Constructor to start a hibernate transaction in case one has not
	 * already been started 
	 */
	public HisAuditDao() {
		HibernateUtilHis.beginTransaction();
	}

	/**
	 * This saves an AuditLog making sure that the time stamp is current    
	 * and the machine name is lower case
	 * @param auditLog AuditLog entry to save
	 */
	public void saveAuditLog(AuditLog auditLog) {
		try {
			auditLog.setTimestamp(new Date());
			auditLog.setMachineName(auditLog.getMachineName().toLowerCase());
			HibernateUtilHis.getSession().save(auditLog);
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a new alert linked to an AuditLog entry
	 * @param auditLog AuditLog linked by foreign key
	 * @return return the newly created alert
	 */
	public Alerts createAlert(AuditLog auditLog) {
		try {
			Alerts alerts = new Alerts();
			alerts.setAuditLog(auditLog);
			alerts.setStatus("New");
			HibernateUtilHis.getSession().save(alerts);
			return alerts;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve  an AuditLog entry based on the primary key
	 * @param id The id or primary key of the needed AuditLog entry
	 * @return The AuditLog entry retrieved from the database
	 */
	public AuditLog getAuditLog(int id) {
		Query query = HibernateUtilHis.getSession().createQuery("from AuditLog a where a.id = :id");
		query.setLong("id", id);
		List list = query.list();
		if (list.size() < 1) {
			return null;
		} else {
			return (AuditLog) list.iterator().next();
		}
	}

	/**
	 * Based on a id retrieve an AuditLog prior to that id.
	 * @param machineName The machine for which the previous AuditLog is needed 
	 * @param date The date prior to which an AuditLog must be retrieved.
	 * @return Previous AuditLog or null if none exists 
	 */
	public AuditLog getPreviousAuditLog(String machineName, Long id) {
		machineName = machineName.toLowerCase();

		Long longId = null;
		Query query = HibernateUtilHis.getSession().createQuery("select max(a.id) from AuditLog a where a.machineName = :machineName and a.id < :id");
		query.setString("machineName", machineName);
		query.setLong("id", id);
		List list = query.list();
		if (list.size() < 1) {
			return null;
		} else {
			longId = (Long) list.iterator().next();
			if (longId == null) {
				return null;
			}
		}

		query = HibernateUtilHis.getSession().createQuery("from AuditLog a where a.id = :id");
		query.setLong("id", longId);
		list = query.list();
		if (list.size() < 1) {
			return null;
		} else {
			return (AuditLog) list.iterator().next();
		}
	}

	/**
	 * Returns the last audit log from a machine or null if none exists.
	 * @param machineName Name of the machine for which a previous comparison is required
	 * @return The last audit log from a machine or null if none exists.
	 */
	public AuditLog getLastAuditLog(String machineName) {
		return getPreviousAuditLog(machineName, Long.MAX_VALUE);
	}

	/**
	 * Total count of integrity reports.
	 * @return Total count of integrity reports.
	 */
	public int getAuditLogCount() {
		try {
			final String queryString = "select count(a.id) from AuditLog a";

			return ((Integer) HibernateUtilHis.getSession().createQuery(queryString).list().iterator().next()).intValue();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve a list of AuditLog entries beginning with  
	 * @param order Integer 0,1 indicated whether to sort by machine name or by time stamp
	 * @param firstResult The first result in a set of thousands of results.
	 * @param maxResults Maximum number of results to return.
	 * @return List of AuditLog entries retrieved from the database.
	 */
	public List<AuditLog> getAuditLogPageWithOrder(int order, int firstResult, int maxResults) {
		try {
			if (HibernateUtilHis.getConfiguration().getProperties().get("hibernate.dialect").toString().toLowerCase().contains("sqlserver")) {
				final String[] queryString = new String[10];
				queryString[0] = "select {audit_logaudit_log.*} from (select *, ROW_NUMBER() OVER (order by machine_name asc,timestamp desc) AS 'RowNumber' from audit_log) as audit_logaudit_log where RowNumber between " + Integer.toString(firstResult + 1) + " and " + Integer.toString(firstResult + maxResults);
				queryString[1] = "select {audit_logaudit_log.*} from (select *, ROW_NUMBER() OVER (order by timestamp desc,machine_name asc) AS 'RowNumber' from audit_log) as audit_logaudit_log where RowNumber between " + Integer.toString(firstResult + 1) + " and " + Integer.toString(firstResult + maxResults);

				Query query = HibernateUtilHis.getSession().createSQLQuery(queryString[order]).addEntity("audit_logaudit_log", AuditLog.class);
				return (List<AuditLog>) query.list();
			} else {
				final String[] queryString = new String[10];
				queryString[0] = "from AuditLog a order by a.machineName asc,a.timestamp desc";
				queryString[1] = "from AuditLog a order by a.timestamp desc,a.machineName asc";

				Query query = HibernateUtilHis.getSession().createQuery(queryString[order]);
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
				return (List<AuditLog>) query.list();
			}
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}
	}
}
