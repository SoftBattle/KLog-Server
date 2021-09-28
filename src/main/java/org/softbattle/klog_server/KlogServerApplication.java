package org.softbattle.klog_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.softbattle.klog_server.*.mapper")
public class KlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlogServerApplication.class, args);
    }

}
