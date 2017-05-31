package com.github.aaric.achieve.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * HbaseTemplate测试类
 *
 * @author Aaric, created on 2017-05-24T17:32.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HbaseTemplateTest {

    @Autowired
    protected HbaseTemplate hbaseTemplate;

    @Test
    public void testPrint() throws Exception {
        System.out.println(hbaseTemplate.getConfiguration().get("hbase.master"));
        System.out.println(hbaseTemplate.getConfiguration().get("hbase.zookeeper.quorum"));
        System.out.println(hbaseTemplate.getConfiguration().get("hbase.zookeeper.property.clientPort"));
    }

    @Test
    public void testTableExists() throws Exception {
        Admin admin = ConnectionFactory.createConnection(hbaseTemplate.getConfiguration()).getAdmin();
        System.out.println(admin.tableExists(TableName.valueOf("merchant")));
    }

    @Test
    public void testQuery() throws Exception {
        System.out.println(hbaseTemplate);
        List<String> dataList = hbaseTemplate.find("merchant", "base", new RowMapper<String>() {
            @Override
            public String mapRow(Result result, int rowNum) throws Exception {
                return Bytes.toString(result.getValue(Bytes.toBytes("base"), Bytes.toBytes("name")));
            }
        });
        System.out.println(dataList);
    }

    @Test
    public void testFilter() throws Exception {
        Scan scan = new Scan();
//		Scan scan = new Scan(Bytes.toBytes("company_rk_gp1_001"), Bytes.toBytes("company_rk_gp1_004"));

        // 1.前缀过滤器(筛选匹配行键的前缀成功的行 )
        Filter filter = new PrefixFilter(Bytes.toBytes("company"));

        // 2.行过滤器(筛选出匹配的所有的行)
        filter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("gk")));

        // 3.只关心键值过滤器(返回所有的行，但值全是空)
        filter = new KeyOnlyFilter();

        // 4.随机行过滤器(随机选出一部分的行)
        filter = new RandomRowFilter(0.8F);

        // 5.范围过滤器(包含了扫描的上限在结果之内 )
        filter = new InclusiveStopFilter(Bytes.toBytes("company_rk_gp1_004"));

        // 6.第一列过滤器(筛选出第一个每个第一个单元格)
        filter = new FirstKeyOnlyFilter();

        // 7.列前缀过滤器(筛选出前缀匹配的列)
        filter = new ColumnPrefixFilter(Bytes.toBytes("code"));

        // 8.值过滤器(筛选某个（值的条件满足的）特定的单元格)
        filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("kong"));

        // 9.列总数过滤器(如果突然发现一行中的列数超过设定的最大值时，整个扫描操作会停止)
        filter = new ColumnCountGetFilter(1);

        // 10.单列值过滤器(完整匹配字节数组)
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(User.FAMILY_NAME), Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, new SubstringComparator("root3")); //匹配是否包含子串,大小写不敏感
        singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(User.FAMILY_NAME), Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, new RegexStringComparator("root.")); //匹配正则表达式
        singleColumnValueFilter.setFilterIfMissing(false); //如果 filterIfColumnMissing 标志设为真，如果该行没有指定的列，那么该行的所有列将不发出。缺省值为假。
        singleColumnValueFilter.setLatestVersionOnly(true); //如果 setLatestVersionOnly 标志设为假，将检查此前的版本。缺省值为真

        // 11.单列值排除过滤器
        filter = new SingleColumnValueExcludeFilter(Bytes.toBytes(User.FAMILY_NAME), Bytes.toBytes("name"), CompareFilter.CompareOp.NOT_EQUAL, Bytes.toBytes("root"));

        // 12.附加过滤器(发现某一行中的一列需要过滤时，整个行就会被过滤掉)
        filter = new SkipFilter(singleColumnValueFilter);

        // 13.循环匹配过滤器
        filter = new WhileMatchFilter(singleColumnValueFilter);

        // 14.分页过滤器
        filter = new PageFilter(2);

        // 15.时间戳过滤器
        List<Long> timestampList = new ArrayList<>();
        timestampList.add(1494899853156L);
        timestampList.add(1494899617208L);
        timestampList.add(1494899138535L);
        filter = new TimestampsFilter(timestampList);

        scan.setFilter(filter);

        List<String> dataList = hbaseTemplate.find(User.TABLE_NAME, scan, new RowMapper<String>() {

            @Override
            public String mapRow(Result result, int rowNum) throws Exception {
                System.err.println(Bytes.toString(result.getRow()));
                List<Cell> cellList = result.listCells();
//				System.err.println(cellList.size());
                for (Cell cell : cellList) {
                    System.err.println(Bytes.toString(cell.getQualifierArray()) + ":" + Bytes.toString(cell.getValueArray()));
                    return Bytes.toString(cell.getValueArray());
                }
                return null;
            }
        });
        System.err.println(dataList.size());
    }
}
