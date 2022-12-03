package com.prgms.kdt.danawa.generic.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JdbcUtils {

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }
}
