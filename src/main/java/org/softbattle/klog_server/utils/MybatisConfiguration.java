package org.softbattle.klog_server.utils;

/**
 * @author Sun鹏
 * @create 2021-09-29 8:24
 *
 * 扫描mapper
 */
@Configuration
@MapperScanner("org.softbattle.klog_server.*.mapper")
public class MybatisConfiguration {
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();

}
