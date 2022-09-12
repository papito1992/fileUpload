package com.dokobit.fileupload.repository;

import com.dokobit.fileupload.models.FileUploadStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileUploadStatisticsRepository extends JpaRepository<FileUploadStatistics, Long> {
    Optional<FileUploadStatistics> findDistinctByIpAddress(String ipAddress);
}