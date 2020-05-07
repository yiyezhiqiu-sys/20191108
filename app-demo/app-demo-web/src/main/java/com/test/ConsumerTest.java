package com.test;

import com.mini.MtoService.MtoHelloWorld;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class ConsumerTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-demo-consumer.xml");
        context.start();
        // Obtaining a remote service proxy
        MtoHelloWorld demoService = (MtoHelloWorld)context.getBean("demoService");
        // Executing remote methods
        String hello = demoService.hello("明天会更好");
        // Display the call result

    }
}
