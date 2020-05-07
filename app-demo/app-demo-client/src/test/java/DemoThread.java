import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoThread {
    private static final int THREADS_COUNT = 2;

    public static int count = 0;
    public static volatile int countVolatile = 0;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static  void increase() {
        count++;
        countVolatile++;
        atomicInteger.incrementAndGet();
    }

    static int threadnum = 10 ;

    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName()+"start");

        ExecutorService  pool = Executors.newFixedThreadPool(threadnum);
       /* for (int i =0 ;i< threadnum ; i++){
            if(countDownLatch.getCount()==0){
                countDownLatch.await();
            }
            pool.execute(new ThreadA());
            countDownLatch.countDown();
        }*/



        ThreadA yy=   new ThreadA();
        Thread a = new Thread(yy);
        Thread a2 = new Thread(yy);
        Thread a3 = new Thread(yy);

        a.start();a.join();
        a2.start();
     //   a2.join();
        a3.start();
       // a3.join();

        System.out.println(Thread.currentThread().getName()+"end");
       // Thread.currentThread().join();

    }

//--------


}


class  ThreadA implements Runnable {

    @Override
    public void  run() {
      synchronized (ThreadA.class){
           try {
               System.out.println("子线程"+Thread.currentThread().getName()+"start");
               Thread.sleep(1000);//业务处理时间
               System.out.println("子线程"+Thread.currentThread().getName()+"end");


           } catch (Exception e) {
               e.printStackTrace();
           }
       }
     /*   try {
            System.out.println("子线程"+Thread.currentThread().getName()+"start");
            Thread.sleep(1000);//业务处理时间
            System.out.println("子线程"+Thread.currentThread().getName()+"end");

        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}

