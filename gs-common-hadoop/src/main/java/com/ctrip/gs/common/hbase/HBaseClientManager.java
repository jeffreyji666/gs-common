package com.ctrip.gs.common.hbase;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.util.PoolMap;

import com.ctrip.gs.common.exceptions.BaseRuntimeException;

/**
 * @author: wgji
 * @date：2014年1月23日 上午11:10:04
 * @comment:
 */
public class HBaseClientManager {
    private static HBaseClientManager instance = new HBaseClientManager();

    /* Map<zkquorum_hbasebasepath, HTablePool> */
    private Map<String, HTablePool> clients = new ConcurrentHashMap<String, HTablePool>();
    private Map<String, Configuration> configCache = new ConcurrentHashMap<String, Configuration>();

    public static final String HBASE_TABLE_POOL_MAX_SIZE = "hbase.table.pool.max.size";
    public static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    public static final String HBASE_ZOOKEEPER_ZNODE = "zookeeper.znode.parent";
    public static final String HBASE_TABLE_THREADS_MAX = "hbase.htable.threads.max";
    public static final int TABLE_POOL_MAX_SIZE = 50;
    public static final int DEFAULT_TABLE_THREADS_MAX = 500000;

    private HBaseClientManager() {
    }

    public static HBaseClientManager getClientManager() {
        return instance;
    }

    public HTablePool getHTablePool(String zkquorum, String basePath) {
        return clients.get(generateClientKey(zkquorum, basePath));
    }

    public HTablePool addHTablePool(String zkquorum, String basePath) {
        Configuration conf = new Configuration();
        conf.set(HBASE_ZOOKEEPER_QUORUM, zkquorum);
        conf.set(HBASE_ZOOKEEPER_ZNODE, basePath);
        return addHTablePool(conf);
    }

    public synchronized HTablePool addHTablePool(Configuration conf) {
        validate(conf);

        String key = generateClientKey(conf);
        HTablePool pool = clients.get(key);
        if (pool != null) {
            throw new HBaseClientExistsException(key);
        }

        int maxSize = conf.getInt(HBASE_TABLE_POOL_MAX_SIZE, TABLE_POOL_MAX_SIZE);
        int threadMax = conf.getInt(HBASE_TABLE_THREADS_MAX, DEFAULT_TABLE_THREADS_MAX);
        conf.setInt(HBASE_TABLE_THREADS_MAX, threadMax);
        pool = new HTablePool(conf, maxSize, PoolMap.PoolType.ThreadLocal);
        clients.put(key, pool);
        return pool;
    }

    public void close(String zkquorum, String basePath) throws IOException {
        close(generateClientKey(zkquorum, basePath));
    }

    public synchronized void close(String poolKey) throws IOException {
        HTablePool hTablePool = clients.remove(poolKey);
        if (hTablePool != null) {
            hTablePool.close();
        }
    }

    public void shutdown() throws IOException {
        for (String key : clients.keySet()) {
            close(key);
        }
    }

    public void refresh(String zkquorum, String basePath) throws IOException {
        refresh(generateClientKey(zkquorum, basePath));
    }

    public void refresh(String poolKey) throws IOException {
        close(poolKey);

        Configuration conf = configCache.get(poolKey);
        addHTablePool(conf);
    }

    public void refreshAll() throws IOException {
        for (String key : clients.keySet()) {
            refresh(key);
        }
    }

    private void validate(Configuration conf) {
        if (StringUtils.isBlank(conf.get(HBASE_ZOOKEEPER_QUORUM))) {
            throw new IllegalArgumentException("Zookeeper quorum must be provided!");
        }
        if (StringUtils.isBlank(conf.get(HBASE_ZOOKEEPER_ZNODE))) {
            throw new IllegalArgumentException("HBase base path must be provided!");
        }
    }

    private String generateClientKey(Configuration conf) {
        return generateClientKey(conf.get(HBASE_ZOOKEEPER_QUORUM), conf.get(HBASE_ZOOKEEPER_ZNODE));
    }

    private String generateClientKey(String zkquorum, String basePath) {
        return zkquorum + "_" + basePath;
    }
}

class HBaseClientExistsException extends BaseRuntimeException {
    private static final long serialVersionUID = 100000000L;

    public HBaseClientExistsException(String key) {
        super("The HBase client has existed: " + key);
    }
}
