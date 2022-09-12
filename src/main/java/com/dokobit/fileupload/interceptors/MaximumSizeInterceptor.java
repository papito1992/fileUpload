package com.dokobit.fileupload.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MaximumSizeInterceptor implements HandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(MaximumSizeInterceptor.class);
//
//    @Override
//    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
//        log.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI() + getParameters(request));
//        return true;
//    }
//
//    @Override
//    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
//        log.info("[postHandle][" + request + "]");
//    }
//
//    @Override
//    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
//        Throwable SizeLimitExceededException = new Throwable();
//        if (ex != null && Objects.equals(ex.getCause(), SizeLimitExceededException))
//            System.out.println("bambino");
////        log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
//    }
//    private String getParameters(final HttpServletRequest request) {
//        final StringBuffer posted = new StringBuffer();
//        final Enumeration<?> e = request.getParameterNames();
//        if (e != null)
//            posted.append("?");
//        while (e != null && e.hasMoreElements()) {
//            if (posted.length() > 1)
//                posted.append("&");
//            final String curr = (String) e.nextElement();
//            posted.append(curr)
//                    .append("=");
//            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
//                posted.append("*****");
//            } else {
//                posted.append(request.getParameter(curr));
//            }
//        }
//
//        final String ip = request.getHeader("X-FORWARDED-FOR");
//        final String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
//        if (!Strings.isNullOrEmpty(ipAddr))
//            posted.append("&_psip=" + ipAddr);
//        return posted.toString();
//    }
}
