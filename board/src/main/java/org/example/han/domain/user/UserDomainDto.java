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

        private String profileUrl;

        public User toEntity(PasswordEncoder passwordEncoder) {
            return new User(this.loginId, this.name, passwordEncoder.encode(this.password), this.profileUrl);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UserLoginCommand {
        private String userId;
        private String password;
    }
}
