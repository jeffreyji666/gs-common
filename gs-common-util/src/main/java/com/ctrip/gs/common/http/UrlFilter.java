package com.ctrip.gs.common.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * URL过滤器，用于过滤一些对页面本身无意义的参数，例如GA的统计参数等。
 * 
 * filterParams是正则表达式数组，例如{"^utm_.*$", "lang", "^jtss.*$"}。过滤时会对URL的每个参数分别和
 * filterParams里的正则表达式进行匹配，如果匹配成功则此参数会被过滤掉。过滤完成后，会对剩下的参数排序并重新组装URL。
 * 
 * @author zyeming
 * 
 */
public class UrlFilter {
    private String[] filterParams;

    /**
     * 构造方法。
     */
    public UrlFilter() {
    }

    /**
     * 构造方法。
     * 
     * @param filterParams
     *            用于过滤的正则表达式数组。
     */
    public UrlFilter(String[] filterParams) {
        this.filterParams = filterParams;
    }

    /**
     * 过滤URL。
     * 
     * @param url
     *            需要过滤的URL
     * @return 过滤后的URL
     */
    public String doFilter(String url) {
        String u = url;
        if (filterParams == null || filterParams.length < 1) {
            return u;
        }

        // remove hash part
        int pos = u.indexOf('#');
        if (pos > 0) {
            u = u.substring(0, pos);
        }

        // get the query params
        pos = u.indexOf('?');
        if (pos < 0) {
            return u;
        }
        String urlNoParams = u.substring(0, pos);
        String[] params = u.substring(pos + 1).split("&");

        // filter the query params
        List<String> list = new ArrayList<String>(params.length);
        for (int i = 0; i < params.length; i++) {
            if (StringUtils.isEmpty(params[i])) {
                continue;
            }

            String[] tmp = params[i].split("=");
            if (tmp.length > 0) {
                boolean shouldFilter = false;
                for (String filterParam : filterParams) {
                    if (tmp[0].matches(filterParam)) {
                        shouldFilter = true;
                        break;
                    }
                }
                if (!shouldFilter) {
                    list.add(params[i]);
                }
            }
        }

        // sort the filtered params and combine them together
        StringBuilder result = new StringBuilder(urlNoParams);
        if (list.size() > 0) {
            Collections.sort(list);
            result.append('?');
            for (String param : list) {
                result.append(param).append('&');
            }
            return result.substring(0, result.length() - 1).toString();
        } else {
            return result.toString();
        }
    }

    public String[] getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(String[] filterParams) {
        this.filterParams = filterParams;
    }
}
