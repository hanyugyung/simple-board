package org.example.han.domain.board.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.domain.board.Board;
import org.example.han.domain.user.User;
import org.example.han.interfaces.CommonResponse;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends Base {

    private String comment;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    public Comment(String comment, User createUser, Board board) {
        if(!StringUtils.hasText(comment) || createUser == null || board == null)
            throw new InvalidParameterException(CommonResponse.CustomError.INVALID_PARAMETER);

        this.comment = comment;
        this.createdBy = createUser;
        this.board = board;
    }
}
