package com.urlshortner.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Url")
public class UrlException extends RuntimeException{

    public UrlException(String msg) {
        super(msg);
    }
}
