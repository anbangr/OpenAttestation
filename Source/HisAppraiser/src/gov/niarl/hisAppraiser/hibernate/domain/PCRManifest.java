/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package gov.niarl.hisAppraiser.hibernate.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java class linked to the PCR_manifest table.
 * @author  intel
 * @version OpenAttestation
 *
 */

@XmlRootElement

public class PCRManifest {
    Long index;
    Integer PCRNumber;
    String PCRValue;
    String PCRDesc;
    Date createTime;
    String createRequestHost;
    Date lastUpdateTime;
    String lastUpdateRequestHost;
    
	public PCRManifest(){}
    
    public PCRManifest(Long index){
    	this.index = index;
    }
    
    public PCRManifest(Long index, Integer number, String value, String desc, Date createTime,
    		String createRequestHost, Date lastUpdateTime, String lastUpdateRequestHost){
    	this.index = index;
    	this.PCRNumber = number;
    	this.PCRValue = value;
    	this.PCRDesc = desc;
    	this.createTime = createTime;
    	this.createRequestHost = createRequestHost;
    	this.lastUpdateTime = lastUpdateTime;
    	this.lastUpdateRequestHost = lastUpdateRequestHost;
    }

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Integer getPCRNumber() {
		return PCRNumber;
	}

	public void setPCRNumber(Integer pCRNumber) {
		PCRNumber = pCRNumber;
	}

	public String getPCRValue() {
		return PCRValue;
	}

	public void setPCRValue(String pCRValue) {
		PCRValue = pCRValue;
	}

	public String getPCRDesc() {
		return PCRDesc;
	}

	public void setPCRDesc(String pCRDesc) {
		PCRDesc = pCRDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	
    public String getCreateRequestHost() {
		return createRequestHost;
	}

	public void setCreateRequestHost(String createRequestHost) {
		this.createRequestHost = createRequestHost;
	}

	public String getLastUpdateRequestHost() {
		return lastUpdateRequestHost;
	}

	public void setLastUpdateRequestHost(String lastUpdateRequestHost) {
		this.lastUpdateRequestHost = lastUpdateRequestHost;
	}

	/**
     * validate 
     * @return
     */
	public String validateDataFormat(){
    	return "";
    }
}
