package com.prgms.kdt.danawa.user.domain;

import com.prgms.kdt.danawa.generic.domain.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private final long userId;

    @NotNull
    private UserType userType;

    @NotNull
    private final Email email;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    public User(long userId, UserType userType, Email email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userType = userType;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(UserType userType, Email email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(0, userType, email, createdAt, updatedAt);
    }

    public long getUserId() {
        return userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && userType == user.userType && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userType, createdAt, updatedAt);
    }
}
