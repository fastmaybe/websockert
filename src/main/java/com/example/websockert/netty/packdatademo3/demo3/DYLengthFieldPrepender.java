package com.example.websockert.netty.packdatademo3.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author: liulang
 * @Date: 2020/9/14 16:04
 */
public class DYLengthFieldPrepender extends LengthFieldPrepender {

    private int Magic_num=78;

    public DYLengthFieldPrepender(int lengthFieldLength) {
        super(lengthFieldLength);
    }

    public DYLengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
        super(lengthFieldLength, lengthIncludesLengthFieldLength);
    }

    public DYLengthFieldPrepender(int lengthFieldLength, int lengthAdjustment) {
        super(lengthFieldLength, lengthAdjustment);
    }

    public DYLengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        super(lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    public DYLengthFieldPrepender(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        super(byteOrder, lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        //加入魔数
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(Magic_num);
        buffer.writeBytes(msg);
        System.out.println(buffer.toString(Charset.forName("utf-8")));
//        out.add(ctx.alloc().buffer(4).writeInt(78));
        super.encode(ctx, buffer, out);
    }
}
