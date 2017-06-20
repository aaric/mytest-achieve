package com.github.aaric.achieve.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * TimeClientHandler
 *
 * @author Aaric, created on 2017-06-20T10:28.
 * @since 1.0-SNAPSHOT
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("success");
        ctx.write(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, (byte) 0xFF});
        ctx.flush();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        try {
            System.out.println(ByteBufUtil.hexDump(buffer));

        } finally {
            ReferenceCountUtil.release(buffer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception");
        cause.printStackTrace();
        ctx.close();
    }
}
