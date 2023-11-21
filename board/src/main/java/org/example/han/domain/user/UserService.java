package org.example.han.domain.user;

public interface UserService {

    long signUp(UserDomainDto.UserSignUpCommand dto);

    String login(UserDomainDto.UserLoginCommand dto);

    UserDomainDto.GetUserInfo getUser(Long requesterId);
}
