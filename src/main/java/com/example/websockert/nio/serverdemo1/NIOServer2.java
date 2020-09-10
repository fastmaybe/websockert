package com.example.websockert.nio.serverdemo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: liulang
 * @Date: 2020/9/2 10:29
 */
public class NIOServer2 {

    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //selector
        Selector selector = Selector.open();

        // 绑定端口监听
        ssc.socket().bind(new InetSocketAddress(6667));

        //设置为非阻塞
        ssc.configureBlocking(false);

        //将ssc注册到 selector 事件为连接事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待连接
        while (true){
            //获取selector 事件
            if (selector.select(1000) == 0){
                continue;
            }

            //返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                //迭代器迭代
                SelectionKey selectionKey = iterator.next();

                //连接事件
                if (selectionKey.isAcceptable()){
                    SocketChannel socketChannel = ssc.accept();

                    //注意将socketChannel 设置为非阻塞  否则连接后会报错 Exception in thread "main" java.nio.channels.IllegalBlockingModeException
                    socketChannel.configureBlocking(false);

                    System.out.println("新客户端连接了：" + socketChannel.getRemoteAddress());

                    //将其注册到selector 关注事件为read 且分配一个buffer
                    //每个都有独立的 channel - > buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }

                //读事件
                if (selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();

                    //获取对应的buffer
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();

                    int read = channel.read(buffer);
                    if (read <0){
                        continue;
                    }

                    buffer.flip();

                    System.out.println(new String(buffer.array()));


                    buffer.clear();
                }

                //最后移除key 避免重复操作
                iterator.remove();

            }


        }
    }
}
