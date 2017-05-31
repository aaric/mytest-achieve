package com.github.aaric.achieve.hbase.util;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.MessageFormat;

/**
 * Hbase工具类
 *
 * @author Aaric, created on 2017-05-24T18:03.
 * @since 1.0-SNAPSHOT
 */
public class HbaseUtils extends org.springframework.data.hadoop.hbase.HbaseUtils {

    /**
     * 主键生成策略
     *
     * @param id ID
     * @return string
     * @throws Exception
     */
    public static String generatePrimaryKey(Integer id) {
        return MessageFormat.format("{0,number,00000000000000000000}", new Object[]{id});
    }

    /**
     * 获得整型值
     *
     * @param result 结果
     * @param family　主键
     * @param qualifier　列
     * @return integer
     */
    public static Integer getValueInteger(Result result, String family, String qualifier) {
        if(null != result && StringUtils.isNotBlank(family) && StringUtils.isNotBlank(qualifier)) {
            return Bytes.toInt(result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier)));
        }
        return null;
    }

    /**
     * 获得字符串值
     *
     * @param result Result
     * @param family 主键
     * @param qualifier 列
     * @return string
     */
    public static String getValueString(Result result, String family, String qualifier) {
        if(null != result && StringUtils.isNotBlank(family) && StringUtils.isNotBlank(qualifier)) {
            return Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier)));
        }
        return null;
    }

    /**
     * 添加整型列数据
     *
     * @param put Put
     * @param family 主键
     * @param qualifier 列
     * @param value 值
     * @return put
     */
    public static Put addColumn(Put put, String family, String qualifier, Integer value) {
        if(null != put && StringUtils.isNotBlank(family) && StringUtils.isNotBlank(qualifier)) {
            return put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        }
        return null;
    }

    /**
     * 添加整型列数据
     *
     * @param put Put
     * @param family 主键
     * @param qualifier 列
     * @param value 值
     * @return put
     */
    public static Put addColumn(Put put, String family, String qualifier, String value) {
        if(null != put && StringUtils.isNotBlank(family) && StringUtils.isNotBlank(qualifier)) {
            return put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        }
        return null;
    }
}
