package org.example.han.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDomainDto {

    @Getter
    @AllArgsConstructor
    public static class UserSignUpCommand {
        private String loginId;
        private String name;
        private String password;

        private String extraUserInfo;

        private String profileFilePath;

        public User toEntity(PasswordEncoder passwordEncoder) {
            return new User(this.loginId, this.name, passwordEncoder.encode(this.password), this.extraUserInfo, this.extraUserInfo);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UserLoginCommand {
        private String userId;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class GetUserInfo {
        private Long id;
        private String userLoginId;
        private String userName;
        private String extraUserInfo;
        private String profileFilePath;

        public static UserDomainDto.GetUserInfo of(User user) {
            return new UserDomainDto.GetUserInfo(user.getId()
                    , user.getLoginId()
                    , user.getName()
                    , user.getExtraUserInfo()
                    , user.getProfileFilePath()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateUserCommand {
        private String userName;
        private String extraUserInfo;
        private String profileFilePath;
    }
}
