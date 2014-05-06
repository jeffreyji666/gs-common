package com.ctrip.gs.common.thrift.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ctrip.gs.common.thrift.config.ClientConfig;
import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.exception.ArgumentError;
import com.ctrip.gs.protocol.base.BaseService;

public class RetryServiceImpl<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol> implements IService {
    private ClientConfig config;
    private SteadyServiceImpl<ServiceClient, Transport, Protocol> service;

    @Override
    public void init(IConfig config) throws InterruptedException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ArgumentError {
        this.config = (ClientConfig) config;
        this.service = new SteadyServiceImpl<ServiceClient, Transport, Protocol>();
        this.service.init(this.config);
    }

    public Object execute(Method method, Object[] args) throws Throwable {
        Integer counter = 0;
        while (counter++ < (this.config.getRetryTimes() + 1)) {
            try {
                return this.service.execute(method, args);
            } catch (Exception e) {
                System.out.println("exception:" + e);
            }
        }
        return null;
    }
}
