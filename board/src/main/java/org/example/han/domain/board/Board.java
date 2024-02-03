package org.example.han.domain.board;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.domain.board.comment.Comment;
import org.example.han.domain.board.like.LikeBoard;
import org.example.han.domain.user.User;
import org.example.han.interfaces.CommonResponse;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Base {

    @NotEmpty
    private String title;

    @Lob
    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId", updatable = false)
    private User createdBy;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "updateUserId")
    private User updatedBy;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeBoard> likeBoardList = new ArrayList<>();

    public Board(String title, String content, User createUser) {
        if(!StringUtils.hasText(title) || createUser == null)
            throw new InvalidParameterException(CommonResponse.CustomError.INVALID_PARAMETER);

        this.title = title;
        this.content = content;
        this.createdBy = createUser;
        this.updatedBy = createUser;
    }

    public void updateBoard(String title, String content) {
        if(!StringUtils.hasText(title))
            throw new InvalidParameterException(CommonResponse.CustomError.INVALID_PARAMETER);

        this.title = title;
        this.content = content;
    }

    public void updateLastModifier(User lastModifier) {
        this.updatedBy = lastModifier;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public void deleteComment(Long commentId) {
        Comment target = this.commentList.stream()
                .filter(comment -> commentId.equals(comment.getId()))
                .findAny()
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.NOT_FOUND_COMMENT));
        this.commentList.remove(target);
    }

    public void likeBoard(User user) {
        if(this.likeBoardList.stream()
                .anyMatch(like -> user.getId().equals(like.getCreatedBy().getId()))) {
            throw new InvalidParameterException(CommonResponse.CustomError.ALREADY_LIKED);
        }
        this.likeBoardList.add(new LikeBoard(user, this));
    }

    public void cancelLikeBoard(Long userId) {
        LikeBoard target = this.likeBoardList.stream()
                .filter(like -> userId.equals(like.getCreatedBy().getId()))
                .findAny()
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.ALREADY_CANCELED));
        this.likeBoardList.remove(target);
    }
}
