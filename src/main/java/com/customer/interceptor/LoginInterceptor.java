package com.customer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
/**
 * 登录拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean flag =true;
//        LoginUser user=(LoginUser)request.getSession().getAttribute("loginUser");
//        if(null==user){
//            request.getRequestDispatcher("/WEB-INF/page/test.jsp").forward(request, response);
//            flag = false;
//        }else{
//            flag = true;
//        }
//        return true;
		request.getRequestDispatcher("/WEB-INF/page/test.jsp").forward(request, response);
        return false;
    }
}
