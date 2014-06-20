package com.rqms.core;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class RQMSStringUtils {

	private static final Pattern sMarkupTagPattern = Pattern.compile("<.*?>");

	private static final Pattern sBRTagPattern = Pattern.compile("<br\\s*\\/?>", Pattern.CASE_INSENSITIVE);

	private static final String CHECK_STRING = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~''''''''''''''''''''''''''\n\r";

	private static final String REPLACE_STRING = "     %          0123456789       ABCDEFGHIJKLMNOPQRSTUVWXYZ      ABCDEFGHIJKLMNOPQRSTUVWXYZ    AAAACCEEEEEEEEIIIIOOUUUUUU'\n\r";

	// Used for conversion of accented character into unaccented character
	// starts
	private static String UPPERCASE_ASCII = "AEIOU" // grave
			+ "AEIOUY" // acute
			+ "AEIOUY" // circumflex
			+ "AON" // tilde
			+ "AEIOUY" // umlaut
			+ "A" // ring
			+ "C" // cedilla
			+ "OU"; // double acute

	private static String UPPERCASE_UNICODE = "\u00C0\u00C8\u00CC\u00D2\u00D9" + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
			+ "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176" + "\u00C3\u00D5\u00D1" + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178" + "\u00C5" + "\u00C7"
			+ "\u0150\u0170";

	private RQMSStringUtils() {
		throw new Error("Contains only static methods");
	}

	public static char charAt(StringBuffer argValue, int argPosition) {
		if (argValue != null && argValue.length() > argPosition) {
			return argValue.charAt(argPosition);
		}
		return ' ';
	}

	public static char charAt(StringBuilder argValue, int argPosition) {
		if (argValue != null && argValue.length() > argPosition) {
			return argValue.charAt(argPosition);
		}
		return ' ';
	}

	public static char charAt(String argValue, int argPosition) {
		if (argValue != null && argValue.length() > argPosition) {
			return argValue.charAt(argPosition);
		}
		return ' ';
	}

	/**
	 * Method for null or blank check
	 * 
	 * @param value
	 *            The value to be checked.
	 * @return true if blank else false.
	 */
	public static boolean isBlank(String value) {
		return isBlank(value, false);
	}

	/**
	 * Method for null or blank check of a String input by removing the whitespace.
	 * 
	 * @param value
	 *            The value to be checked.
	 * @param removeSpace
	 *            Remove leading and trailing whitespace before check.
	 * @return true if blank else false.
	 */
	public static boolean isBlank(String value, boolean removeSpace) {
		return (value == null || (removeSpace ? value.trim().equals("") : value.equals("")));
	}

	/**
	 * This will convert the accented character into the unaccented character.
	 */
	public static String toUpperCaseSansAccent(String accentString) {
		if (accentString == null) {
			return "";
		}

		String accentStringUpper = accentString.toUpperCase();
		StringBuffer unaccent_sb = new StringBuffer();
		int len = accentStringUpper.length();
		for (int i = 0; i < len; i++) {
			char c = charAt(accentStringUpper, i);
			int pos = UPPERCASE_UNICODE.indexOf(c);
			if (pos > -1) {
				unaccent_sb.append(charAt(UPPERCASE_ASCII, pos));
			} else {
				unaccent_sb.append(c);
			}
		}
		return unaccent_sb.toString();
	}

	public static boolean validateURL(String url) {
		String urlPattern = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";
		if (url.matches(urlPattern))
			return true;
		else
			return false;
	}

	public static boolean validateEmail(String inputUrl) {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (inputUrl.matches(emailPattern))
			return true;
		else
			return false;
	}

	public static String removeMarkupTags(String str) {
		if (isBlank(str))
			return str;
		String tmp = sBRTagPattern.matcher(str).replaceAll("' '");
		return sMarkupTagPattern.matcher(tmp).replaceAll(StringUtils.EMPTY);
	}

	public static String replace(String str, String pattern, String replace) {
		if (str == null)
			return "";
		return StringUtils.replace(str, pattern, replace);
	}

	public static String replacedAccentedString(String argVal) {
		int argValLength = 0;
		int replaceAtLocation = 0;
		String tempStr = "";
		String retReplacedStr = "";
		String inArgVal = argVal;
		argValLength = inArgVal.length();
		for (int vCount = 0; vCount < argValLength; vCount++) {
			tempStr = inArgVal.substring(vCount, vCount + 1);
			replaceAtLocation = CHECK_STRING.indexOf(tempStr);

			tempStr = REPLACE_STRING.substring(replaceAtLocation, replaceAtLocation + 1);
			if (!tempStr.equals(""))
				retReplacedStr = retReplacedStr + tempStr;
		}
		return retReplacedStr;
	}

	/**
	 * This method is used to format the Reference File Number for displaying
	 * 
	 * @param referenceFile
	 * @return
	 */
	public static StringBuffer getReferenceFileNumberMaskValue(String referenceFileNumber) {
		StringBuffer maskedValue = new StringBuffer("");
		if (referenceFileNumber.length() == 0)
			return maskedValue;
		char temp[] = referenceFileNumber.toCharArray();

		for (int referenceFileIndex = 0; referenceFileIndex < temp.length; referenceFileIndex++) {
			if (referenceFileIndex == 3) {
				maskedValue.append("-");
			} else if (referenceFileIndex == 5) {
				maskedValue.append("-");
			}
			maskedValue.append(temp[referenceFileIndex]);
		}
		return maskedValue;
	}

	public static String generateRandomString() {
		final String ALPHA_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@#%$";

		StringBuffer sb = new StringBuffer(5);
		for (int i = 0; i < 5; i++) {
			int ndx = (int) (Math.random() * ALPHA_NUM.length());
			sb.append(ALPHA_NUM.charAt(ndx));
		}
		return sb.toString();
	}

	// Aspire 2010/04/14 - Phone number Display Format
	public static StringBuffer getPhoneMaskValue(String phoneNumber) {
		StringBuffer maskedValue = new StringBuffer("");
		if (phoneNumber.length() == 0)
			return maskedValue;
		char temp[] = phoneNumber.toCharArray();
		if (phoneNumber != null && (phoneNumber.contains("(") || phoneNumber.contains(")"))) {
			for (int phoneIndex = 0; phoneIndex < temp.length; phoneIndex++) {
				maskedValue.append(temp[phoneIndex]);
			}
		} else {
			for (int phoneIndex = 0; phoneIndex < temp.length; phoneIndex++) {
				if (phoneIndex == 0) {
					maskedValue.append("(");
				} else if (phoneIndex == 3) {
					maskedValue.append(") ");
				} else if (phoneIndex == 6) {
					maskedValue.append("-");
				} else if (phoneIndex == 10) {
					maskedValue.append(" e ");
				}
				maskedValue.append(temp[phoneIndex]);
			}
		}
		return maskedValue;
	}

	/**
	 * This method is used to format the zip code for displaying.
	 * 
	 * @param zip
	 * @return
	 */
	public static StringBuffer getZipMaskValue(String zip) {
		StringBuffer maskedValue = new StringBuffer("");
		if (zip.length() == 0)
			return maskedValue;
		char temp[] = zip.toCharArray();

		for (int zipIndex = 0; zipIndex < temp.length; zipIndex++) {
			if (zipIndex == 5) {
				maskedValue.append("-");
			}
			maskedValue.append(temp[zipIndex]);
		}
		return maskedValue;
	}

	public static String replaceNullPathValues(String nullPathValue) {
		String valueOfString = nullPathValue.replace("{0}", "empty");
		valueOfString = valueOfString.replace("{}", "empty");
		valueOfString = valueOfString.replace("(?i)null", "empty");
		valueOfString = valueOfString.replace("\\empty", "");
		return valueOfString;
	}

	public static String getValidNumbers(String argString) {
		StringBuilder sb = new StringBuilder();
		int i, len;
		len = argString.length();

		if (len < 1) {
			return "";
		}

		for (i = 0; i < len; i++) {
			char ch = RQMSStringUtils.charAt(argString, i);

			if (((ch >= '0') && (ch <= '9'))) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String replaceAllCaseInsensitive(String str, String pattern, String replace) {
		int slen = str.length();
		int plen = pattern.length();
		int s = 0, e = 0;
		StringBuffer result = new StringBuffer(slen * 2);
		char[] chars = new char[slen];

		if (slen == 0)
			return "";
		if (plen == 0)
			return str;

		String str_lowerCase = str.toLowerCase();
		String pattern_lowerCase = pattern.toLowerCase();

		while ((e = str_lowerCase.indexOf(pattern_lowerCase, s)) >= 0) {
			str.getChars(s, e, chars, 0);
			result.append(chars, 0, e - s).append(replace);
			s = e + plen;
		}

		str.getChars(s, slen, chars, 0);
		result.append(chars, 0, slen - s);

		return result.toString();
	}

	/**
	 * Returns true if the given string starts with "Y" or "y" or "T" or "t" else returns false. <br>
	 * For e.g.: <br>
	 * toBoolean("yes") returns true, toBoolean("no") retruns false <br>
	 * 
	 * 
	 * @param argString
	 *            the string to be checked whether it starts with "Y" or "y" or "T" or "t"
	 * 
	 * @return blooean, true if argString starts with "Y" or "y" or "T" or "t" else returns false. If argString is null or empty then returns false
	 */
	public static boolean toBoolean(String argString) {
		return toBoolean(argString, false);
	}

	/**
	 * Returns true if the given string starts with "Y" or "y" or "T" or "t" else returns false. <br>
	 * For e.g.: <br>
	 * toBoolean("yes") returns true, toBoolean("no") retruns false <br>
	 * 
	 * 
	 * @param argString
	 *            the string to be checked whether it starts with "Y" or "y" or "T" or "t"
	 * 
	 * @return blooean, true if argString starts with "Y" or "y" or "T" or "t" else returns false. If argString is null or empty then returns
	 *         defaultValue
	 */
	public static boolean toBoolean(String argString, boolean defaultValue) {

		// returns true if String is "true" or "yes"
		if (argString == null) {
			return defaultValue;
		}

		if (argString.length() < 1) {
			return defaultValue;
		}

		char c = charAt(argString, 0);

		if ((c == 'y') || (c == 'Y') || (c == 't') || (c == 'T')) {
			return true;
		}

		return false;
	}

	// **************************************************************************************
	// toInt

	/**
	 * Converts the given string into integer if it can else returns 0. <br>
	 * <br>
	 * For e.g.: <br>
	 * toInt("123") returns 123, toInt("a") retruns 0 <br>
	 * 
	 * 
	 * @param argString
	 *            the string to be converted into integer
	 * 
	 * @return int, the integer equivalent of the strign if it can be converted else 0.
	 */
	public static int toInt(String argString) {
		try {
			return Integer.parseInt(argString);
		} catch (java.lang.NumberFormatException nfe) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	// **************************************************************************************
	// toshort
	/**
	 * Converts the given string into short if it can else returns 0. <br>
	 * <br>
	 * For e.g.: <br>
	 * toInt("123") returns 123, toLong("a") retruns 0 <br>
	 * 
	 * 
	 * @param argString
	 *            the string to be converted into short
	 * 
	 * @return Long, the short equivalent of the strign if it can be converted else 0.
	 */
	public static Short toShort(String argString) {
		try {
			return Short.parseShort(argString);
		} catch (java.lang.NumberFormatException nfe) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	// **************************************************************************************
	// toLong

	/**
	 * Converts the given string into Long if it can else returns 0. <br>
	 * <br>
	 * For e.g.: <br>
	 * toInt("123") returns 123, toLong("a") retruns 0 <br>
	 * 
	 * 
	 * @param argString
	 *            the string to be converted into Long
	 * 
	 * @return Long, the Long equivalent of the strign if it can be converted else 0.
	 */
	public static Long toLong(String argString) {
		try {
			return Long.parseLong(argString);
		} catch (java.lang.NumberFormatException nfe) {
			return (long) 0;
		} catch (Exception e) {
			return (long) 0;
		}
	}

	// **************************************************************************************
	// toDouble

	/**
	 * Converts the given string into double data.
	 * <p>
	 * 
	 * Returns 0 if the given string does not contain double data or it cannot be converted. <br>
	 * <br>
	 * For e.g.: <br>
	 * toDouble("123.456") returns 123.456, toDouble("a") retruns 0 <br>
	 * 
	 * 
	 * @param argString
	 *            the string to be converted into double
	 * 
	 * @return double, equivalent double for argString, if argString cannot be converted to double data then 0.
	 */
	public static double toDouble(String argString) {
		try {
			return Double.parseDouble(argString);
		} catch (java.lang.NumberFormatException nfe) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Replace characters that are not supported by xml Supported char ranges: #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
	 * 
	 * @param xml
	 * @return
	 */
	public static String escapeInvalidXMLChars(String xml) {
		if (xml == null) {
			return null;
		}
		String[] eomChar = { new String(new char[] { '\u0019' }), new String(new char[] { '\u0000' }) };
		// Escape the End of Medium (u0019) char
		xml = xml.replaceAll(eomChar[0], "0x19");
		// Aspire 2011/12/20 (#23921) - Escape the null character.
		xml = xml.replaceAll(eomChar[1], "0x0");
		return xml;
	}

	/**
	 * Makes the given string java script safe and returns it.
	 * <p>
	 * <br>
	 * <br>
	 * It does so by replacing line-feed and carriage-return characters with space, <br>
	 * replacing tab and double-quotes characters with proper java script tab and double-quote characters <br>
	 * and if argSingleQuoteFlag is true replacing single-quote character with java script single quote character. <br>
	 * <br>
	 * For e.g.: <br>
	 * <br>
	 * strRet = CsdcUtility.javaScriptSafe(theString[argRow], true); <br>
	 * strRet = (strRet == null) ? "" : strRet.trim(); <br>
	 * 
	 * 
	 * @param argString
	 *            the given string
	 * @param argSingleQuoteFlag
	 *            true means single-quote character will be replaced by java script safe single-quote character
	 * 
	 * @return String, which is safe for java script
	 */
	public static String javaScriptSafe(String argString, boolean argSingleQuoteFlag) {
		if (argString == null)
			return "";
		String[] searchArr = { "\r", "\n", "\\", "\"", argSingleQuoteFlag ? "'" : null };
		String[] replaceArr = { " ", " ", "\\\\", "\\\"", "\\\'" };

		return StringUtils.replaceEach(argString, searchArr, replaceArr);
	}
	
	/**
	 * Converts the given string into tiny int data. Returns 0 if the given
	 * string does not contain tiny int data or it cannot be converted.
	 * 
	 * @param argString
	 * @return
	 */
	public static byte toTinyInt(String argString) {
		try {
			return NumberUtils.toByte(argString);
		} catch (Exception e) {
			return 0;
		}
	}

}
