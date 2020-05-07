package com.mini.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mini.service.HelloWorldService;
import org.springframework.stereotype.Component;
public class HelloConsumerService {

 //    @Reference(version="1.0")
    HelloWorldService helloWorldService;        //不能在Controller中直接Reference,需要使用Component优先加载Bean，否则会报空指针问题

    public String rest(String name) {
        return helloWorldService.hello(name);
    }

}
