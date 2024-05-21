package com.example.capstone.security.filter;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.security.exception.TokenException;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String defaultFilterProcessesUrl){
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("LoginFilter---------------------");

        if (request.getMethod().equalsIgnoreCase("GET")) {
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }


        Map<String, String> jsonData;
        try {
            jsonData = parseRequestJSON(request);
        } catch (TokenException tokenException) {
            tokenException.sendResponseError(response);
            return null;
        }


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                jsonData.get("loginId"),
                jsonData.get("password"));

        try {
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (BadCredentialsException badCredentialsException) {
            new TokenException(ErrorStatus.ID_AND_PASSWORD_NOT_MATCH).sendResponseError(response);
        }
        return null;
    }


    private Map<String, String> parseRequestJSON(HttpServletRequest request) throws TokenException{
        Map<String, String> loginData;

        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            loginData = gson.fromJson(reader, Map.class);
        } catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException();
        }
        try{
            if(!loginData.keySet().containsAll(Set.of("loginId","password"))){
                log.info("test................");
                throw new TokenException(ErrorStatus.MALFORMED_LOGIN_DATA);
            }
        } catch (NullPointerException nullPointerException){
            throw new TokenException(ErrorStatus.LOGIN_DATA_NOT_ACCEPTED);
        }

            return loginData;

    }
}
