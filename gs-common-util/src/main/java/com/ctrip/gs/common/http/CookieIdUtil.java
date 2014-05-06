package com.ctrip.gs.common.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.ctrip.gs.common.codec.Base62;

/**
 * Cookie Id helper class, for generate cookie id and check it valid.
 * 
 * CookieID由20位字符组成，生成算法如下： - 第1位是算法版本号，目前都为1 - 第2~3位是浏览器，可以是IE、SA (Safari)、FF (Firefox)、CH (Chrome)、OP (Opera)、OT
 * (Others)等。 - 第4~9位是IP地址转换为unsigned int后按BASE 62编码后的表示。 - 第10~20位是11位随机数，使用RandomStringUtils.randomAlphanumeric(11)生成
 * 
 * @author Martin
 * 
 */
public final class CookieIdUtil {

    private static final int IP_LENGTH = 6;

    private static final String VERSION = "1";

    private static final int COOKIE_ID_LENGTH = 20;

    private CookieIdUtil() {
    }

    /**
     * Generate a new Cookie Id.
     * 
     * @param userAgent
     *            The user agent string from client.
     * @param clientIp
     *            The client ip.
     * @return
     */
    public static String generateCookieId(String userAgent, String clientIp) {
        StringBuffer sb = new StringBuffer();
        sb.append(VERSION);
        sb.append(getBrowserShortName(UserAgentUtil.getBrowser(userAgent)[1]));
        String ipEncode = Base62.encode(UserAgentUtil.ipToLong(clientIp));
        ipEncode = StringUtils.rightPad(ipEncode, IP_LENGTH, '0');
        sb.append(ipEncode);
        sb.append(RandomStringUtils.randomAlphanumeric(11));
        return sb.toString();
    }

    /**
     * Generate a new Cookie Id.
     * 
     * @param request
     *            The HttpServletRequest object.
     * @return
     */
    public static String generateCookieId(HttpServletRequest request) {
        return generateCookieId(request.getHeader("User-Agent"), UserAgentUtil.getClientIp(request));
    }

    /**
     * Check whether the given cookieId is valid.
     * 
     * @param cookieId
     * @return
     */
    public static boolean isValidCookieId(String cookieId) {
        if (StringUtils.isEmpty(cookieId) || cookieId.length() != COOKIE_ID_LENGTH) {
            return false;
        } else if (!StringUtils.startsWith(cookieId, VERSION)) {
            return false;
        }
        String browser = StringUtils.substring(cookieId, 1, 3);
        if ("FF".equals(browser) || "CH".equals(browser) || "IE".equals(browser) || "OT".equals(browser)
                || "SA".equals(browser) || "OP".equals(browser)) {
            return true;
        }
        return false;
    }

    private static String getBrowserShortName(String browser) {
        String shortName = "";
        if (StringUtils.isEmpty(browser)) {
            shortName = "OT";
        } else if (browser.indexOf("MSIE") > -1) {
            shortName = "IE";
        } else if (browser.indexOf("Chrome") > -1) {
            shortName = "CH";
        } else if (browser.indexOf("Firefox") > -1) {
            shortName = "FF";
        } else if (browser.indexOf("Safari") > -1) {
            shortName = "SA";
        } else if (browser.indexOf("Opera") > -1) {
            shortName = "OP";
        } else {
            shortName = "OT";
        }
        return shortName;
    }
}
