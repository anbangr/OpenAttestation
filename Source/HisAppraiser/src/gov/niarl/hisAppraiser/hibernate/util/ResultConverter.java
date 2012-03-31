package gov.niarl.hisAppraiser.hibernate.util;

import java.util.HashMap;


public class ResultConverter {
	
	public static enum AttestResult {
		UN_TRUSTED, TRUSTED, UN_KNOWN,TIME_OUT,PENDING
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

}
