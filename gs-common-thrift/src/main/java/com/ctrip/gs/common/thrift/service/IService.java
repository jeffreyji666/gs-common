package com.ctrip.gs.common.thrift.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.exception.ArgumentError;
//import com.ctrip.gs.common.thrift.wrapper.IServiceWrapper;
import com.ctrip.gs.protocol.base.BaseService;

//public interface IService<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol> extends IServiceWrapper{
public interface IService{
	public void init(IConfig config) throws InterruptedException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ArgumentError;
	public Object execute(Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, Throwable;	
}