package org.example.han.domain.board;

import lombok.RequiredArgsConstructor;
import org.example.han.common.code.CustomErrorMessage;
import org.example.han.domain.user.User;
import org.example.han.infrastructure.BoardRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public long createBoard(BoardDomainDto.CreateBoardCommand createBoard) {
        return boardRepository
                .save(createBoard.toEntity())
                .getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDomainDto.GetBoardInfo> getBoardList(int startIndex) {
        return boardRepository.findByIdBetween(startIndex, startIndex+9)
                .stream()
                .map(BoardDomainDto.GetBoardInfo::of)
                .toList();
    }

    @Override
    @Transactional
    public long updateBoard(Long id, BoardDomainDto.UpdateBoardCommand updateBoard, Long requesterId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BOARD.getErrorMessage()));

        checkBoardOwner(board.getCreatedBy(), requesterId);

        board.updateBoard(updateBoard.getTitle(), updateBoard.getContent());
        return board.getId();
    }

    @Override
    @Transactional
    public long deleteBoard(Long id, Long requesterId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BOARD.getErrorMessage()));

        checkBoardOwner(board.getCreatedBy(), requesterId);

        boardRepository.delete(board);
        return id;
    }

    private void checkBoardOwner(User owner, Long requesterId) {
        if(!requesterId.equals(owner.getId())) {
            throw new IllegalStateException(CustomErrorMessage.INVALID_ACCESS_TO_BOARD.getErrorMessage());
        }
    }
}
