package com.prgms.kdt.danawa.user.repository;

public enum UserSql {
    INSERT_USER("INSERT INTO `user`(user_type, email, created_at, updated_at) VALUES (:user_type, :email, :created_at, :updated_at)"),
    FIND_BY_EMAIL("SELECT * FROM `user` WHERE email = :email"),
    DELETE_ALL("DELETE FROM `user`");

    private final String sql;

    UserSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
