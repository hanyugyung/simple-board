package org.example.han.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

public class BoardDomainDto {

    @AllArgsConstructor
    public static class CreateBoard {
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class GetBoard {
        private Long id;
        private String title;
        private String content;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
    }

    @AllArgsConstructor
    public static class UpdateBoard {
        private String title;
        private String content;
    }
}
