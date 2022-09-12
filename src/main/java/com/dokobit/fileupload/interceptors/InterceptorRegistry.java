package com.dokobit.fileupload.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorRegistry implements WebMvcConfigurer {

    private final IpAddressInterceptor ipAddressInterceptor;
    private final LoggerInterceptor loggerInterceptor;

    public InterceptorRegistry(IpAddressInterceptor ipAddressInterceptor, LoggerInterceptor loggerInterceptor) {
        this.ipAddressInterceptor = ipAddressInterceptor;
        this.loggerInterceptor = loggerInterceptor;
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(ipAddressInterceptor);
    }
}