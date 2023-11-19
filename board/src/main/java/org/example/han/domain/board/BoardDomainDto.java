package org.example.han.domain.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

public class BoardDomainDto {

    @AllArgsConstructor
    public static class CreateBoardCommand {
        private String title;
        private String content;

        public Board toEntity() {
            return new Board(this.title, this.content);
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
        private String createUserId;
        private String createUserName;
        private String updateUserId;
        private String updateUserName;

        // FIXME use mapstruct
        public static GetBoardInfo of(Board board) {
            return new GetBoardInfo(board.getId()
                    , board.getTitle()
                    , board.getContent()
                    , board.getCreatedAt()
                    , board.getUpdatedAt()
                    , board.getCreatedBy().getLoginId()
                    , board.getCreatedBy().getName()
                    , board.getUpdatedBy().getLoginId()
                    , board.getUpdatedBy().getName()
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
