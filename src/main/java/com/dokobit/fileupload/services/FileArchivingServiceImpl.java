package com.dokobit.fileupload.services;

import com.dokobit.fileupload.interfaces.FileArchivingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileArchivingServiceImpl implements FileArchivingService {

    @Override
    public ResponseEntity<StreamingResponseBody> archiveToZipFormat(ArrayList<MultipartFile> files, HttpServletResponse response) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        String fileName = getFileName(files);
        StreamingResponseBody streamResponseBody = outputStream -> {
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


        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");

        return ResponseEntity.ok(streamResponseBody);
    }

    private static String getFileName(ArrayList<MultipartFile> files) {
        return files.stream()
                .map(multipartFile -> multipartFile.getOriginalFilename().replaceAll(" ", "")
                        .replaceAll("\\..*$", ""))
                .collect(Collectors.joining(""));
    }
}
