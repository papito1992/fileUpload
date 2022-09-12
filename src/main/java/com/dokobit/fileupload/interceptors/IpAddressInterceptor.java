package com.dokobit.fileupload.interceptors;

import com.dokobit.fileupload.interfaces.FileUploadStatisticsService;
import com.dokobit.fileupload.models.FileUploadStatistics;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.dokobit.fileupload.utils.IpAddressUtils.getRemoteAddr;

@Component
public class IpAddressInterceptor implements HandlerInterceptor {

    private final FileUploadStatisticsService fileUploadStatisticsService;

    public IpAddressInterceptor(FileUploadStatisticsService fileUploadStatisticsService) {
        this.fileUploadStatisticsService = fileUploadStatisticsService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Optional<FileUploadStatistics> fileUploadStatistics = fileUploadStatisticsService.findByIpAddress(getRemoteAddr(request));
        if (fileUploadStatistics.isPresent()) {
            fileUploadStatisticsService.update(fileUploadStatistics.get());
        } else {
            fileUploadStatisticsService.save(new FileUploadStatistics(getRemoteAddr(request)));
        }
    }
}