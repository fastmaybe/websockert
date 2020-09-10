package com.example.websockert.nio.chartroomv2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: liulang
 * @Date: 2020/9/2 17:59
 */
public class ChartClient {


    private  final String serverIp = "127.0.0.1";

    private final int port = 6667;

    private Selector selector;

    private SocketChannel sc;

    private String username;

    public ChartClient() throws IOException {

        //获取selector
         selector = Selector.open();

         //连接
         sc = SocketChannel.open(new InetSocketAddress(serverIp, port));

         //设置非阻塞
        sc.configureBlocking(false);

        //将其注册到selector 事件为读
        sc.register(selector, SelectionKey.OP_READ);

         username = sc.getLocalAddress().toString();

        System.out.println(username+" is ok");

    }


    public void sendInfo(String  msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        sc.write(buffer);
    }

    public void readInfo() throws IOException {

        int select = selector.select();

        if (select > 0){
            //有可以操作的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();

                //判断是什么事件
                if (key.isReadable()){
                    //获取通道
                    SocketChannel channel = (SocketChannel) key.channel();

                    //获取buffer
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    channel.read(buffer);

                    System.out.println("收到："+ new String(buffer.array()));

                }
            }

            iterator.remove();

        }

    }

    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {

        ChartClient client = new ChartClient();

       //开启一个线程循环读取

        new Thread(()->{
            try {
                while (true){
                    client.readInfo();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();


        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String msg = scanner.nextLine();
            client.sendInfo(msg);
        }
    }

}
