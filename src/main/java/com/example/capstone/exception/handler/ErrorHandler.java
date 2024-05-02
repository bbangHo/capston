package com.example.capstone.exception.handler;


import com.example.capstone.apiPayload.code.BaseErrorCode;
import com.example.capstone.exception.GeneralException;

public class ErrorHandler extends GeneralException {

    public ErrorHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}