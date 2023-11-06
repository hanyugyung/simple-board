package org.example.han.interfaces.board;

import org.example.han.domain.board.BoardDomainDto;

public class BoardApiDto {

    public static class CreateBoardRequest {
        private String title;
        private String content;

        public BoardDomainDto.CreateBoard toDomainDto() {
            return new BoardDomainDto.CreateBoard(this.title, this.content);
        }
    }

}
