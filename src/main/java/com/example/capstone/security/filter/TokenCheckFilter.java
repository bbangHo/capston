package com.example.capstone.security.filter;

import com.example.capstone.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        String path = request.getRequestURI();

        if(!path.endsWith("/check")) {
            filterChain.doFilter(request,response);
            return;
        }

        log.info("Token Check Filter............................");

        try{
            validateAccessToken(request);
            filterChain.doFilter(request,response);
        }catch (Exception e ){//원래는 (AccessTokenException accessTokenException
            //            accessTokenException.sendResponseError(response);
        }
    }

    /***
     *
     * throws AccessTokenException 대신할 만한 조치가 필요
     */
    private Map<String, Object> validateAccessToken(HttpServletRequest request) {
        String headerStr = request.getHeader("Authorization");

        if(headerStr == null || headerStr.length() < 8){
            /**
             * throw new AccessTokenException(A~~~~) 토큰 없다는 에러
             */
        }

        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        //대소문자를 구분하지 않고 비교
        if(!tokenType.equalsIgnoreCase("Bearer")){
            log.error("BadType error..................");

            /**
             * badtype에러
             */
        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        }catch(MalformedJwtException malformedJwtException){
            log.error("MalformedJwtException.................");
            //throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
            throw new RuntimeException();

        }catch(SignatureException signatureException){
            log.error("SignatureException.................");
            //throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
            throw new RuntimeException();

        }catch(ExpiredJwtException expiredJwtException){
            log.error("ExpiredJwtException.................");
            //throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
            throw new RuntimeException();
        }


    }


}
