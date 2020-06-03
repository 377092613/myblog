package com.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlogNotFundException extends RuntimeException{
    public BlogNotFundException() {
    }

    public BlogNotFundException(String message) {
        super(message);
    }

    public BlogNotFundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogNotFundException(Throwable cause) {
        super(cause);
    }

    public BlogNotFundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
