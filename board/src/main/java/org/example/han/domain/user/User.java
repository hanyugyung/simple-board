package org.example.han.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.code.CustomErrorMessage;
import org.example.han.domain.Base;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Base {

    @NotBlank
    @UniqueElements
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String encodedPassword;

    private String profileUrl;

    public User(String loginId, String name, String encodedPassword, String profileUrl) {
        if(!StringUtils.hasText(loginId)
        || !StringUtils.hasText(name)
        || !StringUtils.hasText(encodedPassword))
            throw new IllegalArgumentException(CustomErrorMessage.INVALID_PARAMETER.getErrorMessage());

        this.loginId = loginId;
        this.name = name;
        this.encodedPassword = encodedPassword;
        this.profileUrl = profileUrl;
    }
}
