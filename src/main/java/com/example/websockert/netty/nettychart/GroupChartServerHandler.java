package com.example.websockert.netty.nettychart;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: liulang
 * @Date: 2020/9/10 16:10
 */
public class GroupChartServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 这里可以用自带的 channle容器管理 或者 自定义也可以；
     * 此处未方便  使用netty提供的
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //一旦链接 第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息 发送给其他在线的  客户端

        // 该方法 会遍历全部的 channel 并且发送消息
        channelGroup.writeAndFlush("[客户端] "+channel.remoteAddress()+"加入了聊天"+sdf.format(new Date())+"\n");

        channelGroup.add(channel);

    }

    //断开链接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端] "+channel.remoteAddress()+"离开了\n");
        System.out.println("当前channelGroup size:"+channelGroup.size());
    }


    //活动状态  可以想象qq在线活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" 上线了...");
    }

    //标识channel不活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了...");
    }


    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        //遍历channelGroup 发送消息
        channelGroup.forEach(e->{
            if (channel != e){
                //非自身 转发消息
                e.writeAndFlush("[客户]"+channel.remoteAddress()+"发送了消息："+msg+"\n");
            }else {
                e.writeAndFlush("[自己]发送了消息："+msg);
            }
        });
    }


    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
