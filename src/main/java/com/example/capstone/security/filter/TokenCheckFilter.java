package com.example.capstone.security.filter;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.security.exception.TokenException;
import com.example.capstone.security.service.MemberDetailsService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter  {
    private final JWTUtil jwtUtil;
    private final MemberDetailsService memberDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        String path = request.getRequestURI();

        if(!path.endsWith("/auth")) {
            filterChain.doFilter(request,response);
            return;
        }

        log.info("Token Check Filter............................");

        try{
            Map<String, Object> payload = validateAccessToken(request);
            String loginId = (String)payload.get("loginId");
            UserDetails userDetails = memberDetailsService.loadUserByUsername(loginId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request,response);
        }catch (TokenException TokenException){
            TokenException.sendResponseError(response);
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws TokenException{
        String headerStr = request.getHeader("Authorization");

        if(headerStr == null || headerStr.length() < 8){
            throw new TokenException(ErrorStatus.ACCESS_TOKEN_NOT_ACCEPTED);
        }

        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        //대소문자를 구분하지 않고 비교
        if(!tokenType.equalsIgnoreCase("Bearer")){
            log.error("BadType error..................");
            throw new TokenException(ErrorStatus.ACCESS_TOKEN_BADTYPE);

        }

        try {
            return jwtUtil.validateToken(tokenStr);
        }catch(MalformedJwtException malformedJwtException){
            log.error("MalformedJwtException.................");
            throw new TokenException(ErrorStatus.MALFORMED_ACCESS_TOKEN);

        }catch(SignatureException signatureException){
            log.error("SignatureException.................");
            throw new TokenException(ErrorStatus.BAD_SIGNED_ACCESS_TOKEN);

        }catch(ExpiredJwtException expiredJwtException){
            log.error("ExpiredJwtException.................");
            throw new TokenException(ErrorStatus.EXPIRED_ACCESS_TOKEN);
        }
    }


}
