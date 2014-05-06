package com.ctrip.gs.common.thrift.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.ctrip.gs.common.date.DateTimeUtil;
import com.ctrip.gs.protocol.base.BaseService;
import com.ctrip.gs.protocol.base.ExceptionBase;
import com.ctrip.gs.common.thrift.config.ClientConfig;
import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.server.Server;
import com.ctrip.gs.common.thrift.exception.ArgumentError;
import com.ctrip.gs.common.thrift.exception.NoServersAvailable;


public class SteadyServiceImpl<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol> implements IService {
	private ClientConfig config;
	private Server<ServiceClient, Transport, Protocol>[] servers;
	private Integer currentServerIdx;
	private Server<ServiceClient, Transport, Protocol> currentServer;
	private ServiceClient client;
	private Integer reqCount;
	private Integer serverMaxReqCount;


	@Override
	public void init(IConfig config) throws InterruptedException, ClassNotFoundException, NoSuchMethodException,
	        SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
	        InvocationTargetException, ArgumentError {
	    this.config = (ClientConfig) config;
	    this.client = null;
	    initServers();
	}

	@Override
	public Object execute(Method method, Object[] args) throws Throwable {
		try {
			if (this.client == null) {
				this.connect();
			}
			this.currentServer.setTimeout(this.config.getTimeoutConfig().getTimeout());
			this.reqCount += 1;
			return method.invoke(this.client, args);
		}catch(InvocationTargetException ae) {
			Boolean isDisconnectException = false;

			//app exception,throw to caller
			if(ae.getTargetException() instanceof ExceptionBase){
				throw ae.getTargetException();
			}
			
			//network io exception
			if (ae.getTargetException() instanceof TException) {
				isDisconnectException = true;
			}else if (ae.getTargetException() instanceof TApplicationException) {
				isDisconnectException = true;
			}else if (ae.getTargetException() instanceof TTransportException) {
				isDisconnectException = true;
			}
			//disconnect the socket.
			if (isDisconnectException) {
				this.disconnect(true);
			}
			
			//throw to caller, it will retry
			throw ae;
		} finally {
			if (this.serverMaxReqCount !=0 && this.reqCount >= this.serverMaxReqCount) {
				this.disconnect(false);
			}
		}
	}
	
	private void initServers() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, ArgumentError {
	    //shuffle hosts
		List<String> hosts = new ArrayList<String>();
	    for (Integer i=0; i<this.config.getHosts().length; i++) {
	    	hosts.add(this.config.getHosts()[i]);
	    }
	    Collections.shuffle(hosts);
	    
	    //init servers
	    this.servers = new Server[this.config.getHosts().length];	
	    for(Integer i=0; i<this.config.getHosts().length; i++) {
	    	Server<ServiceClient, Transport, Protocol> server =  new Server<ServiceClient, Transport, Protocol>(this.config.getHosts()[i]);
	    	server.init(this.config);
	    	this.servers[i] = server;
	    }
	}
	
	private void connect() throws NoServersAvailable, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Long startTime = DateTimeUtil.currentTimeMillis();
		Long count = 0L;
		while(true) {
			try {
				this.currentServer = this.nextLiveServer();
				this.client = this.currentServer.client();
				break;
			}catch(TTransportException ex){
				count++;
				this.disconnect(true);
				Integer timeout = this.timeout();
				if ((timeout > 0) && 
					(DateTimeUtil.currentTimeMillis() - startTime) > timeout) {
					throw new NoServersAvailable(-2, "after [" + count.toString() + "] retry connects failed, give up! ex:" + ex);
				}
			}
		}
	}
	
	private void disconnect(Boolean error) {
		if (this.currentServer != null) {
			if (error && this.currentServer.incrReconnectTimes() > 1) {
				this.currentServer.markDown(this.config.getTimeoutConfig().getServerRetryPeriod());
				this.currentServer.resetReconnectTimes();
			}
			this.currentServer.close(false);
		}
		this.client = null;
		this.currentServer = null;
		this.reqCount = 0;
	}
	
	private Integer timeout() {
		return this.config.getTimeoutConfig().getTimeout();
	}
	
	private Server<ServiceClient, Transport, Protocol> nextLiveServer() throws NoServersAvailable{
		for(Integer i=0; i<this.servers.length; i++) {
			Integer cur = (1 + this.currentServerIdx + i) % this.servers.length;
			if(this.servers[cur].isUp()) {
				this.currentServerIdx = cur;
				return this.servers[cur];
			}
		}
		throw new NoServersAvailable(-1, "NoServerAvailable");
	}
}