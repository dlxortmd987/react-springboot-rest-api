package com.prgms.kdt.danawa.user.dto;

import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.user.domain.User;
import com.prgms.kdt.danawa.user.domain.UserType;

import java.time.LocalDateTime;

public class UserLoginRequest {
    private String userType;
    private String email;

    public UserLoginRequest() {

    }

    public UserLoginRequest(String userType, String email) {
        this.userType = userType;
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public User toUser() {
        return new User(
                UserType.valueOf(this.userType),
                new Email(this.email),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}