package com.ctrip.gs.common.codec;

/**
 * BASE62编码转换工具类，即用52个英文字符和0-9进行编码。
 * 
 * @author wgji
 * 
 */
public final class Base62 {

    private static final char[] ENCODE_TABLE = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
        '2', '3', '4', '5', '6', '7', '8', '9' };

    private Base62() {
    }

    /**
     * 将长整数编码为字符串。
     * 
     * @param num
     * @return
     */
    public static String encode(long num) {
        if (num < 1) {
            throw new IllegalArgumentException("num must be greater than 0.");
        }
        StringBuilder sb = new StringBuilder();
        for (long i = num; i > 0; i /= 62) {
            sb.append(ENCODE_TABLE[(int) (i % 62)]);
        }

        return sb.toString();
    }

    /**
     * 将字符串解码为长整数。
     * 
     * @param str
     * @return
     */
    public static long decode(String str) {
        String s = str.trim();
        if (s.length() < 1) {
            throw new IllegalArgumentException("str must not be empty.");
        }
        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            result += ENCODE_TABLE[i] * Math.pow(62, i);
        }

        return result;
    }

}
