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
package gov.niarl.hisAppraiser.hibernate.domain;

import java.util.Date;

/**
 * Java class linked to the AuditLog table.
 * @author syelama
 * @version Crossbow
 *
 */
public class AuditLog {
	Long id;
	String sid;
	String machineName;
	Date timestamp;
	String pcr0;
	String pcr1;
	String pcr2;
	String pcr3;
	String pcr4;
	String pcr5;
	String pcr6;
	String pcr7;
	String pcr8;
	String pcr9;
	String pcr10;
	String pcr11;
	String pcr12;
	String pcr13;
	String pcr14;
	String pcr15;
	String pcr16;
	String pcr17;
	String pcr18;
	String pcr19;
	String pcr20;
	String pcr21;
	String pcr22;
	String pcr23;
	MachineCert machine;
	String pcrSelect;
	String nonce;
	Boolean signatureVerified;
	String report;
	String previousDifferences;
	String reportErrors;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * @param sid the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}

	/**
	 * @param machineName the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the pcr0
	 */
	public String getPcr0() {
		return pcr0;
	}

	/**
	 * @param pcr0 the pcr0 to set
	 */
	public void setPcr0(String pcr0) {
		this.pcr0 = pcr0;
	}

	/**
	 * @return the pcr1
	 */
	public String getPcr1() {
		return pcr1;
	}

	/**
	 * @param pcr1 the pcr1 to set
	 */
	public void setPcr1(String pcr1) {
		this.pcr1 = pcr1;
	}

	/**
	 * @return the pcr2
	 */
	public String getPcr2() {
		return pcr2;
	}

	/**
	 * @param pcr2 the pcr2 to set
	 */
	public void setPcr2(String pcr2) {
		this.pcr2 = pcr2;
	}

	/**
	 * @return the pcr3
	 */
	public String getPcr3() {
		return pcr3;
	}

	/**
	 * @param pcr3 the pcr3 to set
	 */
	public void setPcr3(String pcr3) {
		this.pcr3 = pcr3;
	}

	/**
	 * @return the pcr4
	 */
	public String getPcr4() {
		return pcr4;
	}

	/**
	 * @param pcr4 the pcr4 to set
	 */
	public void setPcr4(String pcr4) {
		this.pcr4 = pcr4;
	}

	/**
	 * @return the pcr5
	 */
	public String getPcr5() {
		return pcr5;
	}

	/**
	 * @param pcr5 the pcr5 to set
	 */
	public void setPcr5(String pcr5) {
		this.pcr5 = pcr5;
	}

	/**
	 * @return the pcr6
	 */
	public String getPcr6() {
		return pcr6;
	}

	/**
	 * @param pcr6 the pcr6 to set
	 */
	public void setPcr6(String pcr6) {
		this.pcr6 = pcr6;
	}

	/**
	 * @return the pcr7
	 */
	public String getPcr7() {
		return pcr7;
	}

	/**
	 * @param pcr7 the pcr7 to set
	 */
	public void setPcr7(String pcr7) {
		this.pcr7 = pcr7;
	}

	/**
	 * @return the pcr8
	 */
	public String getPcr8() {
		return pcr8;
	}

	/**
	 * @param pcr8 the pcr8 to set
	 */
	public void setPcr8(String pcr8) {
		this.pcr8 = pcr8;
	}

	/**
	 * @return the pcr9
	 */
	public String getPcr9() {
		return pcr9;
	}

	/**
	 * @param pcr9 the pcr9 to set
	 */
	public void setPcr9(String pcr9) {
		this.pcr9 = pcr9;
	}

	/**
	 * @return the pcr10
	 */
	public String getPcr10() {
		return pcr10;
	}

	/**
	 * @param pcr10 the pcr10 to set
	 */
	public void setPcr10(String pcr10) {
		this.pcr10 = pcr10;
	}

	/**
	 * @return the pcr11
	 */
	public String getPcr11() {
		return pcr11;
	}

	/**
	 * @param pcr11 the pcr11 to set
	 */
	public void setPcr11(String pcr11) {
		this.pcr11 = pcr11;
	}

	/**
	 * @return the pcr12
	 */
	public String getPcr12() {
		return pcr12;
	}

	/**
	 * @param pcr12 the pcr12 to set
	 */
	public void setPcr12(String pcr12) {
		this.pcr12 = pcr12;
	}

	/**
	 * @return the pcr13
	 */
	public String getPcr13() {
		return pcr13;
	}

	/**
	 * @param pcr13 the pcr13 to set
	 */
	public void setPcr13(String pcr13) {
		this.pcr13 = pcr13;
	}

	/**
	 * @return the pcr14
	 */
	public String getPcr14() {
		return pcr14;
	}

	/**
	 * @param pcr14 the pcr14 to set
	 */
	public void setPcr14(String pcr14) {
		this.pcr14 = pcr14;
	}

	/**
	 * @return the pcr15
	 */
	public String getPcr15() {
		return pcr15;
	}

	/**
	 * @param pcr15 the pcr15 to set
	 */
	public void setPcr15(String pcr15) {
		this.pcr15 = pcr15;
	}

	/**
	 * @return the pcr16
	 */
	public String getPcr16() {
		return pcr16;
	}

	/**
	 * @param pcr16 the pcr16 to set
	 */
	public void setPcr16(String pcr16) {
		this.pcr16 = pcr16;
	}

	/**
	 * @return the pcr17
	 */
	public String getPcr17() {
		return pcr17;
	}

	/**
	 * @param pcr17 the pcr17 to set
	 */
	public void setPcr17(String pcr17) {
		this.pcr17 = pcr17;
	}

	/**
	 * @return the pcr18
	 */
	public String getPcr18() {
		return pcr18;
	}

	/**
	 * @param pcr18 the pcr18 to set
	 */
	public void setPcr18(String pcr18) {
		this.pcr18 = pcr18;
	}

	/**
	 * @return the pcr19
	 */
	public String getPcr19() {
		return pcr19;
	}

	/**
	 * @param pcr19 the pcr19 to set
	 */
	public void setPcr19(String pcr19) {
		this.pcr19 = pcr19;
	}

	/**
	 * @return the pcr20
	 */
	public String getPcr20() {
		return pcr20;
	}

	/**
	 * @param pcr20 the pcr20 to set
	 */
	public void setPcr20(String pcr20) {
		this.pcr20 = pcr20;
	}

	/**
	 * @return the pcr21
	 */
	public String getPcr21() {
		return pcr21;
	}

	/**
	 * @param pcr21 the pcr21 to set
	 */
	public void setPcr21(String pcr21) {
		this.pcr21 = pcr21;
	}

	/**
	 * @return the pcr22
	 */
	public String getPcr22() {
		return pcr22;
	}

	/**
	 * @param pcr22 the pcr22 to set
	 */
	public void setPcr22(String pcr22) {
		this.pcr22 = pcr22;
	}

	/**
	 * @return the pcr23
	 */
	public String getPcr23() {
		return pcr23;
	}

	/**
	 * @param pcr23 the pcr23 to set
	 */
	public void setPcr23(String pcr23) {
		this.pcr23 = pcr23;
	}

	/**
	 * @return the machine
	 */
	public MachineCert getMachine() {
		return machine;
	}

	/**
	 * @param machine the machine to set
	 */
	public void setMachine(MachineCert machine) {
		this.machine = machine;
	}

	/**
	 * @return the pcrSelect
	 */
	public String getPcrSelect() {
		return pcrSelect;
	}

	/**
	 * @param pcrSelect the pcrSelect to set
	 */
	public void setPcrSelect(String pcrSelect) {
		this.pcrSelect = pcrSelect;
	}

	/**
	 * @return the nonce
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * @param nonce the nonce to set
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	/**
	 * @return the signatureVerified
	 */
	public Boolean getSignatureVerified() {
		return signatureVerified;
	}

	/**
	 * @param signatureVerified the signatureVerified to set
	 */
	public void setSignatureVerified(Boolean signatureVerified) {
		this.signatureVerified = signatureVerified;
	}

	/**
	 * @return the report
	 */
	public String getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(String report) {
		this.report = report;
	}

	/**
	 * @return the previousDifferences
	 */
	public String getPreviousDifferences() {
		return previousDifferences;
	}

	/**
	 * @param previousDifferences the previousDifferences to set
	 */
	public void setPreviousDifferences(String previousDifferences) {
		this.previousDifferences = previousDifferences;
	}

	/**
	 * @return the reportErrors
	 */
	public String getReportErrors() {
		return reportErrors;
	}

	/**
	 * @param reportErrors the reportErrors to set
	 */
	public void setReportErrors(String reportErrors) {
		this.reportErrors = reportErrors;
	}
}
