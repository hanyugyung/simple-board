package org.example.han.domain.board;

import java.util.List;

public interface BoardService {

    long createBoard(BoardDomainDto.CreateBoardCommand createBoard);

    List<BoardDomainDto.GetBoardInfo> getBoardList(int startIndex);

    long updateBoard(Long id, BoardDomainDto.UpdateBoardCommand updateBoard, Long requesterId);

    long deleteBoard(Long id, Long requesterId);
}
