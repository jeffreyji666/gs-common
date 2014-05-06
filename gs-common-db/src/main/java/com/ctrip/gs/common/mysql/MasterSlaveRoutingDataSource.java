package com.ctrip.gs.common.mysql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 支持Master/Slave的DataSource。在Spring里可以这样配置：
 * 
 * <pre>
 * {@code
 *    <bean id="dataSource" class="com.ctrip.gs.recommendation.model.db.MasterSlaveRoutingDataSource">
 *        <property name="targetDataSources">
 *            <map>
 *                <entry key="main_master" value-ref="dataSourceMaster"/>
 *                <entry key="main_slave" value-ref="dataSourceSlave"/>
 *            </map>
 *        </property>
 *        <property name="dataSourceMap">
 *            <list>
 *                <value>main: main_master, main_slave</value>
 *            </list>
 *        </property>
 *        <property name="defaultTargetDataSource" ref="dataSourcePointsSlave"/>
 *    </bean>
 * }
 * </pre>
 * 
 * @author wgji
 * 
 */
public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource implements Serializable {

    private static final long serialVersionUID = -24452098148852615L;

    private static final String DEFAULT_DATABASE = "main";
    private static final Boolean DEFAULT_IS_MASTER = true;
    private static final String DEFAULT_DATASOURCE_KEY = "main_master";

    private Map<String, List<String>> dataSourceMap;

    private static ThreadLocal<String> databaseTl = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return DEFAULT_DATABASE;
        }
    };

    private static ThreadLocal<Boolean> isMasterTl = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return DEFAULT_IS_MASTER;
        }
    };

    /**
     * Parse the data source string. Each line should be like this: analytics: analytics_master, analytics_slave1,
     * analytics_slave The value before ":" is the name of the database, the value is a list of data source keys. The
     * first data source should be the master, and the rest should be slaves.
     * 
     * @param dataSourceList
     */
    public void setDataSourceMap(List<String> dataSourceList) {
        dataSourceMap = new HashMap<String, List<String>>();
        for (String dataSourceValue : dataSourceList) {
            String[] dsPair = dataSourceValue.split(":");
            if (dsPair.length != 2) {
                continue;
            }
            List<String> dss = new ArrayList<String>();
            String db = dsPair[0].trim();
            for (String ds : dsPair[1].split(",")) {
                dss.add(ds.trim());
            }
            dataSourceMap.put(db, dss);
        }
    }

    /**
     * Set the database for this transaction.
     * 
     * @param database
     *            The database name
     * @param isMaster
     *            Whether it's master or slave
     */
    public static void setDatabase(String database, boolean isMaster) {
        databaseTl.set(database);
        isMasterTl.set(isMaster);
    }

    /**
     * Return the key of data source for this transaction
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String db = databaseTl.get();
        if (StringUtils.isEmpty(db)) {
            db = DEFAULT_DATABASE;
        }
        boolean isMaster = isMasterTl.get();

        List<String> dss = dataSourceMap.get(db);
        if (dss == null || dss.isEmpty()) {
            return DEFAULT_DATASOURCE_KEY;
        } else if (isMaster || dss.size() == 1) {
            // if it's master or there's only 1 data source in the list
            return dss.get(0);
        } else {
            // TODO(wgji): choose slave randomly from multi-datasources
            return dss.get(1);
        }
    }

}
