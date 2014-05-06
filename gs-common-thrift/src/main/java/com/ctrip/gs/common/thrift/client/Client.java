package com.ctrip.gs.common.thrift.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.exception.ArgumentError;
import com.ctrip.gs.common.thrift.service.IService;
import com.ctrip.gs.common.thrift.service.PooledServiceImpl;
import com.ctrip.gs.protocol.base.BaseService;

public final class Client<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol> implements InvocationHandler {
	private IService service;
	private IConfig config;
	
	public Client(IConfig config) {
		this.config = config;
	}

	public void init() throws InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, ArgumentError {
		this.service = new PooledServiceImpl<ServiceClient, Transport, Protocol>();
		this.service.init(this.config);
	}

    public Object bind(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }
  
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
    	return this.service.execute(method, args);
    }
};














/*
public final class Client implements InvocationHandler {
	//private Object target;  
	private ClientConfig clientConfig;
	private Pool<Server> pool;

	public Client(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
		pool = new Pool<Server>(clientConfig.getConnPoolSize());
	}
	
	public void Init() throws InterruptedException {
		for(Integer i=0; i < clientConfig.getConnPoolSize(); i++) {
			pool.put(new Server(clientConfig));
		}
	}
	
	//ThriftClient.A();
	//Client->ConnnectionPool->Server->ThriftClient
	//Client.getConnection().getThriftClient().A();
	//Client.A()
    public Object bind(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }
  
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
    	Server server = null;
    	try {
    		server = pool.getWithTimeout(clientConfig.getTimeoutConfig().getTimeout());
    		return method.invoke(server, args);
    	} finally {
    		pool.put(server);
    	}
    }
};
*/