package com.ctrip.gs.common.thrift.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ctrip.gs.common.thrift.config.ClientConfig;
import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.exception.ArgumentError;
import com.ctrip.gs.common.thrift.pool.Pool;
import com.ctrip.gs.protocol.base.BaseService;

public class PooledServiceImpl<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol> implements IService {
    private ClientConfig config;
    private Pool<IService> pool;

    @Override
    public void init(IConfig config) throws InterruptedException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ArgumentError {
        this.config = (ClientConfig) config;
        this.pool = new Pool<IService>(this.config.getConnPoolSize());
        for (Integer i = 0; i < this.config.getConnPoolSize(); i++) {
        	RetryServiceImpl<ServiceClient, Transport, Protocol> retryService = new RetryServiceImpl<ServiceClient, Transport, Protocol>();
        	retryService.init(config);
            this.pool.put(retryService);
        }
    }

    public Object execute(Method method, Object[] args) throws Throwable {
        IService service = null;
        try {
            service = this.pool.getWithTimeout(this.config.getTimeoutConfig().getTimeout());
            return service.execute(method, args);
        } finally {
            if (service != null) {
                this.pool.put(service);
            }
        }
    }

}
