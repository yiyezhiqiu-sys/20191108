package com.mini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jws.WebService;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ServletComponentScan
public class APP {

    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);

    }
}
