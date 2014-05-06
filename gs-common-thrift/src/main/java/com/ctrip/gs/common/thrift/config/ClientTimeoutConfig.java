package com.ctrip.gs.common.thrift.config;

public class ClientTimeoutConfig {
	private Integer tcpConnectTimeout;
	private Integer requestTimeout;
	private Integer connectTimeout;
	private Integer timeout;
	private Integer readerIdleTimeout;
	private Integer writerIdleTimeout;
	private Integer serverRetryPeriod;

		
	public ClientTimeoutConfig() {
		
	}
	
	public ClientTimeoutConfig tcpConnectTimeout(Integer tcpConnectTimeout) {
		this.tcpConnectTimeout = tcpConnectTimeout;
		return this;
	}

	public ClientTimeoutConfig requestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
		return this;
	}

	public ClientTimeoutConfig connectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}
	
	public ClientTimeoutConfig timeout(Integer timeout) {
		this.timeout = timeout;
		return this;
	}

	public ClientTimeoutConfig readerIdleTimeout(Integer readerIdleTimeout) {
		this.readerIdleTimeout = readerIdleTimeout;
		return this;
	}

	public ClientTimeoutConfig writerIdleTimeout(Integer writerIdleTimeout) {
		this.writerIdleTimeout = writerIdleTimeout;
		return this;
	}

	public ClientTimeoutConfig serverRetryPeriod(Integer serverRetryPeriod) {
		this.serverRetryPeriod = serverRetryPeriod;
		return this;
	}

	/*
	private ClientTimeoutConfig(Integer tcpConnectTimeout,
			Integer requestTimeout, Integer connectTimeout, Integer timeout,
			Integer readerIdleTimeout, Integer writerIdleTimeout) {
		super();
		this.tcpConnectTimeout = tcpConnectTimeout;
		this.requestTimeout = requestTimeout;
		this.connectTimeout = connectTimeout;
		this.timeout = timeout;
		this.readerIdleTimeout = readerIdleTimeout;
		this.writerIdleTimeout = writerIdleTimeout;
	}
	
	public ClientTimeoutConfig getInstance() {
		return new ClientTimeoutConfig(tcpConnectTimeout=null, requestTimeout=null,
				connectTimeout=null, timeout=null, readerIdleTimeout=null,
				writerIdleTimeout=null);
	}
	
	public ClientTimeoutConfig copy(ClientTimeoutConfig timeoutConfig) {
		this.tcpConnectTimeout = timeoutConfig.getTcpConnectTimeout();
		this.requestTimeout    = timeoutConfig.getRequestTimeout();
		this.connectTimeout    = timeoutConfig.getConnectTimeout();
		this.timeout           = timeoutConfig.getTimeout();
		this.readerIdleTimeout = timeoutConfig.getReaderIdleTimeout();
		this.writerIdleTimeout = timeoutConfig.getWriterIdleTimeout();
		return this;
	}
	*/

	public Integer getTcpConnectTimeout() {
		return tcpConnectTimeout;
	}

	public void setTcpConnectTimeout(Integer tcpConnectTimeout) {
		this.tcpConnectTimeout = tcpConnectTimeout;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getReaderIdleTimeout() {
		return readerIdleTimeout;
	}

	public void setReaderIdleTimeout(Integer readerIdleTimeout) {
		this.readerIdleTimeout = readerIdleTimeout;
	}

	public Integer getWriterIdleTimeout() {
		return writerIdleTimeout;
	}

	public void setWriterIdleTimeout(Integer writerIdleTimeout) {
		this.writerIdleTimeout = writerIdleTimeout;
	}

	public Integer getServerRetryPeriod() {
		return serverRetryPeriod;
	}

	public void setServerRetryPeriod(Integer serverRetryPeriod) {
		this.serverRetryPeriod = serverRetryPeriod;
	}
};