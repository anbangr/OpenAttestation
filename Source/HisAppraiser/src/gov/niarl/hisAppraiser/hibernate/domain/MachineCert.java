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
 * Java class linked to the MachineCert table.
 * @author syelama
 * @version Crossbow
 *
 */
public class MachineCert {
	/**
	 * Central location to set the machine name linked to the privacy CA
	 */
	public static final String PRIVACY_CA_NAME = "_privacyca";

	Long id;
	String machineName;
	String certificate;
	Boolean active;
	Date timestamp;
	MachineCert privacyCaMachineCert;
	Date lastPoll;
	Integer nextAction;
	String pollArgs;

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
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
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
	 * @return the privacyCaMachineCert
	 */
	public MachineCert getPrivacyCaMachineCert() {
		return privacyCaMachineCert;
	}

	/**
	 * @param privacyCaMachineCert the privacyCaMachineCert to set
	 */
	public void setPrivacyCaMachineCert(MachineCert privacyCaMachineCert) {
		this.privacyCaMachineCert = privacyCaMachineCert;
	}

	/**
	 * @return the lastPoll
	 */
	public Date getLastPoll() {
		return lastPoll;
	}

	/**
	 * @param lastPoll the lastPoll to set
	 */
	public void setLastPoll(Date lastPoll) {
		this.lastPoll = lastPoll;
	}

	/**
	 * @return the nextAction
	 */
	public Integer getNextAction() {
		return nextAction;
	}

	/**
	 * @param nextAction the nextAction to set
	 */
	public void setNextAction(Integer nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * @return the pollArgs
	 */
	public String getPollArgs() {
		return pollArgs;
	}

	/**
	 * @param pollArgs the pollArgs to set
	 */
	public void setPollArgs(String pollArgs) {
		this.pollArgs = pollArgs;
	}
}