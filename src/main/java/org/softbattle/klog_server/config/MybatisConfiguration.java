package org.softbattle.klog_server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author Sun鹏
 * @create 2021-09-29 8:24
 *
 * 扫描mapper
 * 分页
 */
@Configuration
@MapperScan("org.softbattle.klog_server.*.mapper")
public class MybatisConfiguration {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();

    }
}
