package cn.jxzhang.server.campushelper.controller;

import cn.jxzhang.common.BaseController;
import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.common.RegexString;
import cn.jxzhang.common.message.ResponseMessage;
import cn.jxzhang.common.utils.CommonUtils;
import cn.jxzhang.common.utils.MailUtil;
import cn.jxzhang.common.utils.TextUtils;
import cn.jxzhang.server.campushelper.pojo.IdentityType;
import cn.jxzhang.server.campushelper.pojo.User;
import cn.jxzhang.server.campushelper.service.UserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created on 2017-03-16 11:12
 * <p>Title:       UserController</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
@RequestMapping("account")
@Controller
public class UserController extends BaseController {
    /**
     * LOG
     */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final int REQUEST_VERIFY_CODE_THRESOULD_TIMES = 10;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册账户
     *
     * @param user 待注册的账户
     * @return
     * @throws CampusHelperException
     */
    @ResponseBody
    @RequestMapping("signUp")
    public ResponseMessage signUp(@RequestBody User user) throws CampusHelperException {
        log.debug("attempt to register user: {}", user);
        try {
            userService.signUp(user);
        } catch (Exception e) {
            throw new CampusHelperException("注册失败", e);
        }
        log.debug("register user: {} success", user);
        return SUCCESS();
    }

    /**
     * 判断账户是否存在
     *
     * @param user 待判断的账户
     * @return
     */
    @ResponseBody
    @RequestMapping("isAccountExist")
    public ResponseMessage isAccountExist(@RequestBody User user) {
        boolean exist = userService.isAccountExist(user);
        log.info("is identifier : {} exist ? {}", user.getIdentifier(), exist);
        return SUCCESS(exist);
    }

    /**
     * 发送验证码
     *
     * @param account 待发送验证码的账户
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("sendVerifyCode/{account}")
    public ResponseMessage sendVerifyCode(@PathVariable("account") String account, HttpSession session, HttpServletRequest request) throws Exception {
        User user = sendVerifyCodeToAccount(account, session, request);
        return SUCCESS(user);
    }

    /**
     * 校验验证码
     *
     * @param account 待校验验证码的账户
     * @param code
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("verifyVerifyCode/{account}/{code}")
    public ResponseMessage verifyVerifyCode(@PathVariable("account") String account,
                                            @PathVariable("code") String code,
                                            HttpSession session) {
        log.info("attempt to verify for account : {}", account);
        String validCode = (String) session.getAttribute(account);

        log.info("input verify code : {}, correct verify code : {}", code, validCode);
        if (ObjectUtils.isEmpty(validCode)) {
            return SUCCESS(false);
        } else if (validCode.equals(code)) {
            return SUCCESS(true);
        } else {
            return SUCCESS(false);
        }
    }

    /**
     * 生成验证码
     *
     * @param account 账户名
     * @param session Session对象
     * @param request Request对象
     * @return 验证码
     * @throws CampusHelperException 用户名错误时抛出CampusHelperException
     */
    private String generateVerifyCode(String account, HttpSession session, HttpServletRequest request) throws CampusHelperException {
        if (TextUtils.isEmpty(account)) {
            throw new CampusHelperException("参数错误，请检查");
        }
        log.info("attempt to generate verify code for account : {}", account);
        verifyRequestForVerifyCodeTimes(session, request);
        String code = CommonUtils.generateRandomCode(6);
        log.info("generated verify code : " + code);
        session.setAttribute(account, code);
        return code;
    }


    /**
     * 发送验证码
     *
     * @param account 账户名
     * @param session Session对象
     * @param request Request对象
     * @return 发送验证码账户
     * @throws Exception
     */
    private User sendVerifyCodeToAccount(String account, HttpSession session, HttpServletRequest request) throws Exception {
        if (TextUtils.isEmpty(account)) {
            throw new CampusHelperException("参数错误，请检查");
        }

        User user = new User();
        String code = generateVerifyCode(account, session, request);
        user.setVerifyCode(code);

        if (account.matches(RegexString.REGEX_EMAIL)) {
            user.setIdentityType(IdentityType.EMAIL.getType());
            sendEmail(account, code);
        } else if (account.length() == 11 && account.startsWith("1")) {
            user.setIdentityType(IdentityType.PHONE.getType());
            sendSMS(account, code);
        } else {
            throw new CampusHelperException("参数错误，请检查");
        }
        return user;
    }

    /**
     * 发送短信验证码
     *
     * @param account 收信人手机号
     * @param code    验证码
     */
    private void sendSMS(String account, String code) {
        log.info("send SMS to : {}, verify code : {}", account, code);
    }

    /**
     * 发送邮件验证码
     *
     * @param account 收信人邮箱
     * @param code    验证码
     */
    private void sendEmail(String account, String code) throws Exception {
        log.info("send EMAIL to : {}, verify code : {}", account, code);
        String template = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.html"));
        String content = String.format(template, code);
        MailUtil.sendEmail(account, "校园助手 - 账户验证", content);
    }

    /**
     * 校验验证码请求次数，如果某IP地址请求验证码超过五次，将拒绝返回验证码
     *
     * @param session
     * @param request
     * @throws CampusHelperException
     */
    private void verifyRequestForVerifyCodeTimes(HttpSession session, HttpServletRequest request) throws CampusHelperException {
        String ip = request.getRemoteAddr();
        Integer times = (Integer) session.getAttribute(ip);

        if (times == null) {
            times = 0;
        }

        ++times;

        log.info("client ip : {} , the {} time request for verify code.", ip, times);

        if (times >= REQUEST_VERIFY_CODE_THRESOULD_TIMES) {
            throw new CampusHelperException("您的验证码请求次数过于频繁，请稍后再试");
        }

        session.setAttribute(ip, times);
    }
}
