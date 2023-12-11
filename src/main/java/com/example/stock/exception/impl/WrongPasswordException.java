package com.example.stock.exception.impl;

import com.example.stock.exception.AbstractionException;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends AbstractionException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "비밀번호가 일치하지 않습니다.";
    }

}
