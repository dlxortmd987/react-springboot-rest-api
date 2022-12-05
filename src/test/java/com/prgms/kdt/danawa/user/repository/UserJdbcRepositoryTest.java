package com.prgms.kdt.danawa.user.repository;

import com.prgms.kdt.danawa.JdbcTestConfig;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.user.domain.User;
import com.prgms.kdt.danawa.user.domain.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Import(JdbcTestConfig.class)
@Sql(scripts = "classpath:schema.sql")
class UserJdbcRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @DisplayName("유저를 등록할 수 있다.")
    @Test
    void insertTest() {
        // given
        User user = new User(UserType.CUSTOMER, new Email("dlxortmd987@gmail.com"), LocalDateTime.now(), LocalDateTime.now());

        // when
        User insertedUser = userRepository.insert(user);
        Optional<User> maybeUser = userRepository.findByEmail(insertedUser.getEmail());

        // then
        Assertions.assertThat(maybeUser.get())
                .isEqualTo(insertedUser);
    }

    @DisplayName("유저 이메일로 유저를 조회할 수 있다.")
    @Test
    void findByEmailTest() {
        // given
        User user = new User(UserType.CUSTOMER, new Email("dlxortmd987@gmail.com"), LocalDateTime.now(), LocalDateTime.now());
        User insertedUser = userRepository.insert(user);

        // when
        Optional<User> maybeUser = userRepository.findByEmail(insertedUser.getEmail());

        // then
        Assertions.assertThat(maybeUser.get())
                .isEqualTo(insertedUser);
    }
}