package com.ssm.demo.config;


import com.ssm.demo.common.ApiException;
import com.ssm.demo.common.MessageCode;
import com.ssm.demo.common.ResultObject;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/4/28
 */

/**
 * 配置异常过滤器可以通过标签，也可以在web.xml中进行配置
 */
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "exceptionFilter")
public class ExceptionFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        }catch (ApiException e) {
            MessageCode messageCode = e.getCode();
            ResultObject resultObject = new ResultObject(messageCode, null, e.getArgv());
            response.getWriter().println(JSONObject.fromObject(resultObject).toString());
        }catch (ServletException e) {
            logger.error(e.getMessage(), e);

            if(e.getRootCause() instanceof ApiException){
                ApiException apiException = (ApiException) e.getRootCause();
                MessageCode messageCode = apiException.getCode();
                ResultObject resultObject = new ResultObject(messageCode, null, apiException.getArgv());
                response.getWriter().println(JSONObject.fromObject(resultObject).toString());
            }else {
                MessageCode messageCode = MessageCode.CODE_EXCEPTION;
                ResultObject resultObject = new ResultObject(messageCode, null);
                resultObject.setMessage(resultObject.getMessage() + ":" + e.getRootCause().toString());
                response.getWriter().println(JSONObject.fromObject(resultObject).toString());
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            MessageCode messageCode = MessageCode.CODE_EXCEPTION;
            ResultObject resultObject = new ResultObject(messageCode, null);
            response.getWriter().println(JSONObject.fromObject(resultObject).toString());
        }

    }

    @Override
    public void destroy() {

    }
}
