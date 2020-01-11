package com.mini.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorld {
    @RequestMapping("/hello")
    public Map<String,Object> showHelloWorld(){
        Map<String,Object>  map = new HashMap<String,Object>();
        map.put("msg", "HelloWorld");
        return map ;
    }
}

