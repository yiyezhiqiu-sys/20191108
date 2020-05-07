package com.mini.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mini.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorld {

    @Autowired
    HelloWorldService helloWorldService;

    @RequestMapping("/hello")
    public Map<String,Object> showHelloWorld(){

        String temp = helloWorldService.hello("王大");

        Map<String,Object>  map = new HashMap<String,Object>();
        map.put("msg",temp );
        return map ;
    }
}

