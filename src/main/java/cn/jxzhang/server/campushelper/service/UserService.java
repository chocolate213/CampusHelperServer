package cn.jxzhang.server.campushelper.service;

import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.server.campushelper.pojo.User;

/**
 * Created on 2017-03-16 11:16
 * <p>Title:       UserService</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public interface UserService {
    void signUp(User user);

    boolean isAccountExist(User user);

    void resetPassword(User user);

    User getAccountType(User user) throws CampusHelperException;

    User signIn(User user) throws CampusHelperException;
}
