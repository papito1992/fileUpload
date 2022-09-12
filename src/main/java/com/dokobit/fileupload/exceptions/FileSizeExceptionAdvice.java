package com.dokobit.fileupload.exceptions;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class FileSizeExceptionAdvice extends ResponseEntityExceptionHandler {

//    @Value("${spring.servlet.multipart.max-file-size}")
//    private String maxFileSize = "1024";
//
//    @ExceptionHandler(MultipartException.class)
//    public ResponseEntity<Object> handleMaxSizeException(
//            MultipartException ex, WebRequest request) {
//
//        Map<String, String> bodyOfResponse = new HashMap<>();
//        bodyOfResponse.put(ex.getCause().getMessage(), "File total size limit is : " + maxFileSize);
//        return handleExceptionInternal(ex, bodyOfResponse,
//                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
}