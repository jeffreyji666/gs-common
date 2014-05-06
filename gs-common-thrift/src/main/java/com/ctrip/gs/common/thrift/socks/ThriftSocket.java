package com.ctrip.gs.common.thrift.socks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.ctrip.gs.common.string.StringUtil;
import com.ctrip.gs.common.thrift.config.ClientConfig;
import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.exception.ArgumentError;

class HostPort {
	private String host;
	private Integer port;

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
}

public class ThriftSocket<Transport extends TTransport> {
	HostPort hostPort;
	ClientConfig clientConfig;
	Transport transport;
	Boolean opened;
	Integer timeout;

	@SuppressWarnings("unchecked")
	public void init(IConfig config, String hostPort) throws ArgumentError,InterruptedException, ClassNotFoundException, NoSuchMethodException,
    	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		this.clientConfig = (ClientConfig) config;
		this.hostPort = parserServer(hostPort);
		this.opened = false;
		this.transport = null;
		this.timeout = this.clientConfig.getTimeoutConfig().getTimeout().intValue();

		//create transport
		Class<Transport> tmpTransportClass = (Class<Transport>) Class.forName(this.clientConfig.getTransportName());
		Constructor<Transport> ctorTransport = tmpTransportClass.getDeclaredConstructor(String.class, Integer.class);
		this.transport = ctorTransport.newInstance(this.hostPort.getHost(), this.hostPort.getPort());
		((TSocket) this.transport).setTimeout(this.timeout * 1000);

	}
	
	public Transport transport() {
		return this.transport;
	}

	public void connect() throws TTransportException {
	        this.open();
	}

	public void open() throws TTransportException {
		if (!this.opened) {
			this.transport.open();
			this.opened = true;
		}
	}

	public void close() {
		if (this.opened) {
			this.transport.close();
			this.opened = false;
		}
	}

	public void setTimeout(Integer timeout) {
		if (timeout != this.timeout) {
			this.timeout = timeout;
			((TSocket)this.transport).setTimeout(this.timeout * 1000);
		}
	}

	private HostPort parserServer(String hostPortStr) throws ArgumentError{
		String[] pair = StringUtil.split(hostPortStr, ":");
		if(pair.length != 2) {
			throw new ArgumentError(-3, "server must be of host:port, infact:" + hostPortStr);
		}
		HostPort hostPort = new HostPort();
		hostPort.setHost(pair[0]);
		hostPort.setPort(Integer.valueOf(pair[1]));
		return hostPort;
	}
}
