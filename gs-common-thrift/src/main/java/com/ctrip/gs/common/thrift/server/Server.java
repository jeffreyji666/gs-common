package com.ctrip.gs.common.thrift.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.ctrip.gs.common.date.DateTimeUtil;
import com.ctrip.gs.common.thrift.config.ClientConfig;
import com.ctrip.gs.common.thrift.config.IConfig;
import com.ctrip.gs.common.thrift.exception.ArgumentError;
import com.ctrip.gs.common.thrift.socks.ThriftSocket;
import com.ctrip.gs.protocol.base.BaseService;

public class Server<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol>{
    private ClientConfig clientConfig;
    private ServiceClient client;
    private Boolean connected;
    private String hostPort;
    private ThriftSocket<Transport> connection;
    private long markDownTil;
    private Integer hostReconnectTimes;

    public Server(String hostPort) {
    	this.hostPort = hostPort;
    	this.markDownTil = 0;
    	this.hostReconnectTimes = 0;
    }

    public void init(IConfig config) throws InterruptedException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, ArgumentError {
    	this.connection = newConnection();
    }

    @SuppressWarnings("unchecked")
	public ServiceClient client() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, TTransportException {
        //lazy connection
        if (this.client == null) {
            this.connect();

            //create protocol
            Transport transport = this.connection.transport();
			Class<Protocol> tmpProtocolClass = (Class<Protocol>) Class.forName(this.clientConfig.getProtocolName());
          	Constructor<Protocol> ctorProtocol = tmpProtocolClass.getDeclaredConstructor(TTransport.class);
          	Protocol protocol = ctorProtocol.newInstance(transport);
          		
          	//create client
          	Class<ServiceClient> tmpClientClass = (Class<ServiceClient>) Class.forName(this.clientConfig.getClientName());
          	Constructor<ServiceClient> ctorClient = tmpClientClass.getDeclaredConstructor(TProtocol.class);
          	this.client = ctorClient.newInstance(protocol);

        }
        return this.client;
    }

    private void connect() throws TTransportException {
        if (!connected) {
        	this.connection.setTimeout(this.clientConfig.getTimeoutConfig().getConnectTimeout());
        	this.connection.connect();
        	this.connection.setTimeout(this.clientConfig.getTimeoutConfig().getTimeout());
        }
    }

    public Integer incrReconnectTimes() {
        this.hostReconnectTimes += 1;
        return this.hostReconnectTimes;
    }

    public void resetReconnectTimes() {
        this.hostReconnectTimes = 0;
    }

    public void close(Boolean teardown) {
        if (teardown) {
            this.connection.close();
            this.client = null;
            this.connected = false;
        }
    }

    public ThriftSocket<Transport> newConnection() throws ArgumentError, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
    	ThriftSocket<Transport> connection = new ThriftSocket<Transport>();
    	connection.init(this.clientConfig, this.hostPort);
    	return connection;
    }
    
    public void setTimeout(Integer timeout) {
    	this.connection.setTimeout(timeout);
    }
    
    public void markDown(Integer til) {
    	this.close(true);
    	this.markDownTil = DateTimeUtil.currentTimeMillis() + til;
    }
    
    public Boolean isUp() {
    	return !this.isDown();
    }

    public Boolean isDown() {
    	if ((this.markDownTil !=0) && (this.markDownTil > DateTimeUtil.currentTimeMillis())) {
    		return true;
    	}
    	return false;
    }
}