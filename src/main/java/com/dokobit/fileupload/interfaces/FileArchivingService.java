package com.dokobit.fileupload.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public interface FileArchivingService {

    ResponseEntity<StreamingResponseBody> archiveToZipFormat(ArrayList<MultipartFile> files, HttpServletRequest request,
                                                             HttpServletResponse response) throws IOException;
}