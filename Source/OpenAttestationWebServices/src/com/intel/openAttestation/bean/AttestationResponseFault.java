/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.intel.openAttestation.bean;

import javax.xml.bind.annotation.XmlRootElement;

//import com.intel.openAttestation.bean.FaultCode;
//import com.intel.openAttestation.bean.FaultName;

@XmlRootElement
public class AttestationResponseFault {
	int code;
	String name;
	String message;
	String detail;
	
	public AttestationResponseFault(){}
	
	public AttestationResponseFault (int code){
		this.code= code;
		switch (code){
			case 400:
				this.name = FaultName.FAULT_BAD_REQUEST;
				break;
			case 500:
				this.name = FaultName.FAULT_ATTEST_ERROR;
				break;
			case 401:
				this.name = FaultName.FAULT_UNAUTH;
				break;
			case 404:
				this.name = FaultName.FAULT_ITEM_NOT_FOUND;
				break;
			default:
				this.name = "UnknownFault";
		}
	}
	
	public AttestationResponseFault(String name){
		this.name = name;
		if (name.equals(FaultName.FAULT_UNAUTH))
			this.code = FaultCode.FAULT_401;
		else if (name.equals(FaultName.FAULT_BAD_REQUEST))
			this.code = FaultCode.FAULT_400;
		else if (name.equals(FaultName.FAULT_ATTEST_ERROR))
			this.code = FaultCode.FAULT_500;
		else if (name.equals(FaultName.FAULT_ITEM_NOT_FOUND))
			this.code = FaultCode.FAULT_404;

		else
			this.code = 0;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public static class FaultCode{
		public static int FAULT_400 = 400;
		public static int FAULT_401 = 401;
		public static int FAULT_404 = 404;
		public static int FAULT_500 = 500;
	}
	public static class FaultName{
		public static String FAULT_ATTEST_ERROR = "AttestationServersFault";
		public static String FAULT_UNAUTH = "Unauthorized";
		public static String FAULT_BAD_REQUEST = "BadRequest";
		public static String FAULT_ITEM_NOT_FOUND = "ItemNotFound";
	}
}
