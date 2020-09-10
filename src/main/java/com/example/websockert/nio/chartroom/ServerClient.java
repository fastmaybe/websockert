package com.example.websockert.nio.chartroom;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: liulang
 * @Date: 2020/9/2 10:41
 */
public class ServerClient {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //定义selector
        Selector selector = Selector.open();


        // 绑定端口监听
        ssc.socket().bind(new InetSocketAddress(6667));


        //将ssc设置为非阻塞
        ssc.configureBlocking(false);

        //最后将ssc注册到selector监听 连接事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //循环监听
        while (true){

            //获取selector 监听事件key
            if (selector.selectNow() == 0){
                //没有事件发生 跳出 重复下一次循环
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //处理事件
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            if (iterator.hasNext()){

                SelectionKey key = iterator.next();

                //连接事件
                if (key.isAcceptable()){
                    SocketChannel channel = ssc.accept();

                    channel.configureBlocking(false);

                    //注册到selector 且分配一个buffer
                    channel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println(channel.getRemoteAddress()+" 客户端连接");
                }

                //读事件
                if(key.isReadable()){
                    //获取通道
                    SocketChannel channel = (SocketChannel) key.channel();

                    //获取buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();

                    boolean flag= true;
                    //循环读取通道数据
                    while (flag){
                        int read = channel.read(buffer);

                        if (read <= 0) {
                            flag = false;
                        }else {

                            System.out.println("收到：" + new String(buffer.array()));
                            buffer.clear();
                            System.out.println();

                        }

                    }

                }

                iterator.remove();
            }

        }
    }
}
