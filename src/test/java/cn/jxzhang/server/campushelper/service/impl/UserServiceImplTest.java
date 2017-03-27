package cn.jxzhang.server.campushelper.service.impl;

import cn.jxzhang.common.RegexString;
import cn.jxzhang.common.message.RequestMessage;
import cn.jxzhang.common.message.ResponseMessage;
import cn.jxzhang.common.utils.CommonUtils;
import cn.jxzhang.common.utils.DigestUtils;
import cn.jxzhang.common.utils.JsonUtils;
import cn.jxzhang.common.utils.MailUtil;
import cn.jxzhang.server.campushelper.pojo.User;
import cn.jxzhang.server.campushelper.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Test
    public void generateRandomNumber(){
        Integer a = (Integer) test();
        System.out.println(a);

    }

    public Object test() {
        return null;
    }

    @Test
    public void testEmail() throws Exception {
        sendEmail("zhangjiaxing@ultrapower.com.cn", "845658");
    }

    private void sendEmail(String account, String code) throws Exception {
        String template = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.html"));
        String content = String.format(template, code);
        MailUtil.sendEmail(account, "校园助手 - 账户验证", content);
    }

    @Test
    public void testGson(){
        ResponseMessage<User> message = new ResponseMessage<>();
        User user1 = new User();
        user1.setAccountName("zhangSan");
        message.setResult(user1);

        String result = JsonUtils.toJson(message);

        System.out.println(result);

        ResponseMessage<User> message1 = JsonUtils.fromJson(result, ResponseMessage.class);


//        Type type = ;
        Gson gson = new Gson();

        ResponseMessage<User> r = gson.fromJson(result, new TypeToken<ResponseMessage<User>>() {

        }.getType());

        System.out.println(r.getResult().getClass());


    }

    @Test
    public void testRegex(){
        String REGEX_EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        System.out.println("1010110@qq.com".matches(REGEX_EMAIL));
    }

    @Test
    public void testEnum(){
        System.out.println(HttpStatus.ACCEPTED);
    }

    @Test
    public void testLongToDate(){
        String s = "6000000";

        Long l = Long.parseLong(s);

        String s1 = generateDurationTime(l);

        System.out.println(s1);
    }

    private String generateDurationTime(long durationTime) {
        long hours = durationTime / 3600000L;
        long minutes = durationTime % 3600000L / 60000L;
        long seconds = durationTime % 3600000L % 60000L / 1000L;

        if(hours == 0 && minutes == 0 ) {
            return seconds + "秒";
        }

        if (hours == 0) {
            return minutes + "分钟" + seconds + "秒";
        }

        return hours + "小时" + minutes + "分钟" + seconds + "秒";
    }

}