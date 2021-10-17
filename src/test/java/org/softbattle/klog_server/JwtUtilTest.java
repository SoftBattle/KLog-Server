package org.softbattle.klog_server;

import org.junit.Test;
import org.softbattle.klog_server.utils.JwtUtil;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {

    @Test
    public void jwtCreateTest(){
        String uid = "userTest";
        System.out.println(JwtUtil.jwtCreate(uid));
    }

    @Test
    public void getUidTest(){
        String uid = "userTest";
        String token = JwtUtil.jwtCreate(uid);
        System.out.println(JwtUtil.getUid(token));
    }

}
