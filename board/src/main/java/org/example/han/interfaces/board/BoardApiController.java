package org.example.han.interfaces.board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.han.common.auth.AccessUser;
import org.example.han.domain.board.BoardService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @Operation(summary = "게시글 작성", description = "게시글 작성 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> createBoard(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @RequestBody @Valid BoardApiDto.CreateBoardRequest request
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(boardService.createBoard(request.toDomainDto(), accessUser.getId()))
        );
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardApiDto.GetBoardResponse>>> getBoardList(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @RequestParam(name = "startIndex", defaultValue = "0") int startIndex
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(boardService.getBoardList(startIndex)
                        .stream()
                        .map(BoardApiDto.GetBoardResponse::from)
                        .toList())
        );
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<Long>> updateBoard(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @PathVariable Long id
            , @RequestBody @Valid BoardApiDto.UpdateBoardRequest request
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(boardService.updateBoard(id, request.toDomainDto(), accessUser.getId()))
        );
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Long>> deleteBoard(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @PathVariable Long id
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(boardService.deleteBoard(id, accessUser.getId()))
        );
    }

    @Operation(summary = "댓글 작성", description = "댓글 작성 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommonResponse<BoardApiDto.GetCommentResponse>> createCommentToBoard(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token // 댓글은 인증뺄까....
            , @PathVariable Long boardId
            , @RequestBody @Valid BoardApiDto.CreateCommentRequest request
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(BoardApiDto.GetCommentResponse.from(
                        boardService.createComment(boardId, request.toDomainDto(), accessUser.getId())
                ))
        );
    }
}
