package com.ctrip.gs.common.thrift.builder;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ctrip.gs.common.thrift.client.Client;
import com.ctrip.gs.common.thrift.config.ClientHostConfig;
import com.ctrip.gs.common.thrift.config.ClientTimeoutConfig;
import com.ctrip.gs.common.thrift.config.ClientConfig;
import com.ctrip.gs.protocol.base.BaseService;

/**
 * 
 * ClientBuilder is used to build a client to make RPC to remote service point
 * for example:
 * 
 *Client<TestThriftClient, TSocket, TBinaryProtocol> client = 
 *	new ClientBuilder<TestThriftClient, TSocket, TBinaryProtocol>().hosts(["localhost:1234","localhost:5678"]).
 *		tcpConnectTimeout(10).timeout(1000).name("TestClient").retries(2).failFast(true).
 *		clientName("TestThriftClient").transportName("TSocket").protocolName("TBinaryProtocol").build();
 *
 *client.ping();
 *
 * @author yancl
 *
 * @param <ServiceClient> service client
 * @param <Transport> transport
 * @param <Protocol>
 */
public final class ClientBuilder<ServiceClient extends BaseService.Client, Transport extends TTransport, Protocol extends TProtocol> {
    private ClientConfig clientConfig;

    public ClientBuilder() {
        this.clientConfig = new ClientConfig().hostConfig(new ClientHostConfig()).timeoutConfig(
                new ClientTimeoutConfig());
    }

    public ClientBuilder<ServiceClient, Transport, Protocol> hosts(String[] hosts) {
        this.clientConfig.setHosts(hosts);
        return this;
    }

    public ClientBuilder<ServiceClient, Transport, Protocol> codec() {
        return this;
    }

    /**
     * Specify the TCP connection timeout(ms).
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> tcpConnectTimeout(Integer duration) {
        this.clientConfig.getTimeoutConfig().setTcpConnectTimeout(duration);
        return this;
    }

    /**
     * The request timeout is the time given to a *single* request (if there are retries, they each get a fresh request
     * timeout). The timeout is applied only after a connection has been acquired. That is: it is applied to the
     * interval between the dispatch of the request and the receipt of the response.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> requestTimeout(Integer duration) {
        this.clientConfig.getTimeoutConfig().setRequestTimeout(duration);
        return this;
    }

    /**
     * The connect timeout is the timeout applied to the acquisition of a Service. This includes both queueing time (eg.
     * because we cannot create more connections due to {{hostConnectionLimit}} and there are more than
     * {{hostConnectionLimit}} requests outstanding) as well as physical connection time.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> connectTimeout(Integer duration) {
        this.clientConfig.getTimeoutConfig().setConnectTimeout(duration);
        return this;
    }

    /**
     * Total request timeout. This timeout is applied from the issuance of a request (through {{service(request)}})
     * until the satisfaction of that reply future. No request will take longer than this.
     * 
     * Applicable only to service-builds ({{build()}})
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> timeout(Integer duration) {
        this.clientConfig.getTimeoutConfig().setTimeout(duration);
        return this;
    }

    /**
     * The maximum time a connection may have received no data.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> readerIdleTimeout(Integer duration) {
        this.clientConfig.getTimeoutConfig().setReaderIdleTimeout(duration);
        return this;
    }

    /**
     * The maximum time a connection may not have sent any data.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> writerIdleTimeout(Integer duration) {
        this.clientConfig.getTimeoutConfig().setWriterIdleTimeout(duration);
        return this;
    }

    /**
     * Report stats to the given {{StatsReceiver}}. This will report verbose global statistics and counters, that in
     * turn may be exported to monitoring applications. NB: per hosts statistics will *NOT* be exported to this receiver
     * 
     * @see reportHostStats(receiver: StatsReceiver)
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> reportTo() {
        return this;
    }

    /**
     * Report per host stats to the given {{StatsReceiver}}. The statsReceiver will be scoped per client, like this:
     * client/connect_latency_ms_max/0.0.0.0:64754
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> reportHostStats() {
        return this;
    }

    /**
     * Give a meaningful name to the client. Required.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> name(String name) {
        this.clientConfig.setName(name);
        return this;
    }

    /**
     * The maximum number of connections that are allowed per host. Required. we guarantees to to never have more active
     * connections than this limit.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> hostConnectionLimit(Integer value) {
        this.clientConfig.getHostConfig().setHostConnectionLimit(value);
        return this;
    }

    /**
     * The core size of the connection pool: the pool is not shrinked below this limit.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> hostConnectionCoresize(Integer value) {
        this.clientConfig.getHostConfig().setHostConnectionCoresize(value);
        return this;
    }

    /**
     * The amount of time a connection is allowed to linger (when it otherwise would have been closed by the pool)
     * before being closed.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> hostConnectionIdleTime(Integer value) {
        this.clientConfig.getHostConfig().setHostConnectionIdleTime(value);
        return this;
    }

    /**
     * The maximum queue size for the connection pool.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> hostConnectionMaxWaiters(Integer value) {
        this.clientConfig.getHostConfig().setHostConnectionMaxWaiters(value);
        return this;
    }

    /**
     * The maximum time a connection is allowed to linger unused.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> hostConnectionMaxIdleTime(Integer value) {
        this.clientConfig.getHostConfig().setHostConnectionMaxIdleTime(value);
        return this;
    }

    /**
     * The maximum time a connection is allowed to exist, regardless of occupancy.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> hostConnectionMaxLifeTime(Integer value) {
        this.clientConfig.getHostConfig().setHostConnectionMaxLifeTime(value);
        return this;
    }

    /**
     * The number of retries applied. Only applicable to service-builds ({{build()}})
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> retries(Integer value) {
        this.clientConfig.setRetryTimes(value);
        return this;
    }

    /**
     * Sets the TCP send buffer size.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> sendBufferSize(Integer value) {
        this.clientConfig.setSendBufferSize(value);
        return this;
    }

    /**
     * Sets the TCP recv buffer size.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> recvBufferSize(Integer value) {
        this.clientConfig.setRecvBufferSize(value);
        return this;
    }

    /**
     * Specifies a tracer that receives trace events. See [[com.twitter.finagle.tracing]] for details.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> tracer() {
        return this;
    }

    public ClientBuilder<ServiceClient, Transport, Protocol> monitor() {
        return this;
    }

    /**
     * Log very detailed debug information to the given logger.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> logger() {
        return this;
    }

    /**
     * Marks a host dead on connection failure. The host remains dead until we succesfully connect.
     * 
     * Intermediate connection attempts *are* respected, but host availability is turned off during the reconnection
     * period.
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> failFast(Boolean value) {
        this.clientConfig.setFailFast(value);
        return this;
    }

    /**
     * @param value thrift client name
     * @return
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> clientName(String value) {
        this.clientConfig.setClientName(value);
        return this;
    }

    /**
     * @param value thrift transport name
     * @return
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> transportName(String value) {
        this.clientConfig.setTransportName(value);
        return this;
    }

    /**
     * @param value thrift protocol name
     * @return
     */
    public ClientBuilder<ServiceClient, Transport, Protocol> protocolName(String value) {
        this.clientConfig.setProtocolName(value);
        return this;
    }

    /**
     * build a service wrapper that proxy requests to servers
     * 
     * @return Service
     */
    public Client<ServiceClient, Transport, Protocol> build() {
        check();
        return new Client<ServiceClient, Transport, Protocol>(this.clientConfig);
    }

    /**
     * check the default parameters
     */
    private void check() {

    }
};
