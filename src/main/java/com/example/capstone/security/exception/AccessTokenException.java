package com.example.capstone.security.exception;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.BaseErrorCode;
import com.example.capstone.apiPayload.code.ErrorReasonDTO;
import com.example.capstone.exception.GeneralException;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;


public class AccessTokenException extends GeneralException {

    public AccessTokenException (BaseErrorCode errorCode) {
        super(errorCode);
    }


    public void sendResponseError(HttpServletResponse response){


        Gson gson = new Gson();


        try {
            ErrorReasonDTO errorReason = this.getErrorReasonHttpStatus();

            ApiResponse<Object> body = ApiResponse.onFailure(errorReason.getCode(),errorReason.getMessage(),null);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");

            String responseStr = gson.toJson(body);

            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
