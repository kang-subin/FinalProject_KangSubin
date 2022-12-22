package com.example.personalproject.service;

import com.example.personalproject.JwtTokenUtil.JwtTokenUtil;
import com.example.personalproject.domain.dto.UserDto;
import com.example.personalproject.domain.entity.User;
import com.example.personalproject.domain.request.UserJoinRequest;
import com.example.personalproject.domain.request.UserLoginRequest;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("hello")
    private String secretkey;

    private Long expireTime = 10000 * 60 * 60L;

    public UserDto join(UserJoinRequest userJoinRequest) {
        Optional<User> users = userRepository.findByUserName(userJoinRequest.getUserName());
        if (users.isPresent())  throw new UserException(ErrorCode.DUPLICATE_USER_NAME,userJoinRequest.getUserName()+"은 이미 있습니다.");

        User user = User.builder()
                .userName(userJoinRequest.getUserName())
                .password(encoder.encode(userJoinRequest.getPassword()))
                .build();

        User saved = userRepository.save(user);

        return UserDto.builder()
                .userId(saved.getId())
                .userName(saved.getUserName())
                .password(saved.getPassword())
                .build();
    }

    public String login(UserLoginRequest userLoginRequest) {

        Optional<User> users = userRepository.findByUserName(userLoginRequest.getUserName());

        if (users.isEmpty()) throw new UserException(ErrorCode.USERNAME_NOT_FOUND,"Not founded");

        if(!encoder.matches(userLoginRequest.getPassword(), users.get().getPassword())) throw new UserException(ErrorCode.INVALID_PASSWORD,"패스워드가 잘못되었습니다.");

        String token = JwtTokenUtil.createToken(userLoginRequest.getUserName(),secretkey, expireTime);
        return token;
    }
}
