package com.mini.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceImpl implements  HelloWorldService {

    @Override
    public String hello(String name) {
        return name+"调用成功";
    }
}
