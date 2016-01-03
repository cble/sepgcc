package com.sepgcc.site.filter;

import com.sepgcc.site.constants.SecurityConstants;
import com.sepgcc.site.dto.User;
import com.sepgcc.site.exceptions.AuthenticateFailException;
import com.sepgcc.site.utils.SecurityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserAuthenticateFilter implements Filter {

    private static final Logger log = Logger.getLogger(UserAuthenticateFilter.class);

    private static final String[] staticPaths = {"css", "js", "favicon.ico"};
    private static final String[] noLoginPaths = {"login", "logout", "captcha"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);

        Object object = session.getAttribute(SecurityConstants.SESSION_TOKEN);
        String token = object == null ? null : (String) object;
        User user = null;
        try {
            if (token == null) {
                if (isNeedLogin(httpRequest)) {
                    throw new AuthenticateFailException(1);
                }
            } else {
                user = SecurityUtils.parseToken(token);
                if (user == null) {
                    throw new AuthenticateFailException(1);
                }
                if (isAdminRequest(httpRequest) && !user.isAdmin()) {
                    throw new AuthenticateFailException(0);
                }
            }
            invokeNextFilter(filterChain, servletRequest, servletResponse, user);
        } catch (AuthenticateFailException e) {
            if (e.redirectToLogin()) {
                httpRequest.getSession().removeAttribute(SecurityConstants.SESSION_TOKEN);
                if (isAjaxRequest(httpRequest)) {
                    httpResponse.setCharacterEncoding("UTF-8");
                    httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");
                } else {
                    httpResponse.sendRedirect("/login?redirect=" + httpRequest.getRequestURL());
                }
            } else {
                httpResponse.sendError(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    private void invokeNextFilter(FilterChain filterChain, ServletRequest servletRequest, ServletResponse servletResponse, User user) throws IOException, ServletException {
        try {
            servletRequest.setAttribute(SecurityConstants.REQUEST_USER, user);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            servletRequest.removeAttribute(SecurityConstants.REQUEST_USER);
        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/ajax");
    }

    private static boolean isAdminRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/admin/");
    }

    private static boolean isNeedLogin(HttpServletRequest request) {
        String url = request.getRequestURI();
        for (String s : staticPaths) {
            if (url.contains(s)) {
                return false;
            }
        }
        for (String s : noLoginPaths) {
            if (url.contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
            /*如果需要注入，请取消注释*/
//           ServletContext servletContext = filterConfig.getServletContext();
//            WebApplicationContext applicationContext = (WebApplicationContext) servletContext.
//                    getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//            if (null == topConstantsImpl) {
//                //从Spring AC 中加载app configuration对象
//                topConstantsImpl = applicationContext.getBean(TopConstantsImpl.class);
//        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

