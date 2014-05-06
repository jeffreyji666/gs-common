package com.ctrip.gs.common.thrift.exception;

import com.ctrip.gs.common.exception.ExceptionBase;

public class NoServersAvailable extends ExceptionBase{
	public NoServersAvailable(Integer what, String why) {
		super(what, why);
	}
}
