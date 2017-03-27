package cn.jxzhang.server.campushelper.service.impl;

import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.common.utils.CommonUtils;
import cn.jxzhang.common.utils.TextUtils;
import cn.jxzhang.server.campushelper.dao.UserDao;
import cn.jxzhang.server.campushelper.pojo.IdentityType;
import cn.jxzhang.server.campushelper.pojo.User;
import cn.jxzhang.server.campushelper.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2017-03-16 11:17
 * <p>Title:       UserServiceImpl</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * LOG
     */
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void signUp(User user) {
        String userId = userDao.generateUserId();
        patchThirdPartyAccountFlag(user);
        user.setUserId(userId);

        if (IdentityType.EMAIL.getType().equals(user.getIdentityType())) {
            user.setIsThirdPartyAccount("0");
            userDao.insertUserAuth(user);
            log.debug("signUp user : {} with identifier : {}", user.getUserId(), user.getIdentifier());
            user.setIdentifier(user.getAccountName());
            user.setIdentityType(IdentityType.USERNAME.getType());
            userDao.insertUserAuth(user);
        }

        if (IdentityType.PHONE.getType().equals(user.getIdentityType())) {
            userDao.insertUserAuth(user);
        }

        if (TextUtils.isEmpty(user.getAccountName())) {
            String name = generateUserName();
            user.setAccountName(name);
        }

        userDao.insertAccountName(user);

        log.debug("signUp user : {} with identifier : {}", user.getUserId(), user.getIdentifier());
    }

    private String generateUserName() {
        return "mobile_" + CommonUtils.generateUUID().substring(0, 10);
    }

    private void patchThirdPartyAccountFlag(User user) {
        if (IdentityType.PHONE.getType().equals(user.getIdentityType()) || IdentityType.EMAIL.getType().equals(user.getIdentityType()) || IdentityType.USERNAME.getType().equals(user.getIdentityType())) {
            user.setIsThirdPartyAccount("0");
        } else {
            user.setIsThirdPartyAccount("1");
        }
    }

    @Override
    public boolean isAccountExist(User user) {
        return userDao.isAccountExist(user);
    }

    @Transactional
    @Override
    public void resetPassword(User user) {
        userDao.resetPassword(user);
    }

    @Override
    public User getAccountType(User user) throws CampusHelperException {
        User result = null;
        try {
            result = userDao.getAccountType(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.error("用户不存在",e);
            throw new CampusHelperException("用户不存在", e);
        }
        return result;
    }

    @Override
    public User signIn(User user) throws CampusHelperException {
        return userDao.signIn(user);
    }
}
