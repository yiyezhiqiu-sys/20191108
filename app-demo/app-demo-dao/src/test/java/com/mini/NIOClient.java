package com.mini;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class NIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 创建通道SocketChannel
        SocketChannel channel = SocketChannel.open();
        // 将通道设置为非阻塞
        channel.configureBlocking(false);
        // 客户端连接服务器，其实方法执行并没有实现连接，
        // 需要在handleConnect方法中调channel.finishConnect()才能完成连接
        channel.connect(new InetSocketAddress("localhost", 8989));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (!channel.finishConnect()) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("111111111111111111");
        }
        for (int i=0; i<5; i++) {
            TimeUnit.MICROSECONDS.sleep(2);
            String message = "send message " + i + " from" + "AAAAA";
            buffer.put(message.getBytes());
            buffer.flip();
            //buffer先把数据读入到buffer，然后channel先把buffer中的数据写入到channel，
            channel.write(buffer);
            buffer.clear();
        }

        {{
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            int count ;
            buffer2.clear();
            try {
                while ((count = channel.read(buffer2)) > 0) {
                    buffer2.flip();
                    byte[] bytes = new byte[buffer2.remaining()];
                    buffer2.get(bytes);
                    System.out.println("读取客户端消息: " + new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}
        channel.close();
    }
}