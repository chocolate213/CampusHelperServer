package cn.jxzhang.common;

import cn.jxzhang.common.message.ResponseMessage;

/**
 * Created on 2017-03-17 19:29
 * <p>Title:       BaseController</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public class BaseController {

    protected ResponseMessage SUCCESS() {
        return new ResponseMessage();
    }

    protected ResponseMessage SUCCESS(Object result) {
        return new ResponseMessage(result);
    }

    protected ResponseMessage FAIL(int code, String message) {
        return new ResponseMessage(null, code, message, false);
    }
}
