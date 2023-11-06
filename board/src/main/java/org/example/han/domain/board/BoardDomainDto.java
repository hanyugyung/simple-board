package org.example.han.domain.board;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

public class BoardDomainDto {

    @AllArgsConstructor
    public static class CreateBoard {
        private String title;
        private String content;
    }

    public static class GetBoard {
        private String title;
        private String content;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
    }

    public static class UpdateBoard {
        private String title;
        private String content;
    }
}
