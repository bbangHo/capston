package com.example.capstone.security.handler;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.security.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException{
        log.info("Login Success Handler...................");



        log.info(authentication.toString());

        Map<String, Object> claim = Map.of("loginId",authentication.getName());


        //access Token의 유효기간을 하루로 설정
        String accessToken = jwtUtil.generateToken(claim,1);
        //refresh Token의 유효기간 30일
        String refreshToken = jwtUtil.generateToken(claim,30);

        Map<String, String> tokens = Map.of("accessToken", accessToken,"refreshToken", refreshToken);

        Gson gson = new Gson();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        String responseStr = gson.toJson(ApiResponse.onSuccess(tokens));

        response.getWriter().println(responseStr);
    }
}
