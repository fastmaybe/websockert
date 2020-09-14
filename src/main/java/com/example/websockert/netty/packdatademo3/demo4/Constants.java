package com.example.websockert.netty.packdatademo3.demo4;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:02
 */
public class Constants {

    //协议版本号
    public static final short PROTOCOL_VERSION = 1;
    //头部的长度： 版本号 + 报文长度
    public static final short PROTOCOL_HEADLENGTH = 4;
    //长度的偏移
    public static final short LENGTH_OFFSET = 2;
    //长度的字节数
    public static final short LENGTH_BYTES_COUNT = 2;


    public static final short lengthAdjustment = 0;

    public static final short initialBytesToStrip = 4;

}
