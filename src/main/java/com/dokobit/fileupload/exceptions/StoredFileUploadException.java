package com.dokobit.fileupload.exceptions;

public class StoredFileUploadException extends RuntimeException {

    public StoredFileUploadException(String message) {
        super(message);
    }

    public StoredFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
