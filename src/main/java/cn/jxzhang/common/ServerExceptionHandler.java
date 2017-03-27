package cn.jxzhang.common;

import cn.jxzhang.common.message.ResponseMessage;
import cn.jxzhang.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2017-03-18 14:24
 * <p>Title:       ServerExceptionHandler</p>
 * <p>Description: [Description]</p>
 *
 * @author <a href="mailto:zhangjiaxing@ultrapower.com.cn">zhangjiaxing</a>
 * @version 1.0
 */
public class ServerExceptionHandler implements HandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(ServerExceptionHandler.class);


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setSuccess(false);
            responseMessage.setCode(2);
            responseMessage.setMessage(ex.getMessage());
            String result = JsonUtils.toJson(responseMessage);
            response.getWriter().write(result);
        } catch (IOException e) {
            log.error("与客户端通讯异常:"+ e.getMessage(), e);
        }
        log.debug(ex.getMessage(), ex);
        return mv;
    }
}
