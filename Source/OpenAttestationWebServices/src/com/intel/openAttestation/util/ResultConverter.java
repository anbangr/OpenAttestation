/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.intel.openAttestation.util;

import java.util.HashMap;

//import com.intel.openAttestation.util.AttestResult;


public class ResultConverter {
	
	public static enum AttestResult {
		UN_TRUSTED, TRUSTED, UN_KNOWN,TIME_OUT, PENDING
	}
	
	private static HashMap<Integer,AttestResult> integerResultHashMap = new HashMap<Integer, AttestResult>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(0, AttestResult.UN_TRUSTED);
			put(1, AttestResult.TRUSTED);
			put(2, AttestResult.UN_KNOWN);
			put(3, AttestResult.TIME_OUT);
			put(4, AttestResult.PENDING);
		}
	};

	private static HashMap<AttestResult, Integer> ResultIntegerHashMap = new HashMap<AttestResult, Integer>();
	static {
		for (Integer integer : integerResultHashMap.keySet()) {
			ResultIntegerHashMap.put(integerResultHashMap.get(integer), integer);
		}
	}

	/**
	 * Converts a integer into an Action enumeration.
	 * @param i Integer linked to an action.
	 * @return Action enumeration related to an integer.
	 */
	public static AttestResult getResultFromInt(int i) {
		return integerResultHashMap.get(i);
	}

	/**
	 * Converts an Action enumeration into the related integer.
	 * @param action Enumeration value.
	 * @return Integer related to the enumeration.
	 */
	public static int getIntFromResult(AttestResult result) {
		return ResultIntegerHashMap.get(result);
	}
	
	public static String getStringFromInt(int i){
		String trust_lvl = "";
		
		switch(i){
			case 0 :
				trust_lvl = "untrusted";
				break;
			case 1:
				trust_lvl = "trusted";
				break;
			case 2:
				trust_lvl = "unknown";
				break;
			case 3:
				trust_lvl = "timeout";
				break;
			case 4:
				trust_lvl = "pending";
				break;
		}
		return trust_lvl;
	}
}
