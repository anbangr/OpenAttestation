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

import gov.niarl.sal.webservices.hisWebService.server.domain.ActionDelay.Action;

import java.util.HashMap;

/**
 * Utility class to help with Action enumeration serialization and 
 * de-serialization.     
 * @author syelama
 * @version Crossbow
 *
 */
public class ActionConverter {
	private static HashMap<Integer, Action> integerActionHashMap = new HashMap<Integer, Action>() {
		{
			put(0, Action.DO_NOTHING);
			put(1, Action.SEND_REPORT);
			put(2, Action.REBOOT);
			put(3, Action.VERIFY_CLIENT);
			put(4, Action.CLEAN_CLIENT);
		}
	};

	private static HashMap<Action, Integer> actionIntegerHashMap = new HashMap<Action, Integer>();
	static {
		for (Integer integer : integerActionHashMap.keySet()) {
			actionIntegerHashMap.put(integerActionHashMap.get(integer), integer);
		}
	}

	/**
	 * Converts a integer into an Action enumeration.
	 * @param i Integer linked to an action.
	 * @return Action enumeration related to an integer.
	 */
	public static Action getActionFromInt(int i) {
		return integerActionHashMap.get(i) == null ? Action.DO_NOTHING : integerActionHashMap.get(i);
	}

	/**
	 * Converts an Action enumeration into the related integer.
	 * @param action Enumeration value.
	 * @return Integer related to the enumeration.
	 */
	public static int getIntFromAction(Action action) {
		return actionIntegerHashMap.get(action);
	}
}