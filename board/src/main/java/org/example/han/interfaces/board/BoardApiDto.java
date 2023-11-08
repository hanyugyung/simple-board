package org.example.han.interfaces.board;

import lombok.AllArgsConstructor;
import org.example.han.domain.board.BoardDomainDto;

import java.time.ZonedDateTime;

public class BoardApiDto {

    public static class CreateBoardRequest {
        private String title;
        private String content;

        public BoardDomainDto.CreateBoard toDomainDto() {
            return new BoardDomainDto.CreateBoard(this.title, this.content);
        }
    }

    @AllArgsConstructor
    public static class GetBoardResponse {
        private Long id;
        private String title;
        private String content;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private String createdBy;
        private String updatedBy;

        public static GetBoardResponse fromDomainDto(BoardDomainDto.GetBoard dto) {
            return new GetBoardResponse(
                    dto.getId()
                    , dto.getTitle()
                    , dto.getContent()
                    , dto.getCreatedAt()
                    , dto.getUpdatedAt()
                    , dto.getCreatedBy()
                    , dto.getUpdatedBy()
            );
        }
    }

    public static class UpdateBoardRequest {
        private String title;
        private String content;

        public BoardDomainDto.UpdateBoard toDomainDto() {
            return new BoardDomainDto.UpdateBoard(this.title, this.content);
        }
    }
}
