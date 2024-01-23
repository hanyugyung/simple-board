package org.example.han.domain.board;

import org.example.han.domain.board.comment.CommentDomainDto;

import java.util.List;

public interface BoardService {

    long createBoard(BoardDomainDto.CreateBoardCommand command, Long requesterId);

    List<BoardDomainDto.GetBoardInfo> getBoardList(int page, int size);

    long updateBoard(Long id, BoardDomainDto.UpdateBoardCommand command, Long requesterId);

    long deleteBoard(Long id, Long requesterId);

    long createComment(Long boardId, CommentDomainDto.CreateCommentCommand command, Long requesterId);
}
