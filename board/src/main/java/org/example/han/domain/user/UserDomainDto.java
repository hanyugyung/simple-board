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

        private String base64EncodedString;

        public User toEntity(PasswordEncoder passwordEncoder) {
            return new User(this.loginId, this.name, passwordEncoder.encode(this.password), this.base64EncodedString);
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
        private String base64EncodedString;

        public static UserDomainDto.GetUserInfo of(User user) {
            return new UserDomainDto.GetUserInfo(user.getId()
                    , user.getLoginId()
                    , user.getName()
                    , user.getBase64EncodedString()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateUserCommand {
        private String userName;
        private String base64EncodedString;
    }
}
