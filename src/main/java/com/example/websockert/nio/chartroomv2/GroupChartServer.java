package com.example.websockert.nio.chartroomv2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: liulang
 * @Date: 2020/9/2 17:58
 */
public class GroupChartServer {

    private Selector selector;

    private int port = 6667;

    private ServerSocketChannel ssc;

    public GroupChartServer(){

        try {
            ssc = ServerSocketChannel.open();

            ssc.configureBlocking(false);

            selector = Selector.open();

            ssc.socket().bind(new InetSocketAddress(port));

            ssc.register(selector, SelectionKey.OP_ACCEPT);

        }catch (Exception e){

        }

    }


    public void listen(){

        try {

            while (true){

                if (selector.select()==0){
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                if (iterator.hasNext()){
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()){
                        addAccept();
                    }

                    if (key.isReadable()){
                        readData(key);
                    }


                    iterator.remove();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) throws Exception {
        //获取通道
        SocketChannel channel = (SocketChannel) key.channel();

        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            int read = channel.read(buffer);

            if (read > 0) {
                String msg = new String(buffer.array());
                System.out.println("服务器收到："+msg);

                sendMsgToOthers(channel,msg);
            }
        } catch (Exception e) {
            channel.close();
            System.out.println("客户端 断开了一个...");
        }
    }

    private void sendMsgToOthers(SocketChannel self, String msg) throws Exception{

        Set<SelectionKey> keys = selector.keys();


        for (SelectionKey key : keys) {
            SelectableChannel channel = key.channel();
            if (channel instanceof  SocketChannel && channel!= self){

                //将msg存到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //写入到通道
                ((SocketChannel) channel).write(buffer);
            }
        }

    }

    private void addAccept() throws IOException {
        SocketChannel channel = ssc.accept();

        channel.configureBlocking(false);

        channel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

        System.out.println(channel.getRemoteAddress()+" 客户端进入了房间");
    }


    public static void main(String[] args) {
        GroupChartServer server = new GroupChartServer();
        server.listen();
    }
}
