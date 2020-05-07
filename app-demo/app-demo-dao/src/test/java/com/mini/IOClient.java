package com.mini;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class IOClient {

    public static final String IP="127.0.0.1";
    public static final int port=8989;
    private Socket s;
    SocketChannel sc ;
    public IOClient() throws IOException{
        try{
            s=new Socket(IP,port);
          //  sc = s.getChannel();
        }catch(IOException e){

            e.printStackTrace();
        }
    }

    public void setConnection()throws IOException{

        InputStream is;
        OutputStream out =null;
        try{
            //向服务端发送数据
            out = s.getOutputStream();
            out.write("hello everybody !".getBytes());

            //读取服务端数据
           is=s.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            System.out.println(br.readLine());

           /* ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            int count ;
            buffer2.clear();
            try {
                while ((count = sc.read(buffer2)) > 0) {
                    buffer2.flip();
                    byte[] bytes = new byte[buffer2.remaining()];
                    buffer2.get(bytes);
                    System.out.println("读取客户端消息: " + new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }catch(IOException e){

            e.printStackTrace();
        }


    }
    public static void main(String args[]) throws IOException {

        IOClient mc = new IOClient();
        mc.setConnection();

    }
}
