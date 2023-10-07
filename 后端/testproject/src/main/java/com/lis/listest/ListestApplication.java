package com.lis.listest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 需要添加Filter等？时需要使用此注解
@SpringBootApplication
@ServletComponentScan
public class ListestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListestApplication.class, args);
    }

}
