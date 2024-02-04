package org.example.han.interfaces.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.han.domain.board.BoardDomainDto;
import org.example.han.domain.board.comment.CommentDomainDto;

import java.time.ZonedDateTime;
import java.util.List;

public class BoardApiDto {

    @Schema(description = "게시글 작성 API 요청")
    @Getter
    public static class CreateBoardRequest {

        @Schema(description = "게시글 제목")
        @NotBlank(message = "게시글 제목은 필수 입력 값 입니다.")
        private String title;

        @Schema(description = "게시글 내용")
        private String content;

        public BoardDomainDto.CreateBoardCommand toDomainDto() {
            return new BoardDomainDto.CreateBoardCommand(this.title, this.content);
        }
    }

    @Schema(description = "게시글 조회 API 응답")
    @Getter
    @AllArgsConstructor
    public static class GetBoardResponse {
        private Long id;
        private String title;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime updatedAt;
        private Long createUserId;
        private String createUserName;
        private String createUserProfileFilePath;
        private Long updateUserId;
        private String updateUserName;
        private String updateUserProfileFilePath;
        private List<GetCommentResponse> commentList;
        private Integer likeBoardCount;

        public static GetBoardResponse from(BoardDomainDto.GetBoardInfo dto) {
            return new GetBoardResponse(
                    dto.getId()
                    , dto.getTitle()
                    , dto.getContent()
                    , dto.getCreatedAt()
                    , dto.getUpdatedAt()
                    , dto.getCreateUserId()
                    , dto.getCreateUserName()
                    , dto.getCreateUserProfileFilePath()
                    , dto.getUpdateUserId()
                    , dto.getUpdateUserName()
                    , dto.getUpdateUserProfileFilePath()
                    , dto.getCommentInfoList()
                        .stream()
                        .map(GetCommentResponse::from)
                        .toList()
                    , dto.getLikeBoardCount()
            );
        }
    }

    @Schema(description = "게시글 수정 API 요청")
    @Getter
    public static class UpdateBoardRequest {

        @Schema(description = "게시글 제목")
        @NotBlank(message = "게시글 제목은 필수 입력 값 입니다.")
        private String title;

        @Schema(description = "게시글 내용")
        private String content;

        public BoardDomainDto.UpdateBoardCommand toDomainDto() {
            return new BoardDomainDto.UpdateBoardCommand(this.title, this.content);
        }
    }

    @Schema(description = "댓글 API 요청")
    @Getter
    public static class CreateCommentRequest {

        @Schema(description = "댓글 내용")
        @NotBlank(message = "댓글 내용은 필수 입력 값 입니다.")
        private String comment;

        public CommentDomainDto.CreateCommentCommand toDomainDto() {
            return new CommentDomainDto.CreateCommentCommand(this.comment);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GetCommentResponse {
        private Long id;
        private String comment;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime createdAt;
        private Long createUserId;
        private String createUserName;
        private String profileImageUrl;

        public static GetCommentResponse from(CommentDomainDto.GetCommentInfo dto) {
            return new GetCommentResponse(
                    dto.getId()
                    , dto.getComment()
                    , dto.getCreatedAt()
                    , dto.getCreateUserId()
                    , dto.getCreateUserName()
                    , dto.getProfileFilePath()
            );
        }
    }
}
