package com.ctrip.gs.common.hbase;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctrip.gs.common.util.Config;

/**
 * @author wgji
 * @date：2013年12月25日 下午4:36:06
 */
public class HBaseClient {
    private static final Logger logger = LoggerFactory.getLogger(HBaseClient.class);

    private static HTablePool hTablePool;
    private static AtomicLong hbaseExceptionCount = new AtomicLong(0L);
    private static String zkPath = Config.getString("hbase.zkquorum");
    private static String zkParent = Config.getString("hbase.zknode.parent");

    public static synchronized HTablePool getHTablePool(String zkPath, String zkParent) {
        if (hTablePool == null) {
            try {
                logger.info("hbase.zkquorum = " + zkPath + ",hbase.zkparentnode:" + zkParent);
                hTablePool = HBaseClientManager.getClientManager().getHTablePool(zkPath, zkParent);
                if (hTablePool == null) {
                    hTablePool = HBaseClientManager.getClientManager().addHTablePool(zkPath, zkParent);
                }
                logger.info("Hbase table pool was initialized successfully");
            } catch (Throwable e) {
                try {
                    if (hTablePool != null) {
                        hTablePool.close();
                    }
                } catch (IOException ex) {
                    logger.error("Failed to close hbase table pool", ex);
                }
                logger.error("Failed to initialize hbase table pool", e);
                throw new RuntimeException("Initialization failed", e);
            }
        }
        return hTablePool;
    }

    public static HTableInterface getHTable(String zkPath, String zkParent, byte[] name, boolean autoFlush) {
        HTableInterface hTableInterface = getHTablePool(zkPath, zkParent).getTable(name);
        hTableInterface.setAutoFlush(autoFlush);

        return hTableInterface;
    }

    public static HTableInterface getHTable(String name, boolean autoFlush) {
        return getHTable(zkPath, zkParent, name.getBytes(), autoFlush);
    }

    public static HTableInterface getHTable(String name) {
        return getHTable(zkPath, zkParent, name.getBytes(), false);
    }

    public static void closeHTable(HTableInterface table) {
        if (table != null) {
            try {
                table.close();
            } catch (IOException e) {
                logger.error("Close hbase table error.", e);
            }
        }
    }

    public static AtomicLong getHbaseExceptionCount() {
        return hbaseExceptionCount;
    }

    public static void incrHbaseExceptionCount() {
        hbaseExceptionCount.incrementAndGet();
    }

    public static void shutdown() throws IOException {
        HBaseClientManager.getClientManager().shutdown();
    }

    public static void flushTable(HTableInterface table) throws IOException {
        if (table != null) {
            table.flushCommits();
        }
    }
}
