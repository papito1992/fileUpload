package com.dokobit.fileupload.exceptions;

public class StoredFileNotFoundException extends StoredFileUploadException {
    public StoredFileNotFoundException(String message) {
        super(message);
    }

    public StoredFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
