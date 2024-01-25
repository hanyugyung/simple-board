package org.example.han.domain.user;

import lombok.RequiredArgsConstructor;
import org.example.han.common.auth.JwtConfigProperty;
import org.example.han.common.auth.JwtGenerator;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.infrastructure.UserRepository;
import org.example.han.interfaces.CommonResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtConfigProperty jwtConfigProperty;

    private void checkAvailableLoginId(String loginId) {
        userRepository.findByLoginId(loginId)
                .ifPresent(it -> {
                    throw new InvalidParameterException(CommonResponse.CustomError.USER_LOGIN_ID_ALREADY_EXISTED);
                });
    }

    private User validateUserAndGet(String loginId, String password) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.USER_FAIL_LOGIN));

        if (!passwordEncoder.matches(password, user.getEncodedPassword())) {
            throw new InvalidParameterException(CommonResponse.CustomError.USER_FAIL_LOGIN);
        }

        return user;
    }

    @Override
    @Transactional
    public long signUp(UserDomainDto.UserSignUpCommand command) {
        checkAvailableLoginId(command.getLoginId());
        return userRepository.save(command.toEntity(passwordEncoder)).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public String login(UserDomainDto.UserLoginCommand command) {
        User user = validateUserAndGet(command.getUserId(), command.getPassword());
        return JwtGenerator.generateToken(user.getId(), user.getLoginId(), user.getName(), jwtConfigProperty);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDomainDto.GetUserInfo getUser(Long requesterId) {
        return UserDomainDto.GetUserInfo.of(
                userRepository.findById(requesterId)
                        .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.NOT_FOUND_USER))
        );
    }

    @Override
    @Transactional
    public Long updateUser(UserDomainDto.UpdateUserCommand command, Long requesterId) {
        User user = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalStateException("논리적으로 절대 오면 안되는 곳...그치만 올 수도 있는 곳"));
        user.updateUser(command.getUserName(), command.getExtraUserInfo(), command.getProfileFilePath());
        return user.getId();
    }
}
