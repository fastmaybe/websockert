package com.example.websockert.netty.packdatademo3.demo4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;
import java.util.List;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:05
 *
 * 解码器
 */
public class PackageSpliter extends LengthFieldBasedFrameDecoder {

    public PackageSpliter() {
        super(Integer.MAX_VALUE, Constants.LENGTH_OFFSET,Constants.LENGTH_BYTES_COUNT);
    }

    public PackageSpliter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public PackageSpliter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public PackageSpliter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public PackageSpliter(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }



    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本 协议版本号 的客户端 直接关闭

        short ver = in.getShort(0);
        short len = in.getShort(2);
//        System.out.println(ver);
//        System.out.println(len);

        /*
         * 如果协议协商好了 可以再这里做一个过滤 不是本协议的数据 直接关掉连接
         */

        if (ver != Constants.PROTOCOL_VERSION) {
            ctx.channel().close();
            System.err.println("ver is error");
            return null;
        }

        return super.decode(ctx, in);
    }
}
