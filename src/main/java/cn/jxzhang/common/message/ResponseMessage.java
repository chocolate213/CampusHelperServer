package cn.jxzhang.common.message;

/**
 * Created on 2017-03-17 19:07
 * <p>Title:       ResponseMessage</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public class ResponseMessage<T> {

    /**
     * 返回结果
     */
    private T result;
    /**
     * 状态码
     */
    private int code = 0;
    /**
     * 消息
     */
    private String message = "";

    /**
     * 是否成功
     */
    private boolean success = true;

    public ResponseMessage(T result, int code, String message, boolean success) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public ResponseMessage(T result) {
        this.result = result;
    }

    public ResponseMessage() {

    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
