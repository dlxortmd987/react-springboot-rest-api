package com.prgms.kdt.danawa.user.repository;

import com.prgms.kdt.danawa.exception.FailedUpdateException;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.user.domain.User;
import com.prgms.kdt.danawa.user.domain.UserType;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static com.prgms.kdt.danawa.generic.utils.JdbcUtils.toLocalDateTime;
import static com.prgms.kdt.danawa.user.repository.UserSql.*;

@Service
public class UserJdbcRepository implements UserRepository{

    private static final Logger log = LoggerFactory.getLogger(UserJdbcRepository.class);

    private static final int UPDATED_SIZE = 1;
    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, i) -> {
        long userId = resultSet.getLong("user_id");
        UserType userType = UserType.valueOf(resultSet.getString("user_type"));
        Email email = new Email(resultSet.getString("email"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new User(
                userId,
                userType,
                email,
                createdAt,
                updatedAt
        );
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public User insert(@Valid User user) {
        int update = jdbcTemplate.update(INSERT_USER.getSql(), toUserParamMap(user));
        if (update != UPDATED_SIZE) {
            throw new FailedUpdateException(UPDATED_SIZE, update);
        }
        Long generatedId = Objects.requireNonNull(jdbcTemplate.queryForObject("SELECT last_insert_id()", Collections.emptyMap(), Long.class));
        return new User(generatedId, user.getUserType(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(FIND_BY_EMAIL.getSql(),
                            Collections.singletonMap("email", email.getValue()),
                            USER_ROW_MAPPER)
            );
        } catch (EmptyResultDataAccessException exception) {
            log.info("Not found User. [user email]: " + email);
            return Optional.empty();
        }
    }

    private MapSqlParameterSource toUserParamMap(User user) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("user_id", user.getUserId());
        source.addValue("user_type", user.getUserType().name());
        source.addValue("email", user.getEmail().getValue());
        source.addValue("created_at", user.getCreatedAt());
        source.addValue("updated_at", user.getUpdatedAt());
        return source;
    }
}
