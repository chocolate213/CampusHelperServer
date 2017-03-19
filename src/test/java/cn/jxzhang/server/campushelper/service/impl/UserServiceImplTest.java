package cn.jxzhang.server.campushelper.service.impl;

import cn.jxzhang.common.utils.DigestUtils;
import cn.jxzhang.server.campushelper.pojo.User;
import cn.jxzhang.server.campushelper.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017-03-16 19:03
 * <p>Title:       UserServiceImplTest</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml" })
public class UserServiceImplTest {

    @Autowired
    UserService userService;


    @Before
    public void init() {

    }

    @Test
    public void regist() throws Exception {
        User user = new User();
        user.setCredential("123");
        userService.signUp(user);
    }

    @Test
    public void isAccountExist() throws Exception {
        User user = new User();
        user.setIsVerified("123455@qq.com");
    }

    @Test
    public void testList(){
        List list = new ArrayList();
        Object o = list.get(1);
        System.out.println(o);
    }

    @Test
    public void testMd5(){
        String s = DigestUtils.md5DigestAsHex("Hello World");
        System.out.println(s);
    }

}