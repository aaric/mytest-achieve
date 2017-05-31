package com.incarcloud.rooster.support.abs;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.io.IOException;

/**
 * AbstractHbaseRepositoryObject
 *
 * @author Aaric, created on 2017-05-27T14:07.
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractHbaseRepositoryObject {

    @Autowired
    protected HbaseTemplate hbaseTemplate;

    private Logger logger;

    /**
     * Constructor
     */
    public AbstractHbaseRepositoryObject() {
        logger = Logger.getLogger(this.getClass());
    }

    /**
     * getConnection
     *
     * @return
     * @throws IOException
     */
    public Connection getConnection() throws IOException {
        return ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
    }

    /**
     * getAdmin
     *
     * @return
     * @throws IOException
     */
    public Admin getAdmin() throws IOException {
        return getConnection().getAdmin();
    }

    /**
     * getTable
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public Table getTable(String tableName) throws IOException {
        return getConnection().getTable(TableName.valueOf(tableName));
    }
}
