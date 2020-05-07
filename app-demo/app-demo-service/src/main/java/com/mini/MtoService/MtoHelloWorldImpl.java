package com.mini.MtoService;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mini.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;


@Service(version="2.0",weight=400 ,retries = 1)
public class MtoHelloWorldImpl implements  MtoHelloWorld{


    @Autowired
    HelloWorldService helloWorldService;

    @Override
    public String hello(String name) {
        return  helloWorldService.hello(name);
    }
}
