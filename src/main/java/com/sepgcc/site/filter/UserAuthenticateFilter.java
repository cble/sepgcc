package com.sepgcc.site.filter;

import com.sepgcc.site.constants.SecurityConstants;
import com.sepgcc.site.dto.User;
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
    private static final String[] noLoginPaths = {"login", "logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);

        StringBuffer url = httpRequest.getRequestURL();
        for (String s : staticPaths) {
            if (url.indexOf(s) >= 0) {
                invokeNextFilter(filterChain, servletRequest, servletResponse, null);
                return;
            }
        }

        Object object = session.getAttribute(SecurityConstants.SESSION_TOKEN);
        String token = object == null ? null : (String) object;
        if (token != null) {
            User user = SecurityUtils.parseToken(token);
            if (user != null) {
                invokeNextFilter(filterChain, servletRequest, servletResponse, user);
                return;
            }
        }

        for (String s : noLoginPaths) {
            if (url.indexOf(s) >= 0) {
                invokeNextFilter(filterChain, servletRequest, servletResponse, null);
                return;
            }
        }

        httpRequest.getSession().removeAttribute(SecurityConstants.SESSION_TOKEN);
        boolean isAjaxRequest = isAjaxRequest(httpRequest);
        if (isAjaxRequest) {
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),
                    "您已经太长时间没有操作,请刷新页面");
        }
        httpResponse.sendRedirect("/login?redirect="+url);
    }

    private void invokeNextFilter(FilterChain filterChain, ServletRequest servletRequest, ServletResponse servletResponse, User user) throws IOException, ServletException {
        try {
            servletRequest.setAttribute(SecurityConstants.REQUEST_USER, user);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            servletRequest.removeAttribute(SecurityConstants.REQUEST_USER);
        }
    }

    /**
     * 判断是否为Ajax请求
     *
     * @param request HttpServletRequest
     * @return 是true, 否false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/ajax");
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

