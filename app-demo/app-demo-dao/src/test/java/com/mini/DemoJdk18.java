package com.mini;

import sun.text.bidi.BidiBase;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 1jdk8以前的i接口
 * interface 接口名{
 *     静态变量
 *     抽象方法
 * }
 * 2jdk8以后的接口
 * interface 接口名{
 *     静态变量
 *     抽象方法
 *     默认方法  可以被实现类重写，也可以不重写
 *     静态方法   不可以不实现类重写
 * }
 *
 *consumer 消费者、 supplier 供应者 、predicate 断言、function
 *
 * 3什么适合lamda，1）方法的参数是接口  2）接口必须是函数时接口 用@functioninterface可以在编译期校验接口中是否有一个抽象方法
 * **/

public class DemoJdk18 {

    public static void main(String[] args) {
    //=================================================================================================================1
        AA bb = new BB();
      new AA() {
          @Override
          public void test02() {
              System.out.println("我是未使用函数是接口AA未使用lamda表达式的");
          }
      }.test02();

      AA aa = () -> System.out.println("我是函数式接口AA test02抽象方法的实现");
      aa.test02();
     //================================================================================================================2
        List<String> listparam = Arrays.asList("peter", "anna", "mike", "xenia");
       /* Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });*/
        Collections.sort(listparam,( a, b)->{return b.compareTo(a);});
        listparam.sort(( a, b)->{return b.compareTo(a);});//参数比较器comparetor 等价Collections.sort

        System.out.println(listparam.toString());
        //==============================================================================================================3
        //mock 四大接口，我们只需要使用 Person::new 来获取Person类构造函数的引用，Java编译器会自动根据PersonFactory.create方法的签名来选择合适的构造函数。
        PersonFactory<Person> personFactory =(s1,s2)-> {return new Person(s1,s2);};
        Person person = personFactory.createOjb("侯","明");
        System.out.println("mock 4 大 interface : "+person.toString());

        Student<Person> st = new Student<Person>();
        st.consumerStudent((p)-> System.out.println(p.toString()),new Person("王","安石"));

        //Supplier 无参有返回值
        Supplier<Person> personSupplier = Person::new;
        System.out.println("Supplier:"+personSupplier.get());;   // new Person

        Supplier<String> supplier = ()->{return "supplier接口返回" ;};
        System.out.println(personSupplier.get());;

        //consumer 有参数无返回值
        Consumer<Person> greeter =  (p) -> System.out.println("consumer接口：Hello, " + p.firstName);
        greeter.accept(new Person("Luke", "Skywalker"));

        //Function 接口泛型T,R： 参数传入入T返回R
        Function<Person,String> function = person1 -> { return "Function接口返回"+person1.firstName+person1.lastName;};
        System.out.println(function.apply(new Person("侯", "function") ));

        //Predicate接口 传入T返回boolean
        Predicate<String> predicate = (s) -> {return s.length()>0;};//如果{}只有一条语句return 可以省略 如{s.length()>0;};
        System.out.println("predicate接口返回值："+predicate.test("abcd"));

        //==============================================================================================================4
        //optional
        Optional<String> optional = Optional.ofNullable(null);//返回(Optional)empty ,如果是Optional.of(null)包空指针
       System.out.println( Optional.empty());
        System.out.println( optional.isPresent());
        System.out.println( optional);
        System.out.println(optional.orElse("abc"));

        //==============================================================================================================5
        //Stream,filter，map,match
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection
                .stream()
                .filter((s) -> {return s.startsWith("a");})
                .forEach(System.out::println);


        stringCollection
                .stream()
                .map(String::toUpperCase)    //(s)->s.toUpperCase()
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

//Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。所有的匹配操作都是最终操作，并返回一个boolean类型的值。
        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true

        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));

        System.out.println(noneStartsWithZ);      // true

        //并行stream 前面提到过Stream有串行和并行两种，串行Stream上的操作是在一个线程中依次完成，而并行Stream则是在多个线程上同时执行。
        int max = 1000000;
       /* List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }*/
        //串行排序，计算排序时间
 /*       long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));//1408 ms*/
        //并行排序计算排序时间
        /*long tp0 = System.nanoTime();
        long pcount = values.parallelStream().sorted().count();
        System.out.println(pcount);

        long tp1 = System.nanoTime();

        long pmillis = TimeUnit.NANOSECONDS.toMillis(tp1 - tp0);
        System.out.println(String.format("parallel sort took: %d ms", pmillis));//792 ms*/
        //--------------------------------------------------------------------------------------------------------------7
        //Date API
        //Java 8 在包java.time下包含了一组全新的时间日期API。新的日期API和开源的Joda-Time库差不多，但又不完全一样，下面的例子展示了这组新API里最重要的一些部分：

        //Clock类提供了访问当前日期和时间的方法，Clock是时区敏感的，可以用来取代 System.currentTimeMillis() 来获取当前的微秒数。某一个特定的时间点也可以使用Instant类来表示，Instant类也可以用来创建老的java.util.Date对象。
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
        System.out.println("clock="+legacyDate);
        //Timezones 时区
        //在新API中时区使用ZoneId来表示。时区可以很方便的使用静态方法of来获取到。 时区定义了到UTS时间的时间差，在Instant时间点对象到本地日期对象之间转换的时候是极其重要的
        System.out.println(ZoneId.getAvailableZoneIds());
      // prints all available timezone ids
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

// ZoneRules[currentStandardOffset=+01:00]
// ZoneRules[currentStandardOffset=-03:00]

        //LocalTime 本地时间
        //LocalTime 定义了一个没有时区信息的时间，例如 晚上10点，或者 17:30:15。下面的例子使用前面代码创建的时区创建了两个本地时间。之后比较时间并以小时和分钟为单位计算两个时间的时间差：
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        System.out.println(now1.isBefore(now2));  // false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239

        //LocalDate 本地日期
        //
        //LocalDate 表示了一个确切的日期，比如 2014-03-11。该对象值是不可变的，用起来和LocalTime基本一致。下面的例子展示了如何给Date对象加减天/月/年。另外要注意的是这些对象是不可变的，操作返回的总是一个新实例。

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();

        //LocalDateTime 本地日期时间
        //LocalDateTime 同时表示了时间和日期，相当于前两节内容合并到一个对象上了。LocalDateTime和LocalTime还有LocalDate一样，都是不可变的。LocalDateTime提供了一些能访问具体字段的方法。
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
        DayOfWeek dayOfWeek2 = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek2);      // WEDNESDAY

        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439

    }



    static void compareLamda(){


        Comparator<Integer> cpt = (o1, o2) -> Integer.compare(o1,o2);
        List a = new ArrayList();
        a.add(2);a.add(1);
        Collections.sort(a,cpt);
        System.out.println(a);


    }
}

//=========================================start
// 默认方法：使用北京是，当接口被很多实现类实现的时候，如果在接口中新曾功能那么很多 实现类都要去实现这个功能。这不符合实际，于是用默认方法实现了可以重写也可以不重写
// 接口中静态方法 : 调用接口名.方法名。静态方法不能被实现了重写，只能是接口名称调用
@FunctionalInterface
interface AA{

   int a =10 ;

    public default void   test(){
        System.out.println("我是接口AA默认方法，实现类可实现也可以不是实现");
    }

    public static void test01(){
        System.out.println("我是接口中的静态方法AA不能被子类方法重写");
    }

    void test02();//


}
class BB implements  AA{
    @Override
    public void   test(){
        System.out.println("我是实现类BB");
    }

    @Override
    public void test02() {

    }

   /* @Override //override报错说明不能继承
    public static void test01(){
        System.out.println("我是接口中的静态方法AA");
    } */
}
//===========================================end

//========================================start 内置函数试接口 常用的函数式接口


class Person {
    String firstName;
    String lastName;
    Person() {
        System.out.println("我是persion类的无参构造");
    }

    Person(String firstName, String lastName) {
        System.out.println("我是persion类的有参构造");
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return this.firstName+this.lastName;
    }
}
@FunctionalInterface
interface PersonFactory<T> {
    T createOjb(String firstName, String lastName);
   // P create();
}

/**mock 函数参数是接口类型*/
class Student<T>{

    public void consumerStudent(Consumer<T> consumer,T t) {
        consumer.accept(t);
    }
}