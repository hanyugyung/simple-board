package org.example.han.domain.board;

import lombok.RequiredArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.user.User;
import org.example.han.infrastructure.BoardRepository;
import org.example.han.interfaces.CommonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private void checkBoardOwner(User owner, Long requesterId) {
        if(!requesterId.equals(owner.getId())) {
            throw new InvalidParameterException(CommonResponse.CustomErrorMessage.INVALID_ACCESS_TO_BOARD);
        }
    }

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
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomErrorMessage.NOT_FOUND_BOARD));

        checkBoardOwner(board.getCreatedBy(), requesterId);

        board.updateBoard(updateBoard.getTitle(), updateBoard.getContent());
        return board.getId();
    }

    @Override
    @Transactional
    public long deleteBoard(Long id, Long requesterId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomErrorMessage.NOT_FOUND_BOARD));

        checkBoardOwner(board.getCreatedBy(), requesterId);

        boardRepository.delete(board);
        return id;
    }
}
