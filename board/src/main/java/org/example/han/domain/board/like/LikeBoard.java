package org.example.han.domain.board.like;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.domain.board.Board;
import org.example.han.domain.user.User;
import org.example.han.interfaces.CommonResponse;

@Entity
@Table(name = "like-boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeBoard extends Base {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    public LikeBoard(User createUser, Board board) {
        if(createUser == null || board == null)
            throw new InvalidParameterException(CommonResponse.CustomError.INVALID_PARAMETER);

        this.createdBy = createUser;
        this.board = board;
    }
}
