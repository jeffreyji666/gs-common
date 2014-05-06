package com.ctrip.gs.common.codec;

import java.io.ByteArrayOutputStream;

/**
 * BASE62 encoder
 * 
 * @author wgji
 */
public final class BASE62Encoder {

    private static char[] encodes = "ru7FXxhlBJSR1tMcPbWi9Dp5ZKk4C6dj0/2A3Tm+ao8QegnGfIEHNYsywzULOvqV".toCharArray();

    private static byte[] decodes = new byte[256];

    static {
        for (int i = 0; i < encodes.length; i++) {
            decodes[encodes[i]] = (byte) i;
        }
    }
    
    private BASE62Encoder() { 
        
    }

    public static String encode(String data) {
        byte[] dataBytes = data.getBytes();
        StringBuffer sb = new StringBuffer(dataBytes.length * 2);
        int pos = 0, val = 0;
        for (int i = 0; i < dataBytes.length; i++) {
            val = (val << 8) | (dataBytes[i] & 0xFF);
            pos += 8;
            while (pos > 5) {
                char c = encodes[val >> (pos -= 6)];
                sb.append(c == 'i' ? "ia" : c == '+' ? "ib" : c == '/' ? "ic" : c);
                val &= ((1 << pos) - 1);
            }
        }
        if (pos > 0) {
            char c = encodes[val << (6 - pos)];
            sb.append(c == 'i' ? "ia" : c == '+' ? "ib" : c == '/' ? "ic" : c);
        }
        return sb.toString();
    }

    public static String decode(String data) {
        char[] dataBytes = data.toCharArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(dataBytes.length);
        int pos = 0, val = 0;
        for (int i = 0; i < dataBytes.length; i++) {
            char c = dataBytes[i];
            if (c == 'i') {
                c = dataBytes[++i];
                c = c == 'a' ? 'i' : c == 'b' ? '+' : c == 'c' ? '/' : dataBytes[--i];
            }
            val = (val << 6) | decodes[c];
            pos += 6;
            while (pos > 7) {
                baos.write(val >> (pos -= 8));
                val &= ((1 << pos) - 1);
            }
        }
        return new String(baos.toByteArray());
    }

}
