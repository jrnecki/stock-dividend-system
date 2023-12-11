package com.example.stock.exception.impl;

import com.example.stock.exception.AbstractionException;
import org.springframework.http.HttpStatus;

public class AlreadyExistTickerException extends AbstractionException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 존재하는 ticker입니다.";
    }

}
