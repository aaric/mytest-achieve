package com.github.aaric.achieve.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Netty test class
 *
 * @author Aaric, created on 2017-06-02T17:02.
 * @since 1.0-SNAPSHOT
 */
public class NettyTest {

    @Test
    public void testByteBuf() throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer("hello world".getBytes());

        while (0 < buffer.readableBytes()) {
            System.out.printf("readerIndex: %d, writerIndex: %d, readableBytes: %d\n", buffer.readerIndex(), buffer.writerIndex(), buffer.readableBytes());
            buffer.skipBytes(1);
        }
        buffer.discardSomeReadBytes();
        assert 0 == buffer.readableBytes();

        buffer.release();
    }

    @Test
    public void testCompositeByteBuf() throws Exception {
        CompositeByteBuf messageBuffer = Unpooled.compositeBuffer();

        ByteBuf headerBuffer = Unpooled.wrappedBuffer("header".getBytes());
        ByteBuf bodyBuffer = Unpooled.wrappedBuffer("body".getBytes());
        messageBuffer.addComponents(headerBuffer, bodyBuffer);

        //messageBuffer.removeComponent(0);
        for (ByteBuf buffer: messageBuffer) {
            System.out.println(buffer.toString());
        }
        assert 10 == messageBuffer.capacity();

        messageBuffer.release();
    }

    @Test
    public void testRead() throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer("hello world".getBytes());

        System.out.println(buffer.capacity());
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
        assert 11 == buffer.capacity();

        buffer.release();
    }

    @Test
    public void testWrite() throws Exception {
        ByteBuf buffer = Unpooled.buffer();

        buffer.writeCharSequence("hello world", Charset.defaultCharset());

        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
        assert 256 == buffer.capacity();

        buffer.release();
    }

    @Test
    public void testProcessor() throws Exception {
        ByteBuf buffer = Unpooled.wrappedBuffer("PATH=%PATH%;%JAVA_HOME%/bin".getBytes());

        System.out.println(buffer.forEachByte(ByteProcessor.FIND_SEMI_COLON));
        assert 11 == buffer.forEachByte(ByteProcessor.FIND_SEMI_COLON);

        buffer.release();
    }

    @Test
    public void testSlice() throws Exception {
        /**
         * duplicate()
         * slice()
         * slice(int, int)
         * Unpooled.unmodifiableBuffer(…)
         * order(ByteOrder)
         * readSlice(int)
         */
        Charset charset = Charset.defaultCharset();
        ByteBuf buffer = Unpooled.copiedBuffer("Netty in Action rocks!", charset);

        ByteBuf sliceBuffer = buffer.slice(0, 14);
        buffer.setByte(0, (byte) 'J');
        assert buffer.getByte(0) == sliceBuffer.getByte(0) : "they are not the same object";

        buffer.release();
    }

    @Test
    public void testCopy() throws Exception {
        Charset charset = Charset.defaultCharset();
        ByteBuf buffer = Unpooled.copiedBuffer("Netty in Action rocks!", charset);

        ByteBuf copyBuffer = buffer.copy(0, 14);
        buffer.setByte(0, (byte) 'J');
        assert buffer.getByte(0) != copyBuffer.getByte(0) : "they are the same object";

        copyBuffer.release();
        buffer.release();
    }

    @Test
    public void testSet() throws Exception {
        ByteBuf buffer = Unpooled.buffer(10);

        buffer.setBoolean(0, true);
        buffer.setBoolean(1, false);

        System.out.println(buffer.getByte(0));
        System.out.println(buffer.getByte(1));

        assert true == buffer.getBoolean(0);
        assert false == buffer.getBoolean(1);

        buffer.release();
    }

    @Test
    public void testGet() throws Exception {
        Charset charset = Charset.defaultCharset();
        ByteBuf buffer = Unpooled.copiedBuffer("Netty in Action rocks!", charset);

        byte[] nettyBytes = new byte[5];
        buffer.readBytes(nettyBytes);

        assert "Netty".equals(new String(nettyBytes, charset)) : "there are not the same object";
        assert 5 == buffer.readerIndex();

        buffer.release();
    }

    @Test
    public void testMethods() throws Exception {
        Charset charset = Charset.defaultCharset();
        ByteBuf buffer = Unpooled.copiedBuffer("Netty in Action rocks!", charset);

        if(buffer.isWritable()) {
            buffer.writeCharSequence(" Author: Nick.", charset);
        }

        if(buffer.isReadable()) {
            System.out.println(buffer.toString(charset));
            System.out.println(ByteBufUtil.hexDump(buffer));
        }

        assert 0 == buffer.readerIndex() : "Reader index is not 0";

        buffer.release();
    }
}
