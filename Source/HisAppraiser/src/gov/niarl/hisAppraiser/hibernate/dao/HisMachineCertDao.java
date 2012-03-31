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

import gov.niarl.hisAppraiser.hibernate.domain.MachineCert;
import gov.niarl.hisAppraiser.hibernate.util.HibernateUtilHis;
import gov.niarl.hisAppraiser.integrityReport.HisReportUtil;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

/**
 * This class serves as a central location for updates and queries against 
 * the MachineCert table
 * @author syelama
 * @version Crossbow
 *
 */
public class HisMachineCertDao {

	/**
	 * Constructor to start a hibernate transaction in case one has not
	 * already been started 
	 */
	public HisMachineCertDao() {
		HibernateUtilHis.beginTransaction();
	}

	/**
	 * Obtain the active MachineCert row for a given machine name.
	 * @param machineName Name of the machine of interest.
	 * @return The MachineCert entry or null if the machine name has no
	 * active registrations
	 */
	public MachineCert getMachineCert(String machineName) {
		machineName = machineName.toLowerCase();
		Query query = HibernateUtilHis.getSession().createQuery("from MachineCert m where m.machineName = :machineName and m.active = :active");
		query.setString("machineName", machineName);
		query.setBoolean("active", true);
		List list = query.list();
		if (list.size() < 1) {
			return null;
		} else {
			return (MachineCert) list.iterator().next();
		}
	}

	/**
	 * Create a new MachineCert entry and de-activate the previous entry. 
	 * @param machineName Machine name for the new entry.
	 * @param machineCertPEM Machine certificate fro the new entry in PEM 
	 * format.
	 * @return The newly created entry.
	 */
	public MachineCert createMachineCert(String machineName, String machineCertPEM) {
		machineName = machineName.toLowerCase();
		MachineCert machineCertPrevious = getMachineCert(machineName);
		if (machineCertPrevious != null) {
			machineCertPrevious.setActive(false);
			HibernateUtilHis.getSession().saveOrUpdate(machineCertPrevious);
		}

		MachineCert machineCertNew = new MachineCert();
		machineCertNew.setActive(true);
		machineCertNew.setCertificate(machineCertPEM);
		machineCertNew.setMachineName(machineName);
		Exception exception = null;
		try {
			X509Certificate privacyCaCert = getPrivacyCaCert();
			if (privacyCaCert != null)
				HisReportUtil.pemToX509Certificate(machineCertNew.getCertificate()).verify(privacyCaCert.getPublicKey());
			else
				machineCertNew.setPrivacyCaMachineCert(null);
		} catch (Exception e) {
			e.printStackTrace();
			exception = e;
		}
		if (exception == null) {
			machineCertNew.setPrivacyCaMachineCert(getPrivacyCaMachineCert());
		} else {
			machineCertNew.setPrivacyCaMachineCert(null);
		}
		machineCertNew.setTimestamp(new Date());
		HibernateUtilHis.getSession().saveOrUpdate(machineCertNew);

		return machineCertNew;
	}

	/**
	 * Retrieve the certificate of the privacy CA
	 * @return Certificate in X509 format.
	 */
	public X509Certificate getPrivacyCaCert() {
		return getMachineCertX509Certificate(MachineCert.PRIVACY_CA_NAME);
	}

	/**
	 * Retrieve the active privacy CA entry in the MachineCert table.
	 * @return The entry from the MachineCert table.
	 */
	public MachineCert getPrivacyCaMachineCert() {
		return getMachineCert(MachineCert.PRIVACY_CA_NAME);
	}

	/**
	 * Retrieve the certificate enrolled for a machine by machine name.
	 * @param machineName The machine name.
	 * @return Certificate in X509 format.
	 */
	public X509Certificate getMachineCertX509Certificate(String machineName) {
		try {
			//X509Certificate x509C0erificate = null;
			MachineCert machineCert = getMachineCert(machineName);
			if(machineCert == null) 
				return null;
			else
			    return HisReportUtil.pemToX509Certificate(machineCert.getCertificate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieve an ordered list of machines.
	 * @param order Currently only one order, machine name.
	 * @return List of entries from the MachineCert table.
	 */
	public List<MachineCert> getMachineCertAllWithOrder(int order) {
		try {
			Query[] query = new Query[10];
			query[1] = HibernateUtilHis.getSession().createQuery("from MachineCert m  where m.active = :active order by m.machineName asc,m.id asc");
			query[1].setBoolean("active", true);

			return (List<MachineCert>) query[order].list();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve a MachineCert entry based on the primary key.
	 * @param id The id, primary key.
	 * @return Entry from the MachineCert table.
	 */
	public MachineCert getMachineCert(int id) {
		return (MachineCert) HibernateUtilHis.getSession().load(MachineCert.class, new Long(id));
	}
}
