package org.example.han.interfaces.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.han.domain.board.BoardService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CommonResponse<Long>> createBoard(
            @RequestBody @Valid BoardApiDto.CreateBoardRequest request
    ) {
        return ResponseEntity.ok(
                CommonResponse.from(
                        CommonResponse.DetailResponseCode.CODE_200_CREATED
                        , boardService.createBoard(request.toDomainDto())
                )
        );
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardApiDto.GetBoardResponse>>> getBoardList(
            @RequestParam(name = "startIndex", defaultValue = "0") int startIndex
    ) {
        return ResponseEntity.ok(
                CommonResponse.from(boardService.getBoardList(startIndex)
                        .stream()
                        .map(BoardApiDto.GetBoardResponse::fromDomainDto)
                        .toList())
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<Long>> updateBoard(
            @PathVariable Long id
            , @RequestBody @Valid BoardApiDto.UpdateBoardRequest request
    ) {
        return ResponseEntity.ok(
                CommonResponse.from(
                        CommonResponse.DetailResponseCode.CODE_200_UPDATED
                        , boardService.updateBoard(id, request.toDomainDto(), 1l)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Long>> deleteBoard(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                CommonResponse.from(
                        CommonResponse.DetailResponseCode.CODE_200_DELETED
                        , boardService.deleteBoard(id, 1l)
                )
        );
    }
}
