package cn.jxzhang.server.campushelper.controller;

import cn.jxzhang.common.BaseController;
import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.common.message.ResponseMessage;
import cn.jxzhang.server.campushelper.pojo.User;
import cn.jxzhang.server.campushelper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
public class UserController extends BaseController{
    /**
     * LOG
     */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
    public ResponseMessage isAccountExist(@RequestBody User user){
        boolean exist = userService.isAccountExist(user);
        log.debug("is identifier : {} exist ? {}", user.getIdentifier(), exist);
        return SUCCESS(exist);
    }
}
