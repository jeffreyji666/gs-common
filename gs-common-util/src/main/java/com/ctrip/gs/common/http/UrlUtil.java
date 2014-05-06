package com.ctrip.gs.common.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

import com.ctrip.gs.common.string.StringUtil;

/**
 * URL处理帮助类。
 * 
 * @author wgji
 * 
 */
public final class UrlUtil {

    private UrlUtil() {
    }

    /**
     * Checks if the given string is an URL (must start with http:// or https://)
     * 
     * @param url
     * @return
     */
    public static boolean isUrlHttp(String url) {
        if (url == null) {
            return false;
        }
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        try {
            String regex = "^(http|https)\\://[\\w\\-_]+(\\.[\\w\\-_]+)+"
                    + "([\\w\\-\\.,@?^=%&amp;:/~\\+#\u4e00-\u9fa5]*[\\w\\-\\@?^=%&amp;/~\\+#\u4e00-\u9fa5])?";
            return StringUtils.trim(url).matches(regex);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the given string is an URL (with optional http:// or https:// in the front)
     * 
     * @param url
     * @return
     */
    public static boolean isUrlOptionalHttp(String url) {
        try {
            String regex = "(^(http|https)\\://)?[\\w\\-_]+(\\.[\\w\\-_]+)+"
                    + "([\\w\\-\\.,@?^=%&amp;:/~\\+#\u4e00-\u9fa5]*[\\w\\-\\@?^=%&amp;/~\\+#\u4e00-\u9fa5])?";
            return url.matches(regex);
        } catch (PatternSyntaxException p) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the full domain name from the URL (http://www.ctrip.com will return www.ctrip.com)
     * 
     * @param url
     * @return
     */
    public static String getFullDomainName(String url) {
        if (url == null) {
            return null;
        }
        try {
            // extract the full domain:
            URL urlObj = new URL(url);
            String domainFull = urlObj.getHost();
            if (domainFull == null || domainFull.isEmpty()) {
                return url;
            } else {
                return domainFull.trim().toLowerCase();
            }
        } catch (MalformedURLException e) {
            return url;
        } catch (PatternSyntaxException p) {
            return url;
        } catch (IllegalStateException ise) {
            return url;
        } catch (IndexOutOfBoundsException ioe) {
            return url;
        }
    }

    /**
     * Gets the full domain name from the URL (with http or https prefix)
     * 
     * @param url
     * @return
     */
    public static String getFullDomainNameWithHttpPrefix(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }

        String fullDomainName = getFullDomainName(url);
        if (isUrlHttp(url)) {
            if (url.startsWith("https\\://")) {
                fullDomainName = "https://" + fullDomainName;
            } else {
                fullDomainName = "http://" + fullDomainName;
            }
        } else if (StringUtils.isNotBlank(fullDomainName)) {
            fullDomainName = "http://" + fullDomainName;
        }
        return fullDomainName;
    }

    /**
     * Get full url with http prefix
     * 
     * @param url
     * @return
     */
    public static String getFullUrlWithPrefix(String url) {
        return isUrlHttp(url) ? url : "http://" + url;
    }

    /**
     * Checks if the given string is an ip address
     * 
     * @param ip
     * @return whether the string is an ip address
     */
    public static boolean isIpAddress(String ip) {
        String regex = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        if (ip.matches(regex)) {
            return true;
        }
        return false;
    }

    /**
     * Parse the URL query string into map
     * 
     * @param query
     *            the query string
     * @return
     */
    public static Map<String, String> getQueryMap(String query) {
        Map<String, String> qmap = new HashMap<String, String>();
        if (StringUtils.isEmpty(query)) {
            return qmap;
        }
        String[] params = query.split("&");
        for (String param : params) {
            String[] pair = param.split("=");
            String name = pair[0];
            String value = "";
            if (pair.length > 1) {
                value = StringUtil.urlDecode(pair[1]);
            }
            qmap.put(name, value);
        }
        return qmap;
    }

    /**
     * Parse the URL's query string into map
     * 
     * @param url
     *            the url which contains the query string
     * @return
     */
    public static Map<String, String> getQueryMapFromUrl(String url) {
        URL theUrl;
        try {
            theUrl = new URL(url);
        } catch (MalformedURLException e) {
            return new HashMap<String, String>();
        }

        return getQueryMap(theUrl.getQuery());
    }

    /**
     * Parse the URL and get the value from query
     * 
     * @param url
     *            the url
     * @param name
     *            the query name
     * @return
     */
    public static String getQueryValueFromUrl(String url, String name) {

        URL theUrl;
        try {
            theUrl = new URL(url);
        } catch (MalformedURLException e) {
            return "";
        }

        String query = theUrl.getQuery();
        if (query != null && !query.isEmpty()) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (name.equals(pair[0]) && pair.length > 1) {
                    return pair[1];
                }
            }
        }
        return "";
    }

    /**
     * Return the hash part of an URL
     * 
     * @param url
     * @return
     */
    public static String getHashFromUrl(String url) {
        int pos = url.indexOf('#');
        if (pos > 0) {
            return url.substring(pos + 1);
        } else {
            return "";
        }
    }

    /**
     * Remove the protocol (http or https) and the trailing slash from URL
     * 
     * @param url
     * @return The url without protocal and trailing slash
     */
    public static String removeProtocolAndTrailingSlash(String url) {
        return removeTrailingSlash(removeProtocal(url));
    }

    /**
     * Remove protocol from url
     * 
     * @param url
     * @return
     */
    public static String removeProtocal(String url) {
        String u = url;
        int in = u.indexOf("://");
        if (in > 0) {
            u = u.substring(in + 3);
        }
        return u;
    }

    /**
     * Remove trailing slash from url
     * 
     * @param url
     * @return
     */
    public static String removeTrailingSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        } else {
            return url;
        }
    }

    /**
     * Remove preceding slash from url
     * 
     * @param url
     * @return
     */
    public static String removePrecedingSlash(String url) {
        if (url.startsWith("/")) {
            return url.substring(1);
        } else {
            return url;
        }
    }
}
