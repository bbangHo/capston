package com.example.capstone.secret;

import com.example.capstone.security.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@SpringBootTest
@Slf4j
public class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate(){
        Map<String, Object> claimMap =Map.of("test","testObject");
        String jwtStr = jwtUtil.generateToken(claimMap,1);
        log.info(jwtStr);
    }

    @Test
    public void testValidate() {

        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0ZXN0IjoidGVzdE9iamVjdCIsImlhdCI6MTcxNTkyOTY4MiwiZXhwIjoxNzE1OTI5NzQyfQ.1hAPm2XKlipZQNVL9EN01A_jMRmfVvSHdB1YJDWEs7Q";

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info(claim.toString());
    }

    @Test
    public void testAll() {

       String jwtStr = jwtUtil.generateToken(Map.of("id","testId","email","testEmail"),1);

        log.info(jwtStr);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info("ID: " + claim.get("id"));
        log.info("email: " + claim.get("email"));

    }
}
