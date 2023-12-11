package com.example.stock.exception.impl;

import com.example.stock.exception.AbstractionException;
import org.springframework.http.HttpStatus;

public class NullInputException extends AbstractionException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "검색어가 없습니다.";
    }
}
