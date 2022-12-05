package com.prgms.kdt.danawa.user.repository;

import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User insert(User user);

    Optional<User> findByEmail(Email email);

    void deleteAll();
}
