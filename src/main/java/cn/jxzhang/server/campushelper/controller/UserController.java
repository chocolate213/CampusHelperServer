package cn.jxzhang.server.campushelper.controller;

import cn.jxzhang.common.BaseController;
import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.common.message.ResponseMessage;
import cn.jxzhang.common.utils.CommonUtils;
import cn.jxzhang.server.campushelper.pojo.User;
import cn.jxzhang.server.campushelper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private static final int REQUEST_VERIFY_CODE_THRESOULD_TIMES = 5;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @ResponseBody
    @RequestMapping("isAccountExist")
    public ResponseMessage isAccountExist(@RequestBody User user) {
        boolean exist = userService.isAccountExist(user);
        log.info("is identifier : {} exist ? {}", user.getIdentifier(), exist);
        return SUCCESS(exist);
    }

    @ResponseBody
    @RequestMapping("verifyCode/generateVerifyCode/{account}")
    public ResponseMessage generateVerifyCode(@PathVariable("account") String account, HttpSession session, HttpServletRequest request) throws CampusHelperException {
        log.info("attempt to generate verify code for account : {}", account);
        verifyRequestForVerifyCodeTimes(session, request);
        String code = CommonUtils.generateRandomCode(6);
        log.info("generated verify code : " + code);
        session.setAttribute(account, code);
        return SUCCESS(code);
    }

    @ResponseBody
    @RequestMapping("verifyCode/verifyVerifyCode/{account}/{code}")
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
     * 校验验证码请求次数，如果某IP地址请求验证码超过三次，将拒绝返回验证码
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
