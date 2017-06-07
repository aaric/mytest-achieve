package com.github.aaric.achieve.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * DiscardServerHandler
 *
 * @author Aaric, created on 2017-06-07T10:43.
 * @since 1.0-SNAPSHOT
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    private static int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        try {
            // Output
            int length = buffer.writerIndex() - buffer.readerIndex();
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
            File file = new File(desktopPath, "output.dat");
            if(file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = ByteBufUtil.getBytes(buffer, 0, length);
            fos.write(bytes);
            System.out.printf("%d-%s\n", ++counter, ByteBufUtil.hexDump(bytes));
            fos.flush();
            fos.close();

            // Discard
            buffer.skipBytes(length);
            buffer.discardSomeReadBytes();

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
