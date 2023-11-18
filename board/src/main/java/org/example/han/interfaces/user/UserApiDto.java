package org.example.han.interfaces.user;

import org.example.han.domain.user.UserDomainDto;

public class UserApiDto {

    public static class UserSignUpRequest {
        private String loginId;
        private String name;
        private String password;

        private String profileUrl;

        public UserDomainDto.UserSignUpCommand toDomainDto() {
            return new UserDomainDto.UserSignUpCommand(this.loginId, this.name, this.password, this.profileUrl);
        }
    }

    public static class UserLoginRequest {
        private String loginId;
        private String password;

        public UserDomainDto.UserLoginCommand toDomainDto() {
            return new UserDomainDto.UserLoginCommand(this.loginId, this.password);
        }
    }
}
