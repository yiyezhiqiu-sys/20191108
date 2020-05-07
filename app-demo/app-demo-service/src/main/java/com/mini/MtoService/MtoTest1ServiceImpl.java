package com.mini.MtoService;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version="1.0")
public class MtoTest1ServiceImpl implements MtoTest1Service {
    @Override
    public String test1(String name) {
        return name+"test1";
    }
}
