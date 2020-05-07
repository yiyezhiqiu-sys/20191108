package com.mini;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    // 通道管理器(Selector)
    private static Selector selector;

    public static void main(String[] args) throws IOException {
        // 创建通道管理器(Selector)
        selector = Selector.open();// SelectorProvider#openSelector()-->WindowsSelectorProvider#openSelector()-->new WindowsSelectorImpl()

        // 创建通道ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//SelectorProvider.provider().openServerSocketChannel();--> new ServerSocketChannelImpl(this);
        // 将通道设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 将ServerSocketChannel对应的ServerSocket绑定到指定端口(port)
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8989));

        /**
         * 将通道(Channel)注册到通道管理器(Selector)，并为该通道注册selectionKey.OP_ACCEPT事件
         * 注册该事件后，当事件到达的时候，selector.select()会返回，
         * 如果事件没有到达selector.select()会一直阻塞。
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 循环处理
        while (true) {
            try{
                // 当注册事件到达时，方法返回，否则该方法会一直阻塞
                selector.select();
                // 获取监听事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 迭代处理
                while (iterator.hasNext()) {
                    // 获取事件
                    SelectionKey key = iterator.next();
                    // 客户端请求连接事件，接受客户端连接就绪
                    if (key.isAcceptable()) {
                        acceptHandler(key);
                    } else if (key.isReadable()) {// 监听到读事件，对读事件进行处理
                        readHandler(key);
                    }
                    // 移除事件，避免重复处理
                    iterator.remove();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理客户端连接成功事件
     */
    private static void acceptHandler(SelectionKey key) throws IOException {
        // 获取客户端连接通道
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = server.accept();
        socketChannel.configureBlocking(false); // 设置非阻塞
        // 信息通过通道发送给客户端
        socketChannel.write(ByteBuffer.wrap(new String("hello client ， 扑街！！").getBytes()));
        // 给通道设置读事件，客户端监听到读事件后，进行读取操作
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 监听到读事件，读取客户端发送过来的消息
     */
    private static void readHandler(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count ;
        buffer.clear();
        try {
            while ((count = channel.read(buffer)) > 0) {//将数据读到buffer里
                System.out.println(buffer.capacity());
                System.out.println(buffer.limit());
                System.out.println(buffer.position()+"==============================");
                buffer.flip();
                System.out.println(buffer.capacity());
                System.out.println(buffer.limit());
                System.out.println(buffer.position());
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                System.out.println("读取客户端消息: " + new String(bytes));
            }
            if (count < 0) {
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



