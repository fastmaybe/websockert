package com.example.websockert.nio.base;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @Author: liulang
 * @Date: 2020/8/31 18:41
 */
public class BufferDemo {

    public static void main(String[] args) {
//        IntBuffer buffer = IntBuffer.allocate(5);
//        buffer.put(1);
//        buffer.put(2);
//
//        int i = buffer.get();
//        System.out.println(i);
//
//        ByteBuffer allocate = ByteBuffer.allocate(5);
//        allocate.put((byte) 'a');
//        allocate.put((byte) 'b');
//        allocate.flip();
//
//        byte b = allocate.get();
//        System.out.println(b);
        test();
    }

    public static void test(){
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put((byte) 'a');
        allocate.put((byte) 'b');

        allocate.flip();
        allocate.get();
        allocate.get();

        System.out.println(new String(allocate.array()));
    }
}
