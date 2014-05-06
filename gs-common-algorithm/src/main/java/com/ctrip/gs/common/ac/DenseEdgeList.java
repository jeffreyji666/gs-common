package com.ctrip.gs.common.ac;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents an EdgeList by using a single array. Very fast lookup (just an array access), but expensive in terms of
 * memory.
 */

class DenseEdgeList implements EdgeList {

    // 字符接口
    private Map<Character, State> mapState = new HashMap<Character, State>();

    public State get(char c) {
        return mapState.get(c);
    }

    public void put(char c, State s) {
        mapState.put(c, s);
    }

    public char[] keys() {
        int length = mapState.size();
        char[] result = new char[length];
        int i = 0;
        for (Entry<Character, State> entry : mapState.entrySet()) {
            result[i] = entry.getKey();
            i++;
        }
        return result;
    }
}
