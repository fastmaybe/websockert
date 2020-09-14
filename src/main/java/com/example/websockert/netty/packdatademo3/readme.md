#### LengthFieldBasedFrameDecoder
- maxFrameLength：指定了每个包所能传递的最大数据包大小；
- lengthFieldOffset： 指定了长度字段在字节码中的偏移量；
- lengthFieldLength：指定了长度字段所占用的字节长度；
- lengthAdjustment： 对一些不仅包含有消息头和消息体的数据进行消息头的长度的调整，这样就可以只得到消息体的数据，这里的lengthAdjustment指定的就是消息头的长度；
- initialBytesToStrip：对于长度字段在消息头中间的情况，可以通过initialBytesToStrip忽略掉消息头以及长度字段占用的字节。

#### LengthFieldPrepender
 这里LengthFieldBasedFrameDecoder与LengthFieldPrepender需要配合起来使用，其实本质上来讲，这两者一个是解码，一个是编码的关系。它们处理粘拆包的主要思想是在生成的数据包中添加一个长度字段，用于记录当前数据包的长度。LengthFieldBasedFrameDecoder会按照参数指定的包长度偏移量数据对接收到的数据进行解码，从而得到目标消息体数据；而LengthFieldPrepender则会在响应的数据前面添加指定的字节数据，这个字节数据中保存了当前消息体的整体字节数据长度。
 
 - demo1:简单的LengthFieldBasedFrameDecoder  initialBytesToStrip 删除了长度域 采用string 编解码
    
 - demo2:简单的LengthFieldBasedFrameDecoder initialBytesToStrip不删除长度域  仅用string编码  不解码 拿到
    buf自己完成。
 - demo3：定义DYLengthFieldPrepender 编码 加入魔数 Magic_num 
 
 - demo4：自定义 编解码器
