package com.sepgcc.site.filter;

import com.sepgcc.constants.SessionConstants;
import com.sepgcc.site.dto.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserInfoFilter implements Filter {

    private static final Logger log = Logger.getLogger(UserInfoFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("OncePerRequestFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);

        StringBuffer url = httpRequest.getRequestURL();
        String[] strs = {"login", "css", "js"};
        for (String str : strs) {
            if (url.indexOf(str) >= 0) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        Object object = session.getAttribute(SessionConstants.SESSION_USER);
        User user = object == null ? null : (User) object;
        if (user == null) {
            boolean isAjaxRequest = isAjaxRequest(httpRequest);
            if (isAjaxRequest) {
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),
                        "您已经太长时间没有操作,请刷新页面");
            }
            httpResponse.sendRedirect("/login?redirect="+url);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
        return;
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

