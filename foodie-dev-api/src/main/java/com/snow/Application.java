package com.snow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.snow.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.snow", "org.n3r.idworker"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
