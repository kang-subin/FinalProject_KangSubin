package com.example.personalproject.service;

import com.example.personalproject.domain.User;
import com.example.personalproject.domain.UserRequest;
import com.example.personalproject.domain.UserResponse;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse join(UserRequest userRequest){
        Optional<User> users = userRepository.findByUserName(userRequest.getUserName());
    if(users.isPresent()) throw new UserException(ErrorCode.DUPLICATE_USER_NAME);

    User user = User.builder()
            .userName(userRequest.getUserName())
            .password(userRequest.getPassword())
            .build();

    User saved = userRepository.save(user);

    return UserResponse.builder()
            .userId(saved.getId())
            .userName(saved.getUserName())
            .build();
    }
}
