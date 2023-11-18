package org.example.han.domain.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

public class BoardDomainDto {

    @AllArgsConstructor
    public static class CreateBoard {
        private String title;
        private String content;

        public Board toEntity() {
            return new Board(this.title, this.content);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetBoard {
        private Long id;
        private String title;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime updatedAt;
        private String createdBy;
        private String updatedBy;

        // FIXME use mapstruct
        public static GetBoard of(Board board) {
            return new GetBoard(board.getId()
                    , board.getTitle()
                    , board.getContent()
                    , board.getCreatedAt()
                    , board.getUpdatedAt()
                    , board.getCreatedBy()
                    , board.getUpdatedBy());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateBoard {
        private String title;
        private String content;
    }
}
