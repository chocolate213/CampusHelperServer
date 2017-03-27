package cn.jxzhang.server.campushelper.dao;

import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.server.campushelper.pojo.User;

/**
 * Created on 2017-03-16 11:17
 * <p>Title:       UserDao</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public interface UserDao {

    int insertUserAuth(User user);

    boolean isAccountExist(User user);

    String generateUserId();

    int insertAccountName(User user);

    void resetPassword(User user);

    User getAccountType(User user);

    User signIn(User user) throws CampusHelperException;
}
