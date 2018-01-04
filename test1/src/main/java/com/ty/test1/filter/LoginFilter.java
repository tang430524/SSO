package com.ty.test1.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import com.ty.test1.util.HttpRequest;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*",filterName = "loginFilter")
public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    		System.out.println("filter。。。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String servletPath = httpServletRequest.getServletPath();
        HttpSession session=httpServletRequest.getSession();
        servletPath="http://127.0.0.1:9010"+servletPath;

        String token = httpServletRequest.getParameter("token");
        if( StringUtils.isNotBlank(token) ) { 
          //发送 GET 请求 验证token
            String s = null;
			try {
				s = HttpRequest
						.sendGet("http://127.0.0.1:8010/checkToken.action", "reurl=" + servletPath + "&&token=" + token)
						.get("uname").get(0).toString();
				System.out.println(s);
	            session.setAttribute("uname", s);//添加登陆信息
	            if(s!=null&&s!="false"&&s!=""){//返回为空时表示当前登陆已经失效
	            	filterChain.doFilter(httpServletRequest, httpServletResponse); 
	            }
			} catch (NullPointerException e) {//验证失败返回登陆页面
		        httpServletResponse.sendRedirect("http://127.0.0.1:8010/login.html?reurl="+servletPath);
			}
            
           // httpServletResponse.sendRedirect("http://127.0.0.1:8010/checkToken.action?reurl="+servletPath+"&&token="+token);
        	//filterChain.doFilter(httpServletRequest, httpServletResponse); 
            return;
        }
        httpServletResponse.sendRedirect("http://127.0.0.1:8010/login.html?reurl="+servletPath);
        return;
    }

    @Override
    public void destroy() {

    }
}