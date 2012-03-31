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
package gov.niarl.sal.webservices.hisWebService.server.domain;

/**
 * ActionDelay is returned by the HisPollingWebService and contains
 * information to be acted on by the clients.
 * @author syelama
 * @version Crossbow
 *
 */
public class ActionDelay {
	/**
	 * Enumeration containing actions to be taken by the clients.
	 */
	public static enum Action {
		DO_NOTHING, SEND_REPORT, REBOOT, VERIFY_CLIENT, CLEAN_CLIENT
	}

	Action action;
	long delayMilliseconds;
	String args;

	/**
	 * Default constructor with zero arguments.
	 */
	public ActionDelay() {
		super();
	}

	/**
	 * Creates an ActionDelay and sets all elements at once.
	 * @param action An action from the action enumeration.
	 * @param delayMilliseconds Milliseconds for the client to wait for 
	 * doing another poll.
	 * @param args Arguments the client may need to complete an action. 
	 */
	public ActionDelay(Action action, long delayMilliseconds, String args) {
		super();
		this.action = action;
		this.delayMilliseconds = delayMilliseconds;
		this.args = args;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the delayMilliseconds
	 */
	public long getDelayMilliseconds() {
		return delayMilliseconds;
	}

	/**
	 * @param delayMilliseconds the delayMilliseconds to set
	 */
	public void setDelayMilliseconds(long delayMilliseconds) {
		this.delayMilliseconds = delayMilliseconds;
	}

	/**
	 * @return the args
	 */
	public String getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 */
	public void setArgs(String args) {
		this.args = args;
	}
}
