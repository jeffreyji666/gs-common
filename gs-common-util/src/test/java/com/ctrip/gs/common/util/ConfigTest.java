package com.ctrip.gs.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: wgji
 * @date：2014年4月10日 下午3:29:57
 * @comment:
 */
public class ConfigTest {
    @Test
    public void testConfig() {
        System.out.println(Config.getString("test"));
        Assert.assertEquals("uat", Config.getString("test"));
    }
}
