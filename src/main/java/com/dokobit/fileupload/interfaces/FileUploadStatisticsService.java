package com.dokobit.fileupload.interfaces;

import com.dokobit.fileupload.models.FileUploadStatistics;

import java.util.Optional;

public interface FileUploadStatisticsService {

    FileUploadStatistics save(FileUploadStatistics fileUploadStatistics);
    FileUploadStatistics update(FileUploadStatistics fileUploadStatistics);
    Optional<FileUploadStatistics> findByIpAddress(String ipAddress);
}