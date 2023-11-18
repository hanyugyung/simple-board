package org.example.han.interfaces.board;

import lombok.AllArgsConstructor;
import org.example.han.domain.board.BoardDomainDto;

import java.time.ZonedDateTime;

public class BoardApiDto {

    public static class CreateBoardRequest {
        private String title;
        private String content;

        public BoardDomainDto.CreateBoardCommand toDomainDto() {
            return new BoardDomainDto.CreateBoardCommand(this.title, this.content);
        }
    }

    @AllArgsConstructor
    public static class GetBoardResponse {
        private Long id;
        private String title;
        private String content;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private String createUserId;
        private String createUserName;
        private String updateUserId;
        private String updateUserName;

        public static GetBoardResponse from(BoardDomainDto.GetBoardInfo dto) {
            return new GetBoardResponse(
                    dto.getId()
                    , dto.getTitle()
                    , dto.getContent()
                    , dto.getCreatedAt()
                    , dto.getUpdatedAt()
                    , dto.getCreateUserId()
                    , dto.getCreateUserName()
                    , dto.getUpdateUserId()
                    , dto.getUpdateUserName()
            );
        }
    }

    public static class UpdateBoardRequest {
        private String title;
        private String content;

        public BoardDomainDto.UpdateBoardCommand toDomainDto() {
            return new BoardDomainDto.UpdateBoardCommand(this.title, this.content);
        }
    }
}
