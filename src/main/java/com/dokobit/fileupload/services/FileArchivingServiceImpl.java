package com.dokobit.fileupload.services;

import com.dokobit.fileupload.interceptors.LoggerInterceptor;
import com.dokobit.fileupload.interfaces.FileArchivingService;
import com.dokobit.fileupload.models.FileUploadStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.dokobit.fileupload.utils.IpAddressUtils.getRemoteAddr;

@Service
public class FileArchivingServiceImpl implements FileArchivingService {

    private static final Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
    private final FileUploadStatisticsServiceImpl fileUploadStatisticsService;

    public FileArchivingServiceImpl(FileUploadStatisticsServiceImpl fileUploadStatisticsService) {
        this.fileUploadStatisticsService = fileUploadStatisticsService;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> archiveToZipFormat(
            ArrayList<MultipartFile> files,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        String fileName = getFileName(files);

        StreamingResponseBody streamResponseBody = getStreamingResponseBody(files, response, zipOut);

        upsertUsageStatisticsForIp(request);

        setResponseHeaders(response, fileName);

        return ResponseEntity.ok(streamResponseBody);
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<StreamingResponseBody>> asyncArchiveToZipFormat(
            ArrayList<MultipartFile> files,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        String fileName = getFileName(files);

        StreamingResponseBody streamResponseBody = getStreamingResponseBody(files, response, zipOut);

        upsertUsageStatisticsForIp(request);

        setResponseHeaders(response, fileName);

        return CompletableFuture.completedFuture(ResponseEntity.ok(streamResponseBody));
    }

    @Async()
    public void setResponseHeaders(HttpServletResponse response, String fileName) {
        log.info("Started Setting response headers");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
        log.info("Finished Setting response headers");
    }

    @Async()
    public StreamingResponseBody getStreamingResponseBody(ArrayList<MultipartFile> files, HttpServletResponse response, ZipOutputStream zipOut) {
        log.info("Started archiving files");
        return outputStream -> {
            ZipEntry zipEntry = null;
            InputStream inputStream;
            for (MultipartFile file : files) {
                inputStream = file.getInputStream();
                zipEntry = new ZipEntry(Objects.requireNonNull(file.getOriginalFilename()));
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[4096];
                int length;
                while ((length = inputStream.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                inputStream.close();
            }
            zipOut.close();
            response.setContentLength((int) (zipEntry != null ? zipEntry.getSize() : 0));
            log.info("Finished archiving files");
        };
    }

    @Async
    public void upsertUsageStatisticsForIp(HttpServletRequest request) {
        log.info("Started Updating usage statistics");
        Optional<FileUploadStatistics> fileUploadStatistics = fileUploadStatisticsService.findByIpAddress(getRemoteAddr(request));
        if (fileUploadStatistics.isPresent()) {
            fileUploadStatisticsService.update(fileUploadStatistics.get());
        } else {
            fileUploadStatisticsService.save(new FileUploadStatistics(getRemoteAddr(request)));
        }
        log.info("Finished Updating usage statistics");
    }

    public String getFileName(ArrayList<MultipartFile> files) {
        return files.stream()
                .map(multipartFile -> multipartFile.getOriginalFilename().replaceAll(" ", "")
                        .replaceAll("\\..*$", ""))
                .collect(Collectors.joining(""));
    }
}
