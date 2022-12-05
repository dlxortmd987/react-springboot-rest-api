package com.prgms.kdt.danawa.user;

import com.prgms.kdt.danawa.exception.DuplicateUserException;
import com.prgms.kdt.danawa.exception.EmptyResultException;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.user.dto.UserLoginRequest;
import com.prgms.kdt.danawa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void login(UserLoginRequest userLoginRequest) {
        userRepository.findByEmail(new Email(userLoginRequest.getEmail()))
                .ifPresent(user -> {
                    throw new DuplicateUserException(String.format("Duplicate User. Please Check Email. [email]: %s", user.getEmail()));
                });

        userRepository.insert(userLoginRequest.toUser());
    }
}
