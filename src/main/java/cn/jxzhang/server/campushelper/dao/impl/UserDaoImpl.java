package cn.jxzhang.server.campushelper.dao.impl;

import cn.jxzhang.common.utils.CommonUtils;
import cn.jxzhang.server.campushelper.dao.UserDao;
import cn.jxzhang.server.campushelper.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

/**
 * Created on 2017-03-16 11:18
 * <p>Title:       UserDaoImpl</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {
    /**
     * LOG
     */
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate template;

    @Autowired
    public UserDaoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int insertUserAuth(User user) {
        String sql = "INSERT INTO campus_helper.user_auths(user_auths.auth_id,user_auths.user_id,user_auths.identity_type,user_auths.identifier,user_auths.credential, user_auths.is_third_party_account) VALUE (?,?,?,?,?,?)";
        return template.update(sql, CommonUtils.generateUUID(), user.getUserId(), user.getIdentityType(), user.getIdentifier(), user.getCredential(), user.getIsThirdPartyAccount());
    }

    @Override
    public boolean isAccountExist(User user) {
        String sql = "SELECT user_id as userId, identity_type as identityType, identifier FROM campus_helper.user_auths WHERE identifier = ? and identity_type = ?";
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();  // not reusable
        template.query(sql, new Object[]{user.getIdentifier(), user.getIdentityType()}, countCallback);

        return countCallback.getRowCount() != 0;
    }

    @Override
    public String generateUserId() {
        String sql = "INSERT INTO campus_helper.user(user.user_id) VALUE (?)";
        String userId = CommonUtils.generateUUID();
        template.update(sql, userId);
        return userId;
    }

    @Override
    public int insertAccountName(User user) {
        String sql = "UPDATE campus_helper.user SET account_name = ? WHERE user_id = ?";
        int effectedColumn = template.update(sql, user.getAccountName(), user.getUserId());
        return effectedColumn;
    }
}
