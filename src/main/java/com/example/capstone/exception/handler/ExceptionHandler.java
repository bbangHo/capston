package com.example.capstone.exception.handler;


import com.example.capstone.apiPayload.code.BaseErrorCode;
import com.example.capstone.exception.GeneralException;

public class ExceptionHandler extends GeneralException {

    public ExceptionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}