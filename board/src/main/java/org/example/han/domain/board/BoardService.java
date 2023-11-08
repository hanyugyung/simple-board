package org.example.han.domain.board;

import java.util.List;

public interface BoardService {

    long createBoard(BoardDomainDto.CreateBoard createBoard);

    List<BoardDomainDto.GetBoard> getBoardList(int startIndex);

    long updateBoard(Long id, BoardDomainDto.UpdateBoard updateBoard);

    long deleteBoard(Long id);
}
