package org.softbattle.klog_server;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softbattle.klog_server.user.controller.UserRest;
import org.softbattle.klog_server.user.result.Result;
import org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest(classes = KlogServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserRest userRest;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * 用户注册测试
     */
    @Test
    public void userRegistTest() throws Exception {
        String uid = "userTest";
        String passwd = "123456";
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/regist")
                .param("uid", uid)
                .param("passwd", passwd));
        resultActions.andReturn()
                .getResponse()
                .setCharacterEncoding("UTF-8");
        resultActions.andDo(MockMvcResultHandlers.print());

    }

}
