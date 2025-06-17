package org.sopt.user.service;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.user.domain.User;
import org.sopt.user.dto.CreateUserReq;
import org.sopt.user.dto.CreateUserRes;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public CreateUserRes createUser(final CreateUserReq createUserReq){

        User save = userRepository.save(User.from(createUserReq));
        return CreateUserRes.fromUser(save);
    }

    // 엔티티 찾기
    public User getUserById(final Long id){
        return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
