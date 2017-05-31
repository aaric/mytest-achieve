package com.github.aaric.achieve.hbase;

import com.github.aaric.achieve.hbase.util.HbaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * UserRepository
 *
 * @author Aaric, created on 2017-05-25T09:45.
 * @since 1.0-SNAPSHOT
 */
@Repository
public class UserRepository {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    public List<User> findAll() {
        return hbaseTemplate.find(User.TABLE_NAME, User.FAMILY_NAME, new RowMapper<User>() {
            @Override
            public User mapRow(Result result, int rowNum) throws Exception {
                return new User(HbaseUtils.getValueInteger(result, User.FAMILY_NAME, User.PROPERTY_ID),
                        HbaseUtils.getValueString(result, User.FAMILY_NAME, User.PROPERTY_NAME),
                        HbaseUtils.getValueString(result, User.FAMILY_NAME, User.PROPERTY_EMAIL),
                        HbaseUtils.getValueString(result, User.FAMILY_NAME, User.PROPERTY_PASSWORD));
            }
        });
    }

    public User get(Integer id) {
        return hbaseTemplate.get(User.TABLE_NAME, HbaseUtils.generatePrimaryKey(id), User.FAMILY_NAME, new RowMapper<User>() {
            @Override
            public User mapRow(Result result, int rowNum) throws Exception {
                return new User(HbaseUtils.getValueInteger(result, User.FAMILY_NAME, User.PROPERTY_ID),
                        HbaseUtils.getValueString(result, User.FAMILY_NAME, User.PROPERTY_NAME),
                        HbaseUtils.getValueString(result, User.FAMILY_NAME, User.PROPERTY_EMAIL),
                        HbaseUtils.getValueString(result, User.FAMILY_NAME, User.PROPERTY_PASSWORD));
            }
        });
    }

    public User save(final Integer id, final String name, final String email, final String password) {
        return hbaseTemplate.execute(User.TABLE_NAME, new TableCallback<User>() {
            @Override
            public User doInTable(HTableInterface table) throws Throwable {
                User user = new User(id, name, email, password);
                Put put = new Put(Bytes.toBytes(HbaseUtils.generatePrimaryKey(id)));
                HbaseUtils.addColumn(put, User.FAMILY_NAME, User.PROPERTY_ID, id);
                HbaseUtils.addColumn(put, User.FAMILY_NAME, User.PROPERTY_NAME, name);
                HbaseUtils.addColumn(put, User.FAMILY_NAME, User.PROPERTY_EMAIL, email);
                HbaseUtils.addColumn(put, User.FAMILY_NAME, User.PROPERTY_PASSWORD, password);
                table.put(put);
                return user;
            }
        });
    }

    public void delete(final Integer id) {
        hbaseTemplate.delete(User.TABLE_NAME, HbaseUtils.generatePrimaryKey(id), User.FAMILY_NAME);
    }

    public Integer count() throws IOException {
        Connection connection = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration());
        Table table = connection.getTable(TableName.valueOf(User.TABLE_NAME));
        Scan scan = new Scan();
        scan.setFilter(new FirstKeyOnlyFilter());
        ResultScanner resultScanner = table.getScanner(scan);
        Long rowCount = 0L;
        for (Result result: resultScanner) {
            rowCount += result.size();
        }
        return rowCount.intValue();
    }

    public Integer count2() throws IOException {
        /**
         * 解决org.apache.hadoop.hbase.exceptions.UnknownProtocolException异常:
         * 方法一、hbase-site.xml(通过)
         *   <property>
         *     <name>hbase.coprocessor.user.region.classes</name>
         *     <value>org.apache.hadoop.hbase.coprocessor.AggregateImplementation</value>
         *   </property>
         * 方法二、创建表添加Coprocessor(未尝试)
         *   HTableDescriptor tableDescriptor = admin.getTableDescriptor(tableName);
         *   tableDescriptor.addCoprocessor("org.apache.hadoop.hbase.coprocessor.AggregateImplementation");
         */
        AggregationClient client = new AggregationClient(hbaseTemplate.getConfiguration());
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(User.FAMILY_NAME));
        Long rowCount = 0L;
        try {
            rowCount = client.rowCount(TableName.valueOf(User.TABLE_NAME), new LongColumnInterpreter(), scan);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            client.close();
        }
        return rowCount.intValue();
    }

}
