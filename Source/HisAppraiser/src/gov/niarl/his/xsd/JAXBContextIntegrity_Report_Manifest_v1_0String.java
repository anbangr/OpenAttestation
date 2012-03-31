package gov.niarl.his.xsd;

/**
 * This class serves as a central location for the context string used to 
 * marshal and un-marshal.
 * @author syelama
 * @version Crossbow
 *
 */
public class JAXBContextIntegrity_Report_Manifest_v1_0String {
	/**
	 * This string is a delimited list of classes used by the marshaler 
	 * and un-marshaler.
	 */
	public static String contextString = getContextString();

	private static String getContextString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("gov.niarl.his.xsd.integrity_Report_v1_0.org.w3._2000._09.xmldsig");
		stringBuffer.append(":gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.core_Integrity_v1_0_1");
		stringBuffer.append(":gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.integrity_Report_v1_0");
		return stringBuffer.toString();
	}
}
