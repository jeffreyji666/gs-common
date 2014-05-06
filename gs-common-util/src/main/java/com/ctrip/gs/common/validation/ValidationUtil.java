package com.ctrip.gs.common.validation;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证帮助类。
 * 
 * @author wgji
 * 
 */
public final class ValidationUtil {

    private static Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+.[a-zA-Z]{2,6}$");
    private static Pattern namePattern = Pattern.compile("^[a-zA-Z0-9._ \u4e00-\u9fa5%-&amp;:@()*,-?+!#$]+");
    private static Pattern ipPattern = Pattern
            .compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");

    private ValidationUtil() {
    }

    /**
     * Validates the locale string. Defaults to zh
     * 
     * @param locale
     * @return
     */
    public static Locale validateLocale(String locale) {
        if (locale.equalsIgnoreCase("en")) {
            return Locale.ENGLISH;
        } else if (locale.equalsIgnoreCase("zh_TW")) {
            return Locale.TAIWAN;
        }
        return Locale.CHINA;
    }

    /**
     * Validate the email address
     * 
     * @param inputEmail
     * @return whether the input is a valid email
     */
    public static boolean validateEmail(String inputEmail) {
        Matcher m = emailPattern.matcher(inputEmail);
        return m.matches();
    }

    /**
     * Validate the user name
     * 
     * @param inputName
     * @return whether the input is a valid name
     */
    public static boolean validateName(String inputName) {
        Matcher m = namePattern.matcher(inputName);
        return m.matches();
    }

    /**
     * Checks if the given string is an ip address
     * 
     * @param inputIp
     * @return whether the string is an ip address
     */
    public static boolean validateIp(String inputIp) {
        Matcher m = ipPattern.matcher(inputIp);
        return m.matches();
    }
}
