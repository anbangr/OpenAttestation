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
package gov.niarl.hisAppraiser.integrityReport;

import gov.niarl.his.xsd.JAXBContextIntegrity_Report_Manifest_v1_0String;
import gov.niarl.his.xsd.JAXBContextPCR_DifferenceString;
import gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.integrity_Report_v1_0.ReportType;
import gov.niarl.his.xsd.pcr_difference.ObjectFactory;
import gov.niarl.his.xsd.pcr_difference.PCRDifferenceReport;
import gov.niarl.his.xsd.pcr_difference.PCRDifferenceReport.CurrentValue;
import gov.niarl.his.xsd.pcr_difference.PCRDifferenceReport.PreviousCurrent;
import gov.niarl.his.xsd.pcr_difference.PCRDifferenceReport.PreviousValue;
import gov.niarl.hisAppraiser.Constants;
import gov.niarl.hisAppraiser.hibernate.dao.AttestDao;
import gov.niarl.hisAppraiser.hibernate.dao.HisAuditDao;
import gov.niarl.hisAppraiser.hibernate.dao.HisMachineCertDao;
import gov.niarl.hisAppraiser.hibernate.domain.AttestRequest;
import gov.niarl.hisAppraiser.hibernate.domain.AuditLog;
import gov.niarl.hisAppraiser.hibernate.domain.MachineCert;
import gov.niarl.hisAppraiser.hibernate.util.AttestService;
import gov.niarl.hisAppraiser.hibernate.util.HibernateUtilHis;
import gov.niarl.hisAppraiser.util.AlertConfiguration;
import gov.niarl.hisAppraiser.util.Emailer;
import gov.niarl.hisAppraiser.util.HisUtil;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;

import org.apache.log4j.Logger;
import org.bouncycastle.openssl.PEMReader;

/**
 * This class is used by the HisReportParser and others for utility functions.
 * @author syelama
 * @version Crossbow
 *
 */
public class HisReportUtil {
	private static Logger logger = Logger.getLogger(HisReportUtil.class);

	static Unmarshaller unmarshallerIntegrity_Report_Manifest_v1_0;
	static {
		try {
			JAXBContext context = JAXBContext.newInstance(JAXBContextIntegrity_Report_Manifest_v1_0String.contextString);
			unmarshallerIntegrity_Report_Manifest_v1_0 = context.createUnmarshaller();
			// unmarshallerIntegrity_Report_Manifest_v1_0.setValidating(true);

			// For org.bouncycastle.openssl.PEMReader
			HisUtil.loadBouncyCastleProvider();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is a convenience method to un-marshal a report and return the 
	 * top level JAXB element.
	 * @param xmlReport XML string integrity report.
	 * @return ReportType top level JAXB element.
	 */
	public static ReportType unmarshallReport(String xmlReport) {
		try {
			return ((JAXBElement<ReportType>) unmarshallerIntegrity_Report_Manifest_v1_0.unmarshal(new ByteArrayInputStream(xmlReport.getBytes()))).getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert a PEM formatted certificate in X509 format.
	 * @param machineCertPEM Machine certificate in PEM/text format. 
	 * @return An X509 certificate object.
	 */
	public static X509Certificate pemToX509Certificate(String machineCertPEM) {
		try {
			PEMReader reader = new PEMReader(new StringReader(machineCertPEM.replace("-----BEGIN CERTIFICATE-----", "-----BEGIN CERTIFICATE-----\n").replace("-----END CERTIFICATE-----", "\n-----END CERTIFICATE-----")));
			return (X509Certificate) reader.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * This function was used to create XML for a difference report when a
	 * detail link was clicked on the old portal.
	 * @param auditLogDifferentThanPrevious AuditLog entry from the database.
	 * @param pcrNumber PCR number for which the difference was requested.
	 * @return XML string showing differences from the previous report PCR.
	 */
	public static String getPCRDifferenceReport(AuditLog auditLogDifferentThanPrevious, int pcrNumber) {
		try {
			ObjectFactory objectFactory = new ObjectFactory();
			PCRDifferenceReport differenceReport = objectFactory.createPCRDifferenceReport();
			differenceReport.setMachineName(auditLogDifferentThanPrevious.getMachineName());
			differenceReport.setPCRNumber(BigInteger.valueOf(pcrNumber));
			differenceReport.setPCRDescription(HisReportValidator.getPcrDescription(pcrNumber, false));

			CurrentValue currentValue = objectFactory.createPCRDifferenceReportCurrentValue();
			HisReportValidator currentValuehisReportValidator = new HisReportValidator(auditLogDifferentThanPrevious.getReport(), null, null, null, null, null);
			currentValue.setReportID(currentValuehisReportValidator.getHisReportData().getReportID());
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(auditLogDifferentThanPrevious.getTimestamp());
			currentValue.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			currentValue.setValue(currentValuehisReportValidator.getPcrValue(pcrNumber));

			AuditLog auditLogPrevious = new HisAuditDao().getPreviousAuditLog(auditLogDifferentThanPrevious.getMachineName(), auditLogDifferentThanPrevious.getId());
			PreviousValue previousValue = objectFactory.createPCRDifferenceReportPreviousValue();
			
//			String report  = auditLogPrevious.getReport();
			
			if (auditLogPrevious != null)
			{
				String report  = auditLogPrevious.getReport();
				HisReportValidator previousValuehisReportValidator = new HisReportValidator(report, null, null, null, null, null);
				previousValue.setReportID(previousValuehisReportValidator.getHisReportData().getReportID());
				GregorianCalendar gregorianCalendarPrevious = new GregorianCalendar();
				gregorianCalendarPrevious.setTime(auditLogPrevious.getTimestamp());
				previousValue.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendarPrevious));
				previousValue.setValue(previousValuehisReportValidator.getPcrValue(pcrNumber));
	
				differenceReport.setCurrentValue(currentValue);
				differenceReport.setPreviousValue(previousValue);
	
				PreviousCurrent previousCurrent = objectFactory.createPCRDifferenceReportPreviousCurrent();
				previousCurrent.getValue().add(previousValuehisReportValidator.getPcrValue(pcrNumber));
				previousCurrent.getValue().add(currentValuehisReportValidator.getPcrValue(pcrNumber));
				differenceReport.setPreviousCurrent(previousCurrent);
			}
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = JAXBContext.newInstance(JAXBContextPCR_DifferenceString.contextString).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "PCR_Difference.xsd");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(differenceReport, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * This function is used by both the login module and the web services
	 * to submit an integrity report. It calls alert creation functions and 
	 * email functionality.
	 * @param sid The SID sent from the client.
	 * @param reportString The integrity report sent form the client.
	 * @param nonceInput The nonce provided to the client.
	 * @param pcrSelectInput The PCR select provided to the client.
	 * @param machineNameInput The machine name sent from the client.
	 */
	public static void submitReport(String sid, String reportString, byte[] nonceInput, byte[] pcrSelectInput, String machineNameInput) {
	
		HisMachineCertDao hisMachineCertDao = new HisMachineCertDao();
		MachineCert machineCert = null;
		X509Certificate machineCertificate = null;
		machineCert = hisMachineCertDao.getMachineCert(machineNameInput);
		if (machineCert == null) {
			logger.error("Machine '" + machineNameInput + "' is not enrolled.");
		} else {
			machineCertificate = pemToX509Certificate(machineCert.getCertificate());
		}

		HisAuditDao hisAuditDao = new HisAuditDao();

		AuditLog lastAuditLog = hisAuditDao.getLastAuditLog(machineNameInput);
		String previousReportString = lastAuditLog == null ? null : lastAuditLog.getReport();
		
		HisReportValidator hisReportValidator = new HisReportValidator(reportString, nonceInput, pcrSelectInput, machineNameInput, machineCertificate, previousReportString);
		
		AuditLog auditLog = new AuditLog();
		auditLog.setPcr0(hisReportValidator.getPcrValue(0));
		auditLog.setPcr1(hisReportValidator.getPcrValue(1));
		auditLog.setPcr2(hisReportValidator.getPcrValue(2));
		auditLog.setPcr3(hisReportValidator.getPcrValue(3));
		auditLog.setPcr4(hisReportValidator.getPcrValue(4));
		auditLog.setPcr5(hisReportValidator.getPcrValue(5));
		auditLog.setPcr6(hisReportValidator.getPcrValue(6));
		auditLog.setPcr7(hisReportValidator.getPcrValue(7));
		auditLog.setPcr8(hisReportValidator.getPcrValue(8));
		auditLog.setPcr9(hisReportValidator.getPcrValue(9));
		auditLog.setPcr10(hisReportValidator.getPcrValue(10));
		auditLog.setPcr11(hisReportValidator.getPcrValue(11));
		auditLog.setPcr12(hisReportValidator.getPcrValue(12));
		auditLog.setPcr13(hisReportValidator.getPcrValue(13));
		auditLog.setPcr14(hisReportValidator.getPcrValue(14));
		auditLog.setPcr15(hisReportValidator.getPcrValue(15));
		auditLog.setPcr16(hisReportValidator.getPcrValue(16));
		auditLog.setPcr17(hisReportValidator.getPcrValue(17));
		auditLog.setPcr18(hisReportValidator.getPcrValue(18));
		auditLog.setPcr19(hisReportValidator.getPcrValue(19));
		auditLog.setPcr20(hisReportValidator.getPcrValue(20));
		auditLog.setPcr21(hisReportValidator.getPcrValue(21));
		auditLog.setPcr22(hisReportValidator.getPcrValue(22));
		auditLog.setPcr23(hisReportValidator.getPcrValue(23));

		auditLog.setSid(sid);
		auditLog.setMachineName(machineNameInput);
		auditLog.setNonce(HisUtil.hexString(nonceInput));
		auditLog.setPcrSelect(HisUtil.hexString(pcrSelectInput));
		auditLog.setSignatureVerified(hisReportValidator.isSignatureVerified());
		auditLog.setMachine(machineCert);
		auditLog.setReport(reportString);
		auditLog.setPreviousDifferences(hisReportValidator.getPreviousReportDifferences());
		auditLog.setReportErrors(hisReportValidator.getErrors());

		hisAuditDao.saveAuditLog(auditLog);
		
		
		/********************************************************************************************************
		 * OpenAttestation code:
		 * Validating host's PCR with table PCR_manifest.  
		 * 
		 *******************************************************************************************************/
		AttestDao attestDao = new AttestDao();
		HisAuditDao auditLogDao = new HisAuditDao();
		AuditLog newAuditLog = auditLogDao.getLastAuditLog(machineNameInput);
		AttestRequest latestPolledRequest = attestDao.getLatestPolledRequest(machineNameInput);
		if (latestPolledRequest != null && newAuditLog != null)
		{
			System.out.println("latestPolledRequest" +latestPolledRequest.getId());
	     	if (latestPolledRequest.getId()!=null && latestPolledRequest.getResult()==null)
	     	{
				 latestPolledRequest.setAuditLog(newAuditLog);
				 latestPolledRequest = AttestService.validatePCRReport(latestPolledRequest);
				 attestDao.updateRequest(latestPolledRequest);
			 }
	     	System.out.println("------------------------OpenAttestation complete!------------------------------------------");
		 /****************************************************************************************************/
		}
		
		AlertConfiguration alertConfiguration = Constants.ALERT_CONFIGURATION;
		boolean createAlert = false;
		if (alertConfiguration.getAllAlerts()) {
			if (auditLog.getReportErrors() == null || auditLog.getReportErrors().length() < 1) {
				//Do nothing				
			} else {
				createAlert = true;
			}
		}
		if (alertConfiguration.getSignatureAlerts() && !auditLog.getSignatureVerified()) {
			createAlert = true;
		}
		for (int i = 0; i < 24; i++) {
			if (alertConfiguration.getPcrAlerts(i) && auditLog.getPreviousDifferences().contains(HisReportValidator.DIFFERENCE_SEPARATOR + Integer.toString(i) + HisReportValidator.DIFFERENCE_SEPARATOR)) {
				createAlert = true;
				break;
			}
		}
		if (createAlert) {
			hisAuditDao.createAlert(auditLog);
			//Save before attempting to send an email.
			HibernateUtilHis.commitTransaction();
			//Begin a new transaction if needed further on in the code.
			HibernateUtilHis.beginTransaction();
			Emailer.sendDefaultAlertEmail();
		}
	}
}