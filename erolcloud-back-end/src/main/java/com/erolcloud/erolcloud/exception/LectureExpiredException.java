package com.erolcloud.erolcloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LectureExpiredException extends RuntimeException {
    public LectureExpiredException(String message) {
        super(message);
    }
}
