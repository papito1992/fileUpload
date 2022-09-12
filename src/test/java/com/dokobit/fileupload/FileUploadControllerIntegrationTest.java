package com.dokobit.fileupload;

import com.dokobit.fileupload.models.FileUploadStatistics;
import com.dokobit.fileupload.repository.FileUploadStatisticsRepository;
import com.dokobit.fileupload.services.FileUploadStatisticsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FileUploadControllerIntegrationTest {

    @Autowired
    private FileUploadStatisticsRepository fileUploadStatisticsRepository;

    @Autowired
    private FileUploadStatisticsServiceImpl fileUploadStatisticsService;

    @Test
    void shouldCreateStatisticsEntityInDatabaseWithIncomingIp() {

        boolean isRepoEmptyPreSave = fileUploadStatisticsRepository.findAll().isEmpty();
        FileUploadStatistics fileUploadStatistics = fileUploadStatisticsService.save(new FileUploadStatistics("0.0.0.1"));
        boolean isRepoEmptyPostSave = fileUploadStatisticsRepository.findAll().isEmpty();
        Assertions.assertTrue(isRepoEmptyPreSave);
        Assertions.assertFalse(isRepoEmptyPostSave);
        Assertions.assertEquals("0.0.0.1", fileUploadStatistics.getIpAddress());
        Assertions.assertEquals(1, fileUploadStatistics.getTimesAccessed());
    }

    @Test
    void shouldUpdateStatisticsEntityInDatabaseWithIncomingIpAndIncrementTimesAccessed() {

        FileUploadStatistics fileUploadStatistics = fileUploadStatisticsService.save(new FileUploadStatistics("0.0.0.1"));
        int repoSizePreUpdate = fileUploadStatisticsRepository.findAll().size();
        FileUploadStatistics fileUploadStatisticsUpdated = fileUploadStatisticsService.update(fileUploadStatistics);
        int repoSizePostUpdate = fileUploadStatisticsRepository.findAll().size();
        Assertions.assertEquals(1, repoSizePreUpdate);
        Assertions.assertEquals(1, repoSizePostUpdate);
        Assertions.assertEquals("0.0.0.1", fileUploadStatistics.getIpAddress());
        Assertions.assertEquals(2, fileUploadStatisticsUpdated.getTimesAccessed());

    }
}
