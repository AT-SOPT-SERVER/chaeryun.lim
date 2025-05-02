package org.sopt.user.service;

import org.sopt.user.domain.User;
import org.sopt.user.dto.CreateUserReq;
import org.sopt.user.dto.CreateUserRes;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public CreateUserRes createUser(final CreateUserReq createUserReq){

        User save = userRepository.save(User.from(createUserReq));
        return CreateUserRes.fromUser(save);
    }
}
