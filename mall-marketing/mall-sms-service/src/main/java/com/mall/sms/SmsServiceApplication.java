package com.mall.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * program: spring-cloud-mall->SmsServiceApplication
 * description:
 * author: gerry
 * created: 2019-12-13 21:58
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@ComponentScan(basePackages = {"com.mall"})
@MapperScan("com.mall.sms.mapper") // 扫描mapper包
public class SmsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsServiceApplication.class, args);
    }
}
