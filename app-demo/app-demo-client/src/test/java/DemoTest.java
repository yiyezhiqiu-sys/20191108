import org.apache.el.stream.Stream;

import java.lang.reflect.Type;
import java.util.*;

public class DemoTest extends B{



    static int a = 1;
    {
        //int b =2 ;
        System.out.println("a1="+a);
    }

    static  {
        int c =3 ;
        System.out.println("c="+c);
        System.out.println("a2="+a);
    }
    private static Map<Object, People> map = new LinkedHashMap();
   // private static Map<Object, People> map = new HashMap();
    public static void main(String[] args) {
        DemoTest d =new DemoTest();
        System.out.println("a=" +args.length );
        List<People> list = new ArrayList();
        People p1 = new Teacher();
        People p2 = new Student();
       list.add(p1);
       list.add(p2);
       synchronized (new Object()){

       }
        for (People p : list) {

            map.put( p.getClass().getName(), p);
        }

        for (Map.Entry m : map.entrySet()){
            System.out.println(m.getKey()+"=="+m.getValue());
        }



    }
}

class B{

  private   int f =9 ;
    static{
        System.out.println("静态B");
    }
    {
        System.out.println("普通代码块B");
    }

    final   void getB(){

    }
}
