package com.ssm.demo.config;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "accessControlFilter")
public class AccessControlFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(AccessControlFilter.class);
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        
		HttpServletRequest hRequest = (HttpServletRequest)request;
        
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", ((HttpServletRequest)request).getHeader("Origin"));
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "sign,token,Content-Type,cache-control,X-Requested-With");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Credentials", "true");
		if (request instanceof HttpServletRequest) {
			HttpServletRequest request1 = (HttpServletRequest)request;
			logger.debug("token:" + request1.getHeader("token"));
			logger.debug("requestURI:" + request1.getRequestURI());
			logger.debug("contentType:" + request1.getContentType());
			logger.debug("parameterMap:" + JSONObject.fromObject(request1.getParameterMap()).toString());
			if (request1.getContentType() != null && request1.getContentType().startsWith("application/json")) {
				BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request1);
				logger.debug("body:" + requestWrapper.getBody().replace("\\n",""));
				chain.doFilter(requestWrapper, response);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) {
		
	}
}
