package cn.jxzhang.common;

/**
 * Created on 2017-03-18 13:53
 * <p>Title:       CampusHelperException</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public class CampusHelperException extends Exception {
    public CampusHelperException() {
    }

    public CampusHelperException(String message) {
        super(message);
    }

    public CampusHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampusHelperException(Throwable cause) {
        super(cause);
    }

    public CampusHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
