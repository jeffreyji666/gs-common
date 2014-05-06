package com.ctrip.gs.common.ac;

/**
 * Simple interface for mapping bytes to States.
 */
interface EdgeList {
	//字符接口
	State get(char ch);
	void put(char ch, State state);
	char[] keys();
}
