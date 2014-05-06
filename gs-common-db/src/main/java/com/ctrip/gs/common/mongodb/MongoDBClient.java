package com.ctrip.gs.common.mongodb;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

/**
 * 
 * @author wgji
 * 
 */
public class MongoDBClient implements Serializable {
    private static final long serialVersionUID = 460964254527699065L;

    private static Logger log = LoggerFactory.getLogger(MongoDBClient.class);

    private transient Mongo mongo;
    private String dbname;

    private String ips;
    private int port;

    public MongoDBClient(String ips, int port, String dbname) {
        this(ips, port);
        this.dbname = dbname;
    }

    public MongoDBClient(String ips, int port) {
        this.ips = ips;
        this.port = port;
        connect();
    }

    public void connect() {
        List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
        if (StringUtils.isBlank(ips)) {
            log.error("Failed to init MongoDB client. ");
            return;
        }

        for (String s : ips.split(",")) {
            try {
                replicaSetSeeds.add(new ServerAddress(s, port));
            } catch (UnknownHostException e) {
                log.error("Unknown MongoDB Host:" + s + ":" + port);
            }
        }
        if (replicaSetSeeds != null && replicaSetSeeds.size() > 0) {
            MongoOptions options = new MongoOptions();
            options.connectionsPerHost = 100;
            options.threadsAllowedToBlockForConnectionMultiplier = 10;
            try {
                mongo = new Mongo(replicaSetSeeds, options);
                mongo.setReadPreference(ReadPreference.SECONDARY);
            } catch (MongoException e) {
                log.error("Failed to connect MongoDB");
                mongo = null;
            }
        } else {
            log.error("Failed to init MongoDB client. ");
        }
    }

    public Mongo getMongo() {
        return mongo;
    }

    public DB getDB(String dbName) {
        if (mongo != null) {
            return mongo.getDB(dbName);
        } else {
            return null;
        }
    }

    public DB getDB() {
        if (mongo != null) {
            return mongo.getDB(dbname);
        } else {
            return null;
        }
    }
}
