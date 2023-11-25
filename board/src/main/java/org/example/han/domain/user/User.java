package org.example.han.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.interfaces.CommonResponse;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Base {

    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String encodedPassword;

    private String base64EncodedString;

    public User(String loginId, String name, String encodedPassword, String base64EncodedString) {
        if(!StringUtils.hasText(loginId)
        || !StringUtils.hasText(name)
        || !StringUtils.hasText(encodedPassword))
            throw new InvalidParameterException(CommonResponse.CustomErrorMessage.INVALID_PARAMETER);

        this.loginId = loginId;
        this.name = name;
        this.encodedPassword = encodedPassword;
        this.base64EncodedString = base64EncodedString;
    }
}
