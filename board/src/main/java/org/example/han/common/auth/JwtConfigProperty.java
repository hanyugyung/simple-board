package org.example.han.common.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtConfigProperty {
    private String secretKey;
    private String issuer;
    private Long expiredTime;
}
