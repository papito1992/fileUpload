package com.dokobit.fileupload.services;

import com.dokobit.fileupload.interfaces.FileUploadStatisticsService;
import com.dokobit.fileupload.models.FileUploadStatistics;
import com.dokobit.fileupload.repository.FileUploadStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileUploadStatisticsServiceImpl implements FileUploadStatisticsService {

    private final FileUploadStatisticsRepository fileUploadStatisticsRepository;

    public FileUploadStatisticsServiceImpl(FileUploadStatisticsRepository fileUploadStatisticsRepository) {
        this.fileUploadStatisticsRepository = fileUploadStatisticsRepository;
    }

    @Override
    public FileUploadStatistics save(FileUploadStatistics fileUploadStatistics) {
        return fileUploadStatisticsRepository.save(fileUploadStatistics);
    }

    @Override
    public FileUploadStatistics update(FileUploadStatistics fileUploadStatistics) {
        fileUploadStatistics.setTimesAccessed(fileUploadStatistics.getTimesAccessed() + 1);
        return fileUploadStatisticsRepository.save(fileUploadStatistics);
    }

    @Override
    public Optional<FileUploadStatistics> findByIpAddress(String ipAddress) {
        return fileUploadStatisticsRepository.findDistinctByIpAddress(ipAddress);
    }
}
