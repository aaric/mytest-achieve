package com.incarcloud.rooster.support.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.MessageFormat;

/**
 * HbaseUtils
 *
 * @author Aaric, created on 2017-05-27T14:18.
 * @since 1.0-SNAPSHOT
 */
public class HbaseUtils extends org.springframework.data.hadoop.hbase.HbaseUtils {

    /**
     * 生成主键
     *
     * @param id ID
     * @return
     * @throws Exception
     */
    public static String generatePrimaryKey(Integer id) {
        String pattern = "{0}{1,number,00000000000000000000}";
        Object[] arguments = new Object[]{DigestUtils.md5Hex(String.valueOf(id)).substring(0, 5), id};
        return MessageFormat.format(pattern, arguments);
    }
}
