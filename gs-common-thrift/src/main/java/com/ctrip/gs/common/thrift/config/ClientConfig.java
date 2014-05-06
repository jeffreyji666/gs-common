package com.ctrip.gs.common.thrift.config;

import org.apache.log4j.Logger;

import com.ctrip.gs.common.thrift.config.ClientHostConfig;
import com.ctrip.gs.common.thrift.config.ClientTimeoutConfig;

import com.ctrip.gs.common.thrift.config.IConfig;

public class ClientConfig implements IConfig {
	private String name;
	private String clientName;
	private String transportName;
	private String protocolName;
	private Integer sendBufferSize;
	private Integer recvBufferSize;
	private Integer retryTimes;
	private Logger logger;
	private ClientHostConfig hostConfig;
	private ClientTimeoutConfig timeoutConfig;
	private Boolean failFast;
	private String[] hosts;
	private Integer connPoolSize;

	public ClientConfig() {
		
	}
	
	public ClientConfig name(String name) {
		this.name = name;
		return this;
	}

	public ClientConfig clientName(String clientName) {
		this.clientName = clientName;
		return this;
	}

	public ClientConfig transportName(String transportName) {
		this.transportName = transportName;
		return this;
	}

	public ClientConfig protocolName(String protocolName) {
		this.protocolName = protocolName;
		return this;
	}

	public ClientConfig sendBufferSize(Integer sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
		return this;
	}

	public ClientConfig recvBufferSize(Integer recvBufferSize) {
		this.recvBufferSize = recvBufferSize;
		return this;
	}

	public ClientConfig retryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
		return this;
	}
	public ClientConfig logger(Logger logger) {
		this.logger = logger;
		return this;
	}
	public ClientConfig hostConfig(ClientHostConfig hostConfig) {
		this.hostConfig = hostConfig;
		return this;
	}

	public ClientConfig timeoutConfig(ClientTimeoutConfig timeoutConfig) {
		this.timeoutConfig = timeoutConfig;
		return this;
	}

	public ClientConfig failFast(Boolean failFast) {
		this.failFast = failFast;
		return this;
	}
	
	public ClientConfig hosts(String[] hosts) {
		this.hosts = hosts;
		return this;
	}

	public ClientConfig connPoolSize(Integer connPoolSize) {
		this.connPoolSize = connPoolSize;
		return this;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	public Integer getSendBufferSize() {
		return sendBufferSize;
	}

	public void setSendBufferSize(Integer sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
	}

	public Integer getRecvBufferSize() {
		return recvBufferSize;
	}

	public void setRecvBufferSize(Integer recvBufferSize) {
		this.recvBufferSize = recvBufferSize;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public ClientHostConfig getHostConfig() {
		return hostConfig;
	}

	public void setHostConfig(ClientHostConfig hostConfig) {
		this.hostConfig = hostConfig;
	}

	public ClientTimeoutConfig getTimeoutConfig() {
		return timeoutConfig;
	}

	public void setTimeoutConfig(ClientTimeoutConfig timeoutConfig) {
		this.timeoutConfig = timeoutConfig;
	}

	public Boolean getFailFast() {
		return failFast;
	}

	public void setFailFast(Boolean failFast) {
		this.failFast = failFast;
	}

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}
	public Integer getConnPoolSize() {
		return connPoolSize;
	}

	public void setConnPoolSize(Integer connPoolSize) {
		this.connPoolSize = connPoolSize;
	}

}