package com.ctrip.gs.common.number;

/**
 * @author: wgji
 * @date：2014年4月1日 上午11:09:20
 * @comment:
 */
public class NumberUtil {
    /**
     * 判断整形是否为null或0
     * 
     * @param value
     * @return
     */
    public static Boolean isEmpty(Integer value) {
        return (value == null || value == 0);
    }

    /**
     * 判断整形是否为null或0
     * 
     * @param value
     * @return
     */
    public static Boolean isEmpty(Long value) {
        return (value == null || value == 0);
    }
}
