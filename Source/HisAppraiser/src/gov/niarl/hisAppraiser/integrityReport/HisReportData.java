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

import gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.integrity_Report_v1_0.QuoteDataType;
import gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.integrity_Report_v1_0.QuoteType;
import gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.integrity_Report_v1_0.ReportType;
import gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.integrity_Report_v1_0.PcrCompositeType.PcrValue;
import gov.niarl.hisAppraiser.util.HisUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

/**
 * This class generates data needed during report evaluation. 
 * @author syelama  
 * @version Crossbow
 *
 */
public class HisReportData {
	private static Logger logger = Logger.getLogger(HisReportData.class);
	private ReportType report;
	private byte[] pcrSelectSubstitute = null;
	private Integer pcrSizeOfSelectSubstitute = null;
	private Hashtable<Integer, PcrValue> pcrValuesTable = null;

	/**
	 * Initialize with the report.
	 * @param reportString XML report string.
	 */
	public HisReportData(String reportString) {
		report = HisReportUtil.unmarshallReport(reportString);
	}

	/**
	 * Retrieve quote data object from report.
	 * @return List of quote data
	 */
	public List<QuoteDataType> getQuoteData() {
		return report.getQuoteData();
	}

	/**
	 * Retrieve first quote object from report.
	 * @return Quote
	 */
	public QuoteType getQuote() {
		return ((QuoteDataType) (report.getQuoteData().get(0))).getQuote();
	}

	/**
	 * PCR select truncated or padded based on the size of select. 
	 * @return PCR select as byte[]
	 */
	public byte[] generatePcrSelect() {
		byte[] pcrSelect = null;
		if (pcrSelectSubstitute != null) {
			pcrSelect = pcrSelectSubstitute;
		} else {
			pcrSelect = getQuote().getPcrComposite().getPcrSelection().getPcrSelect();
		}
		//truncate or pad
		int length = getPcrSizeOfSelect() - pcrSelect.length;
		StringBuffer sb = new StringBuffer();
		String stringPcrSelect = HisUtil.hexString(pcrSelect);
		if (length < 0) {
			stringPcrSelect = stringPcrSelect.substring(0, getPcrSizeOfSelect() * 2);
		} else {
			for (int i = 0; i < length; i++) {
				stringPcrSelect = sb.append("00").toString();
			}
		}
		return HisUtil.unHexString(stringPcrSelect);
	}

	/**
	 * PCR select from the report.   
	 * @return PCR select as byte[]
	 */
	public byte[] getOriginalPcrSelect() {
		return getQuote().getPcrComposite().getPcrSelection().getPcrSelect();
	}

	/**
	 * Retrieve PCR selected count from generated PCR select. 
	 * @return Number of selected PCRs
	 */
	public int generatePcrSelectedCount() {
		byte[] bytes = generatePcrSelect();
		int count = 0;
		for (int i = 0; i < bytes.length; i++) {
			count = count + HisUtil.getSelected(bytes[i]).size();
		}
		return count;
	}

	/**
	 * Generates a list of selected PCRs.
	 * @return Sorted list of selected selected PCRs.
	 */
	public SortedSet<Integer> generatePcrSelectedPositions() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		byte[] bytes = generatePcrSelect();
		for (int i = 0; i < bytes.length; i++) {
			for (Integer integer : HisUtil.getSelected(bytes[i])) {
				arrayList.add(integer + (i * 8));
			}
		}
		Collections.sort(arrayList);
		return Collections.unmodifiableSortedSet(new TreeSet<Integer>(arrayList));
	}

	/**
	 * Supply substitute value for the PCR select. 
	 * @param pcrSelect
	 */
	public void substitutePcrSelect(byte[] pcrSelect) {
		this.pcrSelectSubstitute = pcrSelect;
	}

	/**
	 * Retrieve the currently used size of select. 
	 * @return the pcrSizeOfSelect
	 */
	public int getPcrSizeOfSelect() {
		if (pcrSizeOfSelectSubstitute == null) {
			return getQuote().getPcrComposite().getPcrSelection().getSizeOfSelect();
		} else {
			return pcrSizeOfSelectSubstitute;
		}
	}

	/**
	 * Supply substitute value for the PCR size of select.
	 * @param pcrSizeOfSelect the pcrSizeOfSelect to set
	 */
	public void substitutePcrSizeOfSelect(int pcrSizeOfSelect) {
		this.pcrSizeOfSelectSubstitute = pcrSizeOfSelect;
	}

	/**
	 * Number of PCRs in the report.
	 * @return The number of PCR values.
	 */
	public int getPcrValueCount() {
		return getQuote().getPcrComposite().getPcrValue().size();
	}

	/**
	 * Populate the PCR hash table.
	 */
	private void populatePcrValues() {
		pcrValuesTable = new Hashtable<Integer, PcrValue>();
		for (int i = 0; i < getQuote().getPcrComposite().getPcrValue().size(); i++) {
			PcrValue pcrValuePcrComposite = (PcrValue) getQuote().getPcrComposite().getPcrValue().get(i);
			pcrValuesTable.put(pcrValuePcrComposite.getPcrNumber().intValue(), pcrValuePcrComposite);
		}
	}

	/**
	 * Get PCR number i. populate PCR Values
	 * @param i PCR number
	 * @return PCR value
	 * @throws RuntimeException if no PCR value exists.
	 */
	public byte[] getPcrValue(int pcrNumber) {
		if (pcrValuesTable == null) {
			populatePcrValues();
		}
		PcrValue key = pcrValuesTable.get(pcrNumber);
		if (key != null) 
			return key.getValue();
		
		return null;	
	}

	/**
	 * The length of each PCR.
	 * @return The length of each PCR
	 */
	public int getPcrValueSize() {
		return getQuote().getPcrComposite().getValueSize().intValue();
	}

	/**
	 * Possible values of PCR number.
	 * @return SortedSet of integers.
	 */
	public SortedSet<Integer> getPossiblePcrs() {
		if (pcrValuesTable == null) {
			populatePcrValues();
		}
		return Collections.unmodifiableSortedSet(new TreeSet<Integer>(pcrValuesTable.keySet()));
	}

	/**
	 * The fixed string related to the quote.
	 * @return Text value of the fixed string.
	 */
	public String getQuoteFixedString() {
		return getQuote().getQuoteInfo().getFixed();
	}

	/**
	 * Digest stored in the report.
	 * @return Digest value.
	 */
	public byte[] getDigest() {
		return getQuote().getQuoteInfo().getDigestValue();
	}

	/**
	 * Nonce stored in the report.
	 * @return Nonce value.
	 */
	public byte[] getNonce() {
		return getQuote().getQuoteInfo().getExternalData();
	}

	/**
	 * Machine name stored in the report.
	 * @return Machine name value.
	 */
	public String getMachineName() {
		QuoteDataType quoteData = ((QuoteDataType) (report.getQuoteData().get(0)));
		return ((JAXBElement<String>) quoteData.getTpmSignature().getKeyInfo().getContent().get(0)).getValue();
	}

	/**
	 * Signature stored in the report.
	 * @return Signature value.
	 */
	public byte[] getSignature() {
		QuoteDataType quoteData = ((QuoteDataType) (report.getQuoteData().get(0)));
		return quoteData.getTpmSignature().getSignatureValue().getValue();
	}

	/**
	 * Create the quote version used at the beginning of the signed data.
	 * @return Quote version data at the beginning of the signed data.
	 */
	public byte[] getTpmQuoteVersion() {
		String tpmQuoteVersionString = HisUtil.hexString(HisUtil.intToByteArray(getQuote().getQuoteInfo().getVersionMajor(), 1));
		tpmQuoteVersionString = tpmQuoteVersionString + HisUtil.hexString(HisUtil.intToByteArray(getQuote().getQuoteInfo().getVersionMinor(), 1));
		tpmQuoteVersionString = tpmQuoteVersionString + HisUtil.hexString(HisUtil.intToByteArray(getQuote().getQuoteInfo().getVersionRevMajor(), 1));
		tpmQuoteVersionString = tpmQuoteVersionString + HisUtil.hexString(HisUtil.intToByteArray(getQuote().getQuoteInfo().getVersionRevMinor(), 1));
		return HisUtil.unHexString(tpmQuoteVersionString);
	}

	/**
	 * Retrieve the ID stored in the XML integrity report.
	 * @return The unique ID string found in an XML integrity report.
	 */
	public String getReportID() {
		return report.getID();
	}

	/**
	 * Compare digest with the report digest. 
	 * @return True if equal, false otherwise.
	 */
	public boolean compareDigest(byte[] digest) {
		return HisUtil.hexString(digest).equalsIgnoreCase(HisUtil.hexString(getDigest()));
	}
}