package com.example.stock.exception.impl;

import com.example.stock.exception.AbstractionException;
import org.springframework.http.HttpStatus;

public class NoCompanyTickerException extends AbstractionException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지않는 회사의 ticker입니다.";
    }
}
