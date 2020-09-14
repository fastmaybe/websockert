package com.example.websockert.netty.packdatademo3.demo4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:03
 * 编码器
 */
public class DYEncoder extends MessageToByteEncoder<ByteBuf> {



    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, ByteBuf out) throws Exception {
        //获取内容长度
        int bodyLen = msg.readableBytes();

        ByteBuf buf = Unpooled.buffer(bodyLen + 4);

        //写入消息头
        buf.writeShort(Constants.PROTOCOL_VERSION);

        //写入长度域
        buf.writeShort(bodyLen);

        //写入消息体
        buf.writeBytes(msg);

        //放入下一步
        out.writeBytes(buf);
    }
}
