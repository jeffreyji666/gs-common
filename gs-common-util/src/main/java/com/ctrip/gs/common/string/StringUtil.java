package com.ctrip.gs.common.string;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 字符串帮助类。
 * 
 * @author wgji
 */
public final class StringUtil {

    private static final String K_HEX_CHARS = "0123456789abcdefABCDEF";

    private StringUtil() {
    }

    /**
     * 判断字符串长度(字符串全为空格的为false)
     * 
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static Boolean checkStrLength(String str, Integer min, Integer max) {
        return (!StringUtils.isBlank(str) && str.length() >= min && str.length() <= max);
    }

    /**
     * 判断字符串是不是数字
     * 
     * @param str
     * @return
     */
    public static Boolean checkNumber(String str) {
        return !StringUtils.isBlank(str) && str.matches("^\\d+$");
    }

    /**
     * Returns a string with ... appended to the end if the length is greater than the given length param.
     * 
     * @param str
     * @param length
     * @return
     */
    public static String shortenString(String str, int length) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        if (length < 3) {
            return "...";
        }
        // shorted the string to length
        if (str.length() > length) {
            return str.substring(0, length - 3) + "...";
        }
        return str;
    }

    /**
     * Convert byte array into hex string
     * 
     * @param bytes
     * @return
     */
    public static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        StringBuffer b = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; ++i) {
            int hex = bytes[i] & 0xFF;
            b.append(K_HEX_CHARS.charAt(hex >> 4));
            b.append(K_HEX_CHARS.charAt(hex & 0x0f));
        }
        return b.toString();
    }

    /**
     * Convert hex string into byte array
     * 
     * @param hex
     * @return
     */
    public static byte[] hexStringToByteArray(String hex) {
        if (StringUtils.isEmpty(hex)) {
            return new byte[0];
        }

        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i + 1 < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Generates 16 Byte random String
     * 
     * @return unique random string
     */
    public static String get16ByteRandomString() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    /**
     * Convert latin string to utf-8 string
     * 
     * @param source
     * @return
     */
    public static String convertLatin2Utf8(String source) {
        return convertLatin(source, "utf-8");
    }

    /**
     * Convert latin string to specific encoding
     * 
     * @param source
     * @param encoding
     * @return
     */
    public static String convertLatin(String source, String encoding) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        try {
            byte[] sourceBytes = source.getBytes("iso-8859-1");
            return new String(sourceBytes, encoding);
        } catch (Exception e) {
            return source;
        }
    }

    /**
     * Hash the given string and return a byte array
     * 
     * @param s
     * @return
     */
    public static byte[] hash(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(s.getBytes("UTF-8"));
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /**
     * Convenient method to decode a utf-8 url
     * 
     * @param s
     * @return
     */
    public static String urlDecode(String s) {
        return urlDecode(s, "UTF-8");
    }

    /**
     * Decode a url with specific codec
     * 
     * @param s
     * @param codec
     * @return
     */
    public static String urlDecode(String s, String codec) {
        try {
            return URLDecoder.decode(s, codec);
        } catch (UnsupportedEncodingException e) {
            return s;
        } catch (Exception e) {
            return s;
        }
    }

    /**
     * Convenient method to encode a utf-8 url
     * 
     * @param s
     * @return
     */
    public static String urlEncode(String s) {
        return urlEncode(s, "UTF-8");
    }

    /**
     * Encode a url with specific codec
     * 
     * @param s
     * @param codec
     * @return
     */
    public static String urlEncode(String s, String codec) {
        if (s == null) {
            return "";
        }
        try {
            String tmp = URLEncoder.encode(s, codec);
            return tmp.replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
    
    /**
     * split a string by splitter 
     * @param s: string to split
     * @param splitter: splitter to split string s
     * @return: slices
     */

    public static String[] split(String s, String splitter) {
    	return s.split(splitter);
    }
}
