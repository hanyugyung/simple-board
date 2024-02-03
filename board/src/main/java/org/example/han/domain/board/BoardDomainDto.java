package org.example.han.domain.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.han.domain.board.comment.CommentDomainDto;
import org.example.han.domain.user.User;

import java.time.ZonedDateTime;
import java.util.List;

public class BoardDomainDto {

    @AllArgsConstructor
    public static class CreateBoardCommand {
        private String title;
        private String content;

        public Board toEntity(User createUser) {
            return new Board(this.title, this.content, createUser);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetBoardInfo {
        private Long id;
        private String title;
        private String content;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private Long createUserId;
        private String createUserName;
        private String createUserProfileFilePath;
        private Long updateUserId;
        private String updateUserName;
        private String updateUserProfileFilePath;
        private List<CommentDomainDto.GetCommentInfo> commentInfoList;
        private Integer likeBoardCount;

        // FIXME use mapstruct
        public static GetBoardInfo of(Board board) {
            return new GetBoardInfo(board.getId()
                    , board.getTitle()
                    , board.getContent()
                    , board.getCreatedAt()
                    , board.getUpdatedAt()
                    , board.getCreatedBy().getId()
                    , board.getCreatedBy().getName()
                    , board.getCreatedBy().getProfileFilePath()
                    , board.getUpdatedBy().getId()
                    , board.getUpdatedBy().getName()
                    , board.getUpdatedBy().getProfileFilePath()
                    , board.getCommentList().stream().map(CommentDomainDto.GetCommentInfo::of).toList()
                    , board.getLikeBoardList().size()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateBoardCommand {
        private String title;
        private String content;
    }
}
