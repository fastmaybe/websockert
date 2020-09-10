package com.example.websockert.netty.task;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.TimeUnit;

/**
 * @Author: liulang
 * @Date: 2020/9/8 17:15
 */
public class ServerHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到消息："+buf.toString(CharsetUtil.UTF_8));

        //情况1 直接在这里处理业务 阻塞
//        doBusiness(ctx,1);

        //任务提交场景一  自定义加入task 将耗时任务加入任务队列 异步回调
//        ctx.channel().eventLoop().submit(()->{
//            doBusiness(ctx,2);
//        });
//
//        //任务队列内部是阻塞的  任务一个一个的排队处理
//        ctx.channel().eventLoop().submit(()->{
//            doBusiness(ctx,3);
//        });

        //任务场景二  定时任务
        ctx.channel().eventLoop().schedule(
                () -> {
                    doBusiness(ctx, 4);
                }, 5, TimeUnit.SECONDS
        );


        //任务场景三
        //思路 维护一个channel 容器 可以与用户对用上  然后执行任务


    }

    //数据读取完成
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好...",CharsetUtil.UTF_8));
    }

    private void doBusiness(ChannelHandlerContext ctx,int index){
        try {
            Thread.sleep(5000);

            ctx.writeAndFlush(Unpooled.copiedBuffer(Thread.currentThread().getName()+" 线程 doBusiness "+index+" ...处理完毕....",CharsetUtil.UTF_8));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
