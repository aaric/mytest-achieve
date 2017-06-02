package com.github.aaric.achieve.netty;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;

/**
 * Description:
 *
 * @author Aaric, created on 2017-06-02T17:37.
 * @since 1.0-SNAPSHOT
 */
public class HexBinaryTest {

    @Test
    public void testParseHexBinaryString() {
        String source = "AA 55 00 0B FF F4 1C 05 16 06 00 02 3B";
        String[] array = source.split(" ");
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = Integer.valueOf(array[i], 16).byteValue();
            //------------------------------
            StringBuffer buffer = new StringBuffer(Integer.toHexString(bytes[i] & 0xFF));
            if(1 == buffer.length()) {
                buffer.insert(0, "0");
            }
            System.out.println(buffer.toString().toUpperCase());
            //------------------------------
        }
        Assert.assertEquals("AA55000BFFF41C05160600023B", DatatypeConverter.printHexBinary(bytes));
    }
}
