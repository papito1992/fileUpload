package com.dokobit.fileupload.services;

import com.dokobit.fileupload.interfaces.FileArchivingService;
import com.dokobit.fileupload.models.FileUploadStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.dokobit.fileupload.utils.IpAddressUtils.getRemoteAddr;

@Service
public class FileArchivingServiceImpl implements FileArchivingService {

    @Autowired
    private FileUploadStatisticsServiceImpl fileUploadStatisticsService;

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

    private static void setResponseHeaders(HttpServletResponse response, String fileName) {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
    }

    private static StreamingResponseBody getStreamingResponseBody(ArrayList<MultipartFile> files, HttpServletResponse response, ZipOutputStream zipOut) {
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
        };
    }

    private void upsertUsageStatisticsForIp(HttpServletRequest request) {
        Optional<FileUploadStatistics> fileUploadStatistics = fileUploadStatisticsService.findByIpAddress(getRemoteAddr(request));
        if (fileUploadStatistics.isPresent()) {
            fileUploadStatisticsService.update(fileUploadStatistics.get());
        } else {
            fileUploadStatisticsService.save(new FileUploadStatistics(getRemoteAddr(request)));
        }
    }

    private static String getFileName(ArrayList<MultipartFile> files) {
        return files.stream()
                .map(multipartFile -> multipartFile.getOriginalFilename().replaceAll(" ", "")
                        .replaceAll("\\..*$", ""))
                .collect(Collectors.joining(""));
    }
}
