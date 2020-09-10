package com.example.websockert.nio.chartroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: liulang
 * @Date: 2020/9/2 11:07
 */
public class Client {

    public static void main(String[] args) throws IOException {

        SocketChannel sc = SocketChannel.open();

        //设置非阻塞
        sc.configureBlocking(false);

        //服务端地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6667);

        if (!sc.connect(address)){
            while (!sc.finishConnect()){
                System.out.println("...");
            }
        }

        String content= "hello ---";

        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());

        //发送数据
        sc.write(buffer);
        System.in.read();
        System.out.println("关闭client");

    }
}
