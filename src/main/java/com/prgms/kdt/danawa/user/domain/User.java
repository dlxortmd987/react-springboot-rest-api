package com.prgms.kdt.danawa.user.domain;

import java.time.LocalDateTime;

public class User {
    private final long userId;
    private UserType userType;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(long userId, UserType userType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userType = userType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
