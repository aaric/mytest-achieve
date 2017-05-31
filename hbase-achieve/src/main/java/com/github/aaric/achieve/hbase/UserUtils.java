package com.github.aaric.achieve.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * UserUtils
 *
 * @author Aaric, created on 2017-05-25T09:21.
 * @since 1.0-SNAPSHOT
 */
@Component
public class UserUtils implements InitializingBean {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    private Admin admin;

    public void initialize() throws IOException {
        TableName tableName = TableName.valueOf(User.TABLE_NAME);

        // Delete table
        if(admin.tableExists(tableName)) {
            if(!admin.isTableDisabled(tableName)) {
                System.out.printf("Disabling %s\n", User.TABLE_NAME);
                admin.disableTable(tableName);
            }
            System.out.printf("Deleting %s\n", User.TABLE_NAME);
            admin.deleteTable(tableName);
        }

        // Create table
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(User.FAMILY_NAME);
        tableDescriptor.addFamily(columnDescriptor);
        admin.createTable(tableDescriptor);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        admin = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration()).getAdmin();
    }
}
