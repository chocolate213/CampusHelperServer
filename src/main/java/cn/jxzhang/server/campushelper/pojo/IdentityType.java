package cn.jxzhang.server.campushelper.pojo;

/**
 * Created on 2017-03-16 13:17
 * <p>Title:       IdentityType</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public enum IdentityType {
    /**
     * 1. 手机号
     */
    PHONE("1", "手机号"),

    /**
     * 2. 邮箱
     */
    EMAIL("2", "邮箱"),

    /**
     * 3. 用户名
     */
    USERNAME("3", "用户名"),

    /**
     * 4. QQ
     */
    QQ("4", "QQ"),

    /**
     * 5. 微信
     */
    WECHAT("5", "微信"),
    /**
     * 6. 微博
     */
    WEIBO("6", "微博");


    private final String type;
    private final String desc;

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    IdentityType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
