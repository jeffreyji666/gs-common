package com.ctrip.gs.common.thrift.exception;

import com.ctrip.gs.common.exception.ExceptionBase;

public class ArgumentError extends ExceptionBase{
	public ArgumentError(Integer what, String why) {
		super(what, why);
	}
}
