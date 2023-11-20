package org.example.han.domain.board;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.domain.user.User;
import org.example.han.interfaces.CommonResponse;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends Base {

    @NotEmpty
    private String title;

    private String content;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "createUserId")
    private User createdBy;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "updateUserId")
    private User updatedBy;

    public Board(String title, String content, User createUser) {
        if(!StringUtils.hasText(title))
            throw new InvalidParameterException(CommonResponse.CustomErrorMessage.INVALID_PARAMETER);

        this.title = title;
        this.content = content;
        this.createdBy = createUser;
        this.updatedBy = createUser;
    }

    public void updateBoard(String title, String content) {
        if(!StringUtils.hasText(title))
            throw new InvalidParameterException(CommonResponse.CustomErrorMessage.INVALID_PARAMETER);

        this.title = title;
        this.content = content;
    }

    public void updateLastModifier(User lastModifier) {
        this.updatedBy = lastModifier;
    }
}
