package com.dokobit.fileupload.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface FileArchivingService {

    ResponseEntity<StreamingResponseBody> archiveToZipFormat(ArrayList<MultipartFile> files, HttpServletRequest request,
                                                             HttpServletResponse response) throws IOException;
    CompletableFuture<ResponseEntity<StreamingResponseBody>> asyncArchiveToZipFormat(ArrayList<MultipartFile> files, HttpServletRequest request,
                                                                                HttpServletResponse response) throws IOException;
}