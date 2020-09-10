package com.example.websockert.nio.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: liulang
 * @Date: 2020/9/1 17:51
 */
public class ChannelDemo {

    public static void main(String[] args) throws Exception {

//        read();
        reset();
    }

    public static void write() throws Exception {
        String content = "jsut test \n 123";
        FileOutputStream os = new FileOutputStream("demo.txt");

        FileChannel channel = os.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(content.length());
        buffer.put(content.getBytes());

        buffer.flip();
        channel.write(buffer);


        os.close();
    }


    public static void read() throws Exception {

        FileInputStream is = new FileInputStream("demo.txt");

        FileChannel channel = is.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(4);

        StringBuilder sb = new StringBuilder();
        while ((channel.read(buffer)) > 0) {
            sb.append(new String(buffer.array()));
            buffer.clear();
        }

        System.out.println(sb.toString());
    }


    public static void reset() throws Exception {

        IntBuffer buffer = IntBuffer.allocate(5);
        buffer.put(1);
        buffer.put(2);

        buffer.mark();
        buffer.put(3);
        buffer.put(4);

        buffer.reset();

        System.out.println();
    }
}
