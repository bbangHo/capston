package com.example.capstone.member.util;


import com.example.capstone.member.common.MemberValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.example.capstone.member.common.MemberValidator.*;

public class MemberValidatorTest {

    @Test
    @DisplayName("로그인ID 체크 테스트")
    void testLoginId(){
        String[] failCase = {"test", "328543", "3한글543", "t1"};

        String successCase ="test543";

        for (String s : failCase) {
            Assertions.assertThat(validateLoginId(s)).isFalse();
        }

        Assertions.assertThat(validateLoginId(successCase)).isTrue();

    }

    @Test
    @DisplayName("비밀번호 체크 테스트")
    void testPassword(){

        String[] failCase = {"test", "328543", "3한글543", "t1","sjse98ㄱ","sghwu4egow9he4gw4g"};

        String successCase ="test5d43";

        for (String s : failCase) {
            Assertions.assertThat(validatePassword(s)).isFalse();
        }

        Assertions.assertThat(validatePassword(successCase)).isTrue();
    }

    @Test
    @DisplayName("핸드폰 번호 체크 테스트")
    void testPhone(){
        String[] failCase = {"test", "3한글543", "t1","sjse98ㄱ","sghwu4egow9he4gw4g"};

        String successCase ="34098093498";

        for (String s : failCase) {
            Assertions.assertThat(validatePhone(s)).isFalse();
        }

        Assertions.assertThat(validatePhone(successCase)).isTrue();
    }

    @Test
    @DisplayName("닉네임 체크 테스트")
    void testNickName(){
        String[] failCase = {"tst", "3한글54tjoie4jgojrgjw43", "ㅇㄹㅎㄱ호4","sjse98ㄱ","sghwu4egow9he4gw4g"};

        String successCase ="3한글543";

        for (String s : failCase) {
            Assertions.assertThat(validateNickName(s)).isFalse();
        }

        Assertions.assertThat(validateNickName(successCase)).isTrue();
    }

    @Test
    @DisplayName("이름 체크 테스트")
    void testName(){
        String[] failCase = {"3한글543","tst", "3한글54tjoie4jgojrgjw43", "ㅇㄹㅎㄱ호4","sjse98ㄱ","sghwu4egow9he4gw4g"};

        String successCase ="한글";

        for (String s : failCase) {
            Assertions.assertThat(validateName(s)).isFalse();
        }

        Assertions.assertThat(validateName(successCase)).isTrue();
    }





}
