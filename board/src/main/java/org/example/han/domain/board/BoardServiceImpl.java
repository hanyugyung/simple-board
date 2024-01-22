package org.example.han.domain.board;

import lombok.RequiredArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.board.comment.Comment;
import org.example.han.domain.board.comment.CommentDomainDto;
import org.example.han.domain.user.User;
import org.example.han.infrastructure.BoardRepository;
import org.example.han.infrastructure.UserRepository;
import org.example.han.interfaces.CommonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    // FIXME 게시글 작성자 이외에도 게시글을 변경할 수 있다면, 권한 테이블이 추가로 필요할 거 같음~~~
    private void checkBoardOwner(User owner, Long requesterId) {
        if(!requesterId.equals(owner.getId())) {
            throw new InvalidParameterException(CommonResponse.CustomError.INVALID_ACCESS_TO_BOARD);
        }
    }

    @Override
    @Transactional
    public long createBoard(BoardDomainDto.CreateBoardCommand command, Long requesterId) {

        User createUser = userRepository.findById(requesterId).orElseThrow(
                () -> new IllegalStateException("논리적으로 절대 오면 안되는 곳...그치만 올 수도 있는 곳")
        );

        return boardRepository
                .save(command.toEntity(createUser))
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
    public long updateBoard(Long id, BoardDomainDto.UpdateBoardCommand command, Long requesterId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.NOT_FOUND_BOARD));

        //checkBoardOwner(board.getCreatedBy(), requesterId);

        board.updateBoard(command.getTitle(), command.getContent());

        if(!requesterId.equals(board.getCreatedBy().getId())) {
            board.updateLastModifier(
                userRepository.findById(requesterId).orElseThrow(
                        () -> new IllegalStateException("논리적으로 절대 오면 안되는 곳...그치만 올 수도 있는 곳")
                )
            );
        }

        return id;
    }

    @Override
    @Transactional
    public long deleteBoard(Long id, Long requesterId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.NOT_FOUND_BOARD));

        //checkBoardOwner(board.getCreatedBy(), requesterId);

        boardRepository.delete(board);
        return id;
    }

    @Override
    @Transactional
    public long createComment(Long boardId
            , CommentDomainDto.CreateCommentCommand command
            , Long requesterId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new InvalidParameterException(CommonResponse.CustomError.NOT_FOUND_BOARD));

        Comment comment = command.toEntity(
                userRepository.findById(requesterId).orElseThrow(
                        () -> new IllegalStateException("논리적으로 절대 오면 안되는 곳...그치만 올 수도 있는 곳")
                )
                , board
        );

        board.addComment(comment);
        boardRepository.save(board);
        return comment.getId();
    }
}
