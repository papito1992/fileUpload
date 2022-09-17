package com.dokobit.fileupload.controller;

import com.dokobit.fileupload.interfaces.FileArchivingService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@RestController
public class FileUploadController {

    private final FileArchivingService fileArchivingService;

    public FileUploadController(FileArchivingService fileArchivingService) {
        this.fileArchivingService = fileArchivingService;
    }

    @PostMapping(value = "/upload", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> handleFileUpload(
            @RequestParam("files") ArrayList<MultipartFile> files, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        return fileArchivingService.archiveToZipFormat(files, request, response);
    }

    @PostMapping(value = "/async/upload", produces = "application/zip")
    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<StreamingResponseBody>> handleFileUploadAsynch(
            @RequestParam("files") ArrayList<MultipartFile> files, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        return fileArchivingService.asyncArchiveToZipFormat(files, request, response);
    }
}