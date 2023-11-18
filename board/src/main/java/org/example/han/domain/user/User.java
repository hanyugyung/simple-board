package org.example.han.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.domain.Base;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Base {

    @NotBlank
    @UniqueElements
    private String userId;

    @NotBlank
    private String name;

    private String profileUrl;
}
