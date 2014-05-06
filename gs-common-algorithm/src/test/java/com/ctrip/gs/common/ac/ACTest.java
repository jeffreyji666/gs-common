package com.ctrip.gs.common.ac;

import java.util.Iterator;

import org.junit.Test;

/**
 * @author: wgji
 * @date：2014年3月31日 上午11:50:02
 * @comment:
 */

public class ACTest {
    @Test
    public void testAC() {
        AhoCorasick tree = new AhoCorasick();
        tree.add("hello".toCharArray(), "hello");
        tree.add("world".toCharArray(), "world");
        tree.prepare();

        Iterator<SearchResult> searcher = tree.search("hello world".toCharArray());
        while (searcher.hasNext()) {
            SearchResult result = searcher.next();
            System.out.println(result.getOutputs());
            System.out.println("Found at index: " + result.getLastIndex());
        }
    }
}
