package com.ctrip.gs.common.exception;

public class ExceptionBase extends Exception{
	Integer what;
	String  why;
	public ExceptionBase(Integer what, String why) {
		super();
		this.what = what;
		this.why  = why;
	}
}
