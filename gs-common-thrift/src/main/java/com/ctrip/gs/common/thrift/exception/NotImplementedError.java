package com.ctrip.gs.common.thrift.exception;

import com.ctrip.gs.common.exception.ExceptionBase;

public class NotImplementedError extends ExceptionBase{
	public NotImplementedError(Integer what, String why) {
		super(what, why);
	}
}
