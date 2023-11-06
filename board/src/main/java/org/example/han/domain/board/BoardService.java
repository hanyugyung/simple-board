package org.example.han.domain.board;

import java.util.List;

public interface BoardService {

    void createBoard(BoardDomainDto.CreateBoard createBoard);

    BoardDomainDto.GetBoard getDetailBoard(Long id);

    List<BoardDomainDto> getBoardList(int page);

    void updateBoard(BoardDomainDto.UpdateBoard updateBoard);

    long deleteBoard(Long id);
}
