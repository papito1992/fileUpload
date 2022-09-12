package com.dokobit.fileupload.utils;

import javax.servlet.http.HttpServletRequest;

public final class IpAddressUtils {
    public static String getRemoteAddr(final HttpServletRequest request) {
        final String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
