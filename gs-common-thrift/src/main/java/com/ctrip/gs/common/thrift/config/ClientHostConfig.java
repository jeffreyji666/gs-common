package com.ctrip.gs.common.thrift.config;

public	class ClientHostConfig {
	private Integer hostConnectionCoresize;
	private Integer hostConnectionLimit;
	private Integer hostConnectionIdleTime;
	private Integer hostConnectionMaxWaiters;
	private Integer hostConnectionMaxIdleTime;
	private Integer hostConnectionMaxLifeTime;
	private Integer hostConnectionBufferSize;

	public ClientHostConfig() {
		
	}
	
	public ClientHostConfig hostConnectionCoresize(Integer hostConnectionCoresize) {
		this.hostConnectionCoresize = hostConnectionCoresize;
		return this;
	}

	public ClientHostConfig hostConnectionLimit(Integer hostConnectionLimit) {
		this.hostConnectionLimit = hostConnectionLimit;
		return this;
	}

	public ClientHostConfig hostConnectionIdleTime(Integer hostConnectionIdleTime) {
		this.hostConnectionIdleTime = hostConnectionIdleTime;
		return this;
	}

	public ClientHostConfig hostConnectionMaxWaiters(Integer hostConnectionMaxWaiters) {
		this.hostConnectionMaxWaiters = hostConnectionMaxWaiters;
		return this;
	}

	public ClientHostConfig hostConnectionMaxIdleTime(Integer hostConnectionMaxIdleTime) {
		this.hostConnectionMaxIdleTime = hostConnectionMaxIdleTime;
		return this;
	}

	public ClientHostConfig hostConnectionMaxLifeTime(Integer hostConnectionMaxLifeTime) {
		this.hostConnectionMaxLifeTime = hostConnectionMaxLifeTime;
		return this;
	}

	public ClientHostConfig hostConnectionBufferSize(Integer hostConnectionBufferSize) {
		this.hostConnectionBufferSize = hostConnectionBufferSize;
		return this;
	}

	/*
	public ClientHostConfig(Integer hostConnectionCoresize,
			Integer hostConnectionLimit, Integer hostConnectionIdleTime,
			Integer hostConnectionMaxWaiters,
			Integer hostConnectionMaxIdleTime,
			Integer hostConnectionMaxLifeTime, Integer hostConnectionBufferSize) {
		super();
		this.hostConnectionCoresize = hostConnectionCoresize;
		this.hostConnectionLimit = hostConnectionLimit;
		this.hostConnectionIdleTime = hostConnectionIdleTime;
		this.hostConnectionMaxWaiters = hostConnectionMaxWaiters;
		this.hostConnectionMaxIdleTime = hostConnectionMaxIdleTime;
		this.hostConnectionMaxLifeTime = hostConnectionMaxLifeTime;
		this.hostConnectionBufferSize = hostConnectionBufferSize;
	}
	*/

	public Integer getHostConnectionCoresize() {
		return hostConnectionCoresize;
	}

	public void setHostConnectionCoresize(Integer hostConnectionCoresize) {
		this.hostConnectionCoresize = hostConnectionCoresize;
	}

	public Integer getHostConnectionLimit() {
		return hostConnectionLimit;
	}

	public void setHostConnectionLimit(Integer hostConnectionLimit) {
		this.hostConnectionLimit = hostConnectionLimit;
	}

	public Integer getHostConnectionIdleTime() {
		return hostConnectionIdleTime;
	}

	public void setHostConnectionIdleTime(Integer hostConnectionIdleTime) {
		this.hostConnectionIdleTime = hostConnectionIdleTime;
	}

	public Integer getHostConnectionMaxWaiters() {
		return hostConnectionMaxWaiters;
	}

	public void setHostConnectionMaxWaiters(Integer hostConnectionMaxWaiters) {
		this.hostConnectionMaxWaiters = hostConnectionMaxWaiters;
	}

	public Integer getHostConnectionMaxIdleTime() {
		return hostConnectionMaxIdleTime;
	}

	public void setHostConnectionMaxIdleTime(Integer hostConnectionMaxIdleTime) {
		this.hostConnectionMaxIdleTime = hostConnectionMaxIdleTime;
	}

	public Integer getHostConnectionMaxLifeTime() {
		return hostConnectionMaxLifeTime;
	}

	public void setHostConnectionMaxLifeTime(Integer hostConnectionMaxLifeTime) {
		this.hostConnectionMaxLifeTime = hostConnectionMaxLifeTime;
	}

	public Integer getHostConnectionBufferSize() {
		return hostConnectionBufferSize;
	}

	public void setHostConnectionBufferSize(Integer hostConnectionBufferSize) {
		this.hostConnectionBufferSize = hostConnectionBufferSize;
	}
};