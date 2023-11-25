package org.example.han.domain.user;

import org.example.han.interfaces.CommonResponse;

public interface UserService {

    long signUp(UserDomainDto.UserSignUpCommand dto);

    String login(UserDomainDto.UserLoginCommand dto);

    UserDomainDto.GetUserInfo getUser(Long requesterId);

    Long updateUser(UserDomainDto.UpdateUserCommand command, Long requesterId);
}
