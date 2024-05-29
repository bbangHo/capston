package com.example.capstone.member.common;

public class MemberValidator {
    public static Boolean validateLoginId(String loginId) {
        String regex = "^(?=.*[A-Za-z])([A-Za-z\\d]{4,14})$";

        return loginId.matches(regex);
    }

    public static Boolean validatePassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,14}$";

        return password.matches(regex);
    }

    public static Boolean validatePhone(String phone) {

        String regex = "^(?!-)(?!.*--)(?=.{1,13}$)(\\d{1,4}-){2}\\d{1,4}$";

        return phone.matches(regex);
    }

    public static Boolean validateNickName(String nickName) {

        String regex = "^(?=.*[A-Za-z가-힣])(?=.*\\d*[A-Za-z가-힣])[A-Za-z가-힣\\d]{4,14}$";

        return nickName.matches(regex);
    }

    public static Boolean validateName(String name) {
        String regex = "^[가-힣]{2,17}$";

        return name.matches(regex);
    }

}
