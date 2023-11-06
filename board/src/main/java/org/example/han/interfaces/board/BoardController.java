package org.example.han.interfaces.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.han.domain.board.BoardService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CommonResponse> createBoard(
            @RequestBody @Valid BoardApiDto.CreateBoardRequest request
    ) {
        boardService.createBoard(request.toDomainDto());
        return ResponseEntity.ok(
                CommonResponse.from(CommonResponse.DetailResponseCode.CODE_201)
        );
    }

}
