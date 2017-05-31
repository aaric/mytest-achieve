package com.github.aaric.achieve.hbase.util;

import org.junit.Test;

/**
 * HbaseUtils测试类
 *
 * @author Aaric, created on 2017-05-24T18:04.
 * @since 1.0-SNAPSHOT
 */
public class HbaseUtilsTest {

    @Test
    public void testGeneratePrimaryKey() throws Exception {
        System.out.println(HbaseUtils.generatePrimaryKey(12345));
    }
}
