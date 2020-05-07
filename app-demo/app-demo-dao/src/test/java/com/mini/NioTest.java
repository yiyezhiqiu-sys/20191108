package com.mini;

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.List;

public class NioTest {


    static int i = 9;

    public static <K, V> void main(String[] args) throws Exception {
      // new NioTest().test2();
        float   a  =  1111.5f;
         double  b = 1111.5d;  System.out.println(-9.0/0);
//byte i =;

       // short s = 32767 ;POSITIVE_INFINITY
       //  byte qq = 1 ;
       // System.out.println(Integer.toBinaryString(1) );;
//double c =123.124;
//double d = 123;
        tableSizeFor(10000);





       // Double.POSITIVE_INFINITY;
              //  Integer.numberOfLeadingZeros()
        //System.out.println(c-d);

       // testNio();
        //  System.out.println(test());
        //  System.out.println(test());
       /* Map<String,User> map =new HashMap();
        User u =new User();
        map.put("a",u);
        System.out.println(map.get("a").get());
        u.set("猴王");
        System.out.println(map.get("a").get())*/

     /*   String jj = "[{'name':'王大'},{'name':'王大','age':'12','sex':'男'}]";

         List<User> users = JSONArray.parseArray(jj, User.class);
        System.out.println(users);*/

    }

    static final void tableSizeFor(int cap) {//16384*0.75 = 12288>10000
        int n = cap - 1;
        System.out.println(n);
        n |= n >>> 1; //或运算 n=n|n>>>1
        System.out.println("1="+n);
        n |= n >>> 2;
        System.out.println("2="+n);
        n |= n >>> 4;
        System.out.println("3="+n);
        n |= n >>> 8;
        System.out.println("4="+n);
        n |= n >>> 16;
        System.out.println("5="+n);
       // return (n < 0) ? 1 : (n >= cap) ? cap : n + 1;
        System.out.println((n < 0) ? 1 : (n >= cap) ? cap : n + 1);
    }

    public static void test(int i) {

        i = 8;
        System.out.println("f=" + i);
        // return i ;
    }

    public static int test() {

        try {
            return i;
        } finally {
            i = 8;
            System.out.println("f=" + i);
        }
    }

    /*  public void test3()throws Exception{
          //创建ServerSocketChannel，监听8080端口
          ServerSocketChannel ssc = ServerSocketChannel.open();
          //通过ServerSocket绑定8080端口
          ssc.socket().bind(new InetSocketAddress(8080));//   //创建基于NIO通道的socket连接,新建socket通道的端口
          //设置非阻塞
          ssc.configureBlocking(false);
          //为ssc注册选择器
          Selector selector = Selector.open();
          ssc.register(selector, SelectionKey.OP_ACCEPT);
          //创建处理器
         // Handler handler = new Handler(1024);
          while(true){
              //等待请求，每次等待阻塞3s,超过3s后线程继续向下运行，如果传入0或者不传参则一直阻塞
              if(selector.select(3000)==0){
                  System.out.println("等待请求超时");
                  continue;
              }
              System.out.println("处理请求");
              //获取待处理的SelectionKey
              Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

              while (keyIter.hasNext()) {
                  SelectionKey key = (SelectionKey) keyIter.next();
                  try {
                      //接收到连接请求时
                      if(key.isAcceptable()){
                          handler.handleAccept(key);
                      }
                      //读数据
                      if(key.isReadable()){
                          handler.handleRead(key);
                      }
                  } catch (IOException e) {
                      keyIter.remove();
                      continue;
                  }
                  //处理完后，从待处理的SelectionKey迭代器中移除当前所使用的key
                  keyIter.remove();
              }

          }

      }


      public void handleAccept(SelectionKey key) throws IOException{
          //获取channel
          SocketChannel sc = (SocketChannel) key.channel();
          //获取buffer并重置
          ByteBuffer buffer = (ByteBuffer) key.attachment();
          buffer.clear();
          //没有读到内容则关闭
          if(sc.read(buffer)==-1){
              sc.close();
          }else{
              //将buffer转换为在读状态
              buffer.flip();
              //将buffer中接收到的值按localCharset格式编码后保存到receivedString
              String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
              System.out.println("接收到客户端："+receivedString);
              //返回数据给客户端
              String sendString = "收到的数据："+receivedString;
              buffer = ByteBuffer.wrap(sendString.getBytes(localCharset));
              sc.write(buffer);
              sc.close();
          }

      }


  */

    public static void testNio() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 设置为非阻塞式
        serverSocketChannel.socket().bind(new InetSocketAddress(9999)); // 绑定端口
        System.out.println("启动成功");
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept(); // 获取 tcp 连接通道
            if (socketChannel != null) {
                System.out.println("收到新的连接: " + socketChannel.getRemoteAddress());
                socketChannel.configureBlocking(false); // 默认是阻塞的，要设置成非阻塞
                try {
                    ByteBuffer requestByteBuffer = ByteBuffer.allocate(1024);
                    // 长连接情况下，需要手动判断数据有没有读取结束（这里做一个简单的判断，超过 0 就认为请求结束了）
                    while (socketChannel.isOpen() && socketChannel.read(requestByteBuffer) != -1) { // 读到 -1 表示连接已经断开
                        if (requestByteBuffer.position() > 0) {
                            break;
                        }
                    }
                    if (requestByteBuffer.position() == 0) continue; // 如果没有数据 则不继续处理
                    requestByteBuffer.flip();
                    byte[] content = new byte[requestByteBuffer.limit()];
                    requestByteBuffer.get(content);
                    System.out.println(new String(content));
                    System.out.println("收到数据，来自：" + socketChannel.getRemoteAddress());
                    // 响应结果 200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Hello World";
                    ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                    while (responseBuffer.hasRemaining()) {
                        socketChannel.write(responseBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void test2() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.capacity());// capacity =10
        System.out.println(buffer.limit());   // limit =10
        System.out.println(buffer.position()); // position =0 pistion可以理解为未读的元素位置
        System.out.println(buffer.mark());  //java.nio.HeapByteBuffer[pos=0 lim=10 cap=10]
        System.out.println("allocate方法===================================");
        buffer.put("abc".getBytes());
        System.out.println(buffer.capacity()); //capacity = 10
        System.out.println(buffer.limit());   // limit = 10
        System.out.println(buffer.position()); // position = 3
        System.out.println(buffer.mark()); //java.nio.HeapByteBuffer[pos=3 lim=10 cap=10]
        System.out.println("put方法===================================");
        buffer.flip();
        System.out.println(buffer.capacity()); //capacity = 10
        System.out.println(buffer.limit());   // limit = 3
        System.out.println(buffer.position()); // position = 0
        System.out.println(buffer.mark()); //java.nio.HeapByteBuffer[pos=0 lim=3 cap=10]
        System.out.println("flip===================================");
        System.out.println((char)buffer.get());
        System.out.println(buffer.capacity()); //capacity = 10
        System.out.println(buffer.limit());   // limit = 3
        System.out.println(buffer.position()); // position = 1
        System.out.println(buffer.mark()); //java.nio.HeapByteBuffer[pos=1 lim=3 cap=10]
        System.out.println("get===================================");
        /*buffer.rewind(); //position重新置为起始位置可再读。
        System.out.println(buffer.capacity()); //capacity = 10
        System.out.println(buffer.limit());   // limit = 3
        System.out.println(buffer.position()); // position = 0
        System.out.println(buffer.mark()); //java.nio.HeapByteBuffer[pos=0 lim=3 cap=10]
         System.out.println("===================================");
        */
        /*buffer.clear();//清空缓冲去
        System.out.println(buffer.capacity()); //capacity = 10
        System.out.println(buffer.limit());   // limit = 10
        System.out.println(buffer.position()); // position = 0
        System.out.println(buffer.mark()); //java.nio.HeapByteBuffer[pos=0 lim=10 cap=10]
        System.out.println("===================================");

        buffer.compact();//将为读的数据copy到bufferd其实处，然后将position设置为最后一个元素数据的位置。
        System.out.println(buffer.capacity()); //capacity = 10
        System.out.println(buffer.limit());   // limit = 10
        System.out.println(buffer.position()); // position = 2
        System.out.println(buffer.mark()); //java.nio.HeapByteBuffer[pos=2 lim=10 cap=10]
        System.out.println("compact===================================");

*/
    }

    public void test1() throws Exception {
        RandomAccessFile raf = new RandomAccessFile("D:/1.txt", "rw");
        FileChannel fileChannel = raf.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int readby = fileChannel.read(bb);
        while (readby != -1) {
            bb.flip();
            while (bb.hasRemaining()) {
                System.out.print((char) bb.get());
            }
            bb.clear();
            readby = fileChannel.read(bb);
            fileChannel.read(bb);
        }
    }
}