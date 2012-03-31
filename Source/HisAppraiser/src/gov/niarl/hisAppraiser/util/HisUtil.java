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
package gov.niarl.hisAppraiser.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class is a central location for utility functions used throughout 
 * HIS projects. 
 * @author syelama
 * @version Crossbow
 *
 */
public class HisUtil {
	private static Provider SECURITY_PROVIDER = new BouncyCastleProvider();

	private static boolean BOUNCY_CASTLE_PROVIDER_LOADED = false;

	static {
		loadBouncyCastleProvider();
	}

	/**
	 * If not already loaded, load the Java Bouncy Castle security provider.
	 */
//	public static synchronized void loadBouncyCastleProvider() {
	public static synchronized void loadBouncyCastleProvider() {
		if (BOUNCY_CASTLE_PROVIDER_LOADED) {
			return;
		} else {
			Security.removeProvider(SECURITY_PROVIDER.getName());
			Security.addProvider(SECURITY_PROVIDER);
			BOUNCY_CASTLE_PROVIDER_LOADED = true;
		}
	}

	/**
	 * @return
	 */
	public static Provider getProvider() {
		return SECURITY_PROVIDER;
	}

	/**
	 * Blocking read of an entire input stream into a byte array.
	 * @param inputStream Input stream to be read.
	 * @return Byte array contents of an input stream.
	 */
	public static byte[] InputStreamToByteArray(InputStream inputStream) {
		try {

			int i;
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			while ((i = inputStream.read()) != -1) {
				byteArrayOutputStream.write(i);
			}

			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}

		}
	}

	/**
	 * Removes white space from a string.
	 * @param string String from which white space will be removed.
	 * @return String without white space.
	 */
	public static String removeWhiteSpace(String string) {
		string = string.replace("\r", "");
		string = string.replace("\n", "");
		string = string.replace("\t", "");
		string = string.replace(" ", "");
		return string;
	}

	/**
	 * Turns a hexadecimal string representation to a byte array.  
	 * @param string Hexadecimal formatted string.
	 * @return Byte array generated from a hexadecimal string representation
	 */
	public static byte[] unHexString(String string) {

		string = removeWhiteSpace(string);
		string = string.toUpperCase().replace("0X", "");

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		for (int i = 0; i < string.length(); i += 2) {
			byteArrayOutputStream.write(Integer.parseInt(string.substring(i, i + 2), 16));
		}

		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Turn a byte array into a hexadecimal string representation. 
	 * @param byteArray Byte array to be converted into a hexadecimal  
	 * string representation.
	 * @return A string containing a hexadecimal representation of a byte 
	 * array without space or 0x's.
	 */
	public static String hexString(byte[] byteArray) {
		String returnstring = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			Integer integer = byteArray[i] < 0 ? byteArray[i] + 256 : byteArray[i];
			String integerString = Integer.toString(integer, 16);
//			returnstring += integerString.length() == 1 ? "0" + integerString : integerString;
			returnstring = sb.append(integerString.length() == 1 ? "0" + integerString : integerString).toString();
		}

		return returnstring.toUpperCase();
	}

	/**
	 * Changes a byte array which is signed into an int array with values 
	 * ranging from 0 to 255
	 * @param byteArray Byte array to be converted to an unsigned      
	 * representation.
	 * @return An integer array with values ranging from 0 to 255.     
	 */
	public static int[] byteArrayToUnsignedIntArray(byte[] byteArray) {
		int[] ints = new int[byteArray.length];

		for (int i = 0; i < byteArray.length; i++) {
			ints[i] = byteArray[i] < 0 ? byteArray[i] + 256 : byteArray[i];
		}

		return ints;
	}

	/**
	 * Generate a secure byte array of a given length.
	 * @param length Length of the needed byte array.
	 * @return Byte array of randomly generated numbers.
	 */
	public static byte[] generateSecureRandom(int length) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[length];
			random.nextBytes(bytes);
			return bytes;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Converts an integer to a byte array of given length
	 * @param input Integer to be converted
	 * @param arrayLength Length of the byte array to be returned, padded 
	 * with zeros if necessary.
	 * @return Byte array representation.
	 * @throws RuntimeException if the input integer is larger than the byte
	 * array can hold.
	 */
	public static byte[] intToByteArray(int input, int arrayLength) {
		String string = Integer.toHexString(input);
		string = string.length() % 2 == 1 ? "0" + string : string;
		int length = arrayLength - (string.length() / 2);
		if (length < 0) {
			throw new RuntimeException("Integer exceeds length.");
		} else {
			for (int i = 0; i < length; i++) {
				string = "00" + string;
			}
		}
		return HisUtil.unHexString(string);
	}

	/**
	 * Get the sorted selected positions in a byte with the left most
	 * position as zero.
	 * @param input Byte to be evaluated
	 * @return Array of integer positions.
	 */
	public static SortedSet<Integer> getSelected(byte input) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		byte mask = 0x01;
		for (int i = 0; i <= 7; i++) {
			int value = (input >>> i) & mask;
			if (value == 1) {
				arrayList.add(7 - i);
			}
		}
		Collections.sort(arrayList);
		return Collections.unmodifiableSortedSet(new TreeSet<Integer>(arrayList));
	}
}