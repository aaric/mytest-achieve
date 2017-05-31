package com.incarcloud.rooster;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * HbaseTemplate测试类
 *
 * @author Aaric, created on 2017-05-27T10:38.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class HbaseTemplateTest {

    @Autowired
    protected HbaseTemplate hbaseTemplate;

    @Before
    public void begin() throws Exception {
        TableName tableName = TableName.valueOf("org");
        Admin admin = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration()).getAdmin();
        if(!admin.tableExists(tableName)) {
            HTableDescriptor desc = new HTableDescriptor(tableName);
            HColumnDescriptor family = new HColumnDescriptor("key");
            desc.addFamily(family);
            admin.createTable(desc);
        }
    }

    @Test
    public void testPrint() throws Exception {
        System.out.println(hbaseTemplate);
    }
}
