package com.ctrip.gs.common.codec;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: wgji
 * @date：2014年4月22日 下午7:11:37
 * @comment:
 */

public class MD5 {

    public static long getMD5(String text) {
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] md5hash = md5Digest.digest(text.getBytes(Charset.forName("UTF-8")));
            long hash = 0L;
            for (int i = 0; i < 8; i++) {
                hash = hash << 8 | md5hash[i] & 0x00000000000000FFL;
            }
            return Math.abs(hash);
        } catch (NoSuchAlgorithmException nsae) {
            // Can't happen
            throw new IllegalStateException(nsae);
        }
    }
}
