package org.example.han.domain.board;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.domain.board.comment.Comment;
import org.example.han.domain.user.User;
import org.example.han.interfaces.CommonResponse;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends Base {

    @NotEmpty
    private String title;

    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId", updatable = false)
    private User createdBy;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "updateUserId")
    private User updatedBy;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public Board(String title, String content, User createUser) {
        if(!StringUtils.hasText(title) || createUser == null)
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

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
