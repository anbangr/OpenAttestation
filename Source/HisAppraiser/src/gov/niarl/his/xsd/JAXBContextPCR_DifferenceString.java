package gov.niarl.his.xsd;

/**
 * This class serves as a central location for the context string used to 
 * marshal and un-marshal.
 * @author syelama
 * @version Crossbow
 *
 */
public class JAXBContextPCR_DifferenceString {
	/**
	 * This string is a delimited list of classes used by the marshaler 
	 * and un-marshaler.
	 */
	public static String contextString = getContextString();

	private static String getContextString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("gov.niarl.his.xsd.pcr_difference");
//		stringBuffer.append(":gov.niarl.his.xsd.integrity_Report_v1_0.org.trustedcomputinggroup.xml.schema.core_Integrity_v1_0_1");
		return stringBuffer.toString();
	}
}
