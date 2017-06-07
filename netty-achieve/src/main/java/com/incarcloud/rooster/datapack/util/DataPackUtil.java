package com.incarcloud.rooster.datapack.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * ByteBuf工具类
 *
 * @author Aaric, created on 2017-06-06T16:45.
 * @since 1.0-SNAPSHOT
 */
public final class DataPackUtil {

    /**
     * 根据offset获得1byte无符号整型<br>
     *
     * @param buffer ByteBuf
     * @return int
     */
    public static int readUInt1(ByteBuf buffer) {
        if(null == buffer) {
            throw new IllegalArgumentException("buffer is null");
        }
        int offset = buffer.readerIndex();
        return readUInt1(buffer, offset);
    }

    /**
     * 根据offset获得1byte无符号整型<br>
     *
     * @param buffer ByteBuf
     * @param offset 偏移
     * @return int
     */
    public static int readUInt1(ByteBuf buffer, int offset) {
        if(null == buffer) {
            throw new IllegalArgumentException("buffer is null");
        }
        // 丢弃1个字节
        buffer.skipBytes(1);
        // 返回数值
        return buffer.getByte(offset) & 0xFF;
    }

    /**
     * 根据offset获得4byte无符号整型(高位在前)<br>
     *
     * @param buffer ByteBuf
     * @return int
     */
    public static int readUInt4(ByteBuf buffer) {
        if(null == buffer) {
            throw new IllegalArgumentException("buffer is null");
        }
        int offset = buffer.readerIndex();
        return readUInt4(buffer, offset);
    }

    /**
     * 根据offset获得4byte无符号整型(高位在前)<br>
     *
     * @param buffer ByteBuf
     * @param offset 偏移
     * @return int
     */
    public static int readUInt4(ByteBuf buffer, int offset) {
        if(null == buffer) {
            throw new IllegalArgumentException("buffer is null");
        }
        // 丢弃4个字节
        buffer.skipBytes(4);
        // 返回数值
        return (buffer.getByte(offset) & 0xFF) << 24
                | (buffer.getByte(offset + 1) & 0xFF) << 16
                | (buffer.getByte(offset + 2) & 0xFF) << 8
                | (buffer.getByte(offset + 3) & 0xFF);
    }

    /**
     * 根据offset获得以0x00结束的字符串<br>
     *
     * @param buffer ByteBuf
     * @return string
     */
    public static String readString(ByteBuf buffer) {
        if(null == buffer) {
            throw new IllegalArgumentException("buffer is null");
        }
        int offset = buffer.readerIndex();
        return readString(buffer, offset);
    }

    /**
     * 根据offset获得以0x00结束的字符串<br>
     *
     * @param buffer ByteBuf
     * @param offset 偏移
     * @return string
     */
    public static String readString(ByteBuf buffer, int offset) {
        if(null == buffer) {
            throw new IllegalArgumentException("buffer is null");
        }
        // 扫描
        while (0x00 != buffer.getByte(buffer.readerIndex())) {
            buffer.skipBytes(1);
        }
        // 读取长度
        int length = buffer.readerIndex() - offset;
        // 丢弃0x00
        buffer.skipBytes(1);
        // 返回字符串
        return new String(ByteBufUtil.getBytes(buffer, offset, length));
    }

    private DataPackUtil(){}
}
