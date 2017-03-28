package cn.jxzhang.server.campushelper.dao.impl;

import cn.jxzhang.common.CampusHelperException;
import cn.jxzhang.common.utils.CommonUtils;
import cn.jxzhang.common.utils.TextUtils;
import cn.jxzhang.server.campushelper.dao.UserDao;
import cn.jxzhang.server.campushelper.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

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
        String sql = "SELECT user_id AS userId, identity_type AS identityType, identifier FROM campus_helper.user_auths WHERE identifier = ? AND identity_type = ?";
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
    public int insertUserInfo(User user) {
        String sql = "UPDATE campus_helper.user SET " +
                "account_name = ? , " +
                "nickname = ?," +
                "head = ?," +
                "gender = ?," +
                "birth = ?," +
                "qq = ?," +
                "phone = ? ," +
                "email = ?," +
                "description = ? " +
                "WHERE user_id = ?";
        int effectedColumn = template.update(sql, user.getAccountName(),
                user.getNickname(), user.getHead(), user.getGender(), user.getBirth(),
                user.getQq(), user.getPhone(), user.getEmail(), user.getDescription(), user.getUserId());
        return effectedColumn;
    }

    @Override
    public void resetPassword(User user) {
        String sql = "UPDATE campus_helper.user_auths SET credential = ? WHERE identifier = ? AND identity_type = ?";
        template.update(sql, user.getCredential(), user.getIdentifier(), user.getIdentityType());
    }

    @Override
    public User getAccountType(User user) {
        String sql = "SELECT user_auths.identifier, user_auths.identity_type FROM campus_helper.user_auths WHERE identifier = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.queryForObject(sql, rowMapper, user.getIdentifier());
    }

    @Override
    public User signIn(User user) throws CampusHelperException {
        //1. 判断用户名密码是否存在
        String sql = "SELECT " +
                "campus_helper.user_auths.user_id AS 'userId', " +
                "campus_helper.user_auths.login_time AS 'loginTime'," +
                "campus_helper.user_auths.last_login_time AS 'lastLoginTime' ," +
                "campus_helper.user_auths.login_ip AS 'loginIp', " +
                "campus_helper.user_auths.identity_type AS 'identityType'" +
                "FROM campus_helper.user_auths WHERE identifier = ? AND credential = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> result = template.query(sql, rowMapper, user.getIdentifier(), user.getCredential());

        if (result == null || result.isEmpty()) {
            throw new CampusHelperException("用户名或密码不正确");
        }

        User authResult = result.get(0);

        System.out.println(authResult);

        //2. 插入用户登陆信息
        String insertUserInfoSQL = "UPDATE campus_helper.user_auths SET login_ip = ?, login_time = ?, last_login_time = ? WHERE campus_helper.user_auths.user_id = ? AND identity_type = ?";

        Long lastLoginTime = null;

        if (authResult.getLastLoginTime() == null) {
            lastLoginTime = System.currentTimeMillis();
        } else {
            lastLoginTime = authResult.getLoginTime();
        }

        authResult.setLoginTime(System.currentTimeMillis());
        authResult.setLastLoginTime(lastLoginTime);

        template.update(insertUserInfoSQL, user.getLoginIp(), authResult.getLoginTime(), authResult.getLastLoginTime(), authResult.getUserId(), authResult.getIdentityType());

        //3. 查询更新后的用户信息并返回
        String updatedUser = "SELECT" +
                "  u.user_id AS userId," +
                "  u.account_name AS accountName," +
                "  u.nickname," +
                "  u.head," +
                "  u.gender," +
                "  u.birth," +
                "  u.stu_no AS stuNo," +
                "  u.stu_class AS stuClass," +
                "  u.stu_dept AS stuDept," +
                "  u.stu_college AS stuCollege," +
                "  u.qq," +
                "  u.phone," +
                "  u.email, " +
                "  ua.identity_type AS identityType, " +
                "  ua.login_time AS loginTime, " +
                "  ua.last_login_time AS lastLoginTime, " +
                "  ua.is_third_party_account AS isThirdPartyAccount, " +
                "  ua.is_verified AS isVerified, " +
                "  ua.login_ip AS loginIp " +
                "FROM user u JOIN user_auths ua ON u.user_id = ua.user_id " +
                "WHERE u.user_id = ? AND ua.identity_type = ?";

        List<User> userInfos = template.query(updatedUser, rowMapper, authResult.getUserId(), authResult.getIdentityType());

        return userInfos.get(0);
    }
}
