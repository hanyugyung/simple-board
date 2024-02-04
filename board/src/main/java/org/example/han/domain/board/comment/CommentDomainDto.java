package org.example.han.domain.board.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.han.domain.board.Board;
import org.example.han.domain.user.User;

import java.time.ZonedDateTime;

public class CommentDomainDto {

    @AllArgsConstructor
    public static class CreateCommentCommand {
        private String comment;

        public Comment toEntity(User createUser, Board board) {
            return new Comment(this.comment, createUser, board);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetCommentInfo {
        private Long id;
        private String comment;
        private ZonedDateTime createdAt;
        private Long createUserId;
        private String createUserName;
        private String profileFilePath;

        public static GetCommentInfo of(Comment comment) {
            return new GetCommentInfo(
                    comment.getId()
                    , comment.getComment()
                    , comment.getCreatedAt()
                    , comment.getCreatedBy().getId()
                    , comment.getCreatedBy().getName()
                    , comment.getCreatedBy().getProfileFilePath()
            );
        }
    }
}
