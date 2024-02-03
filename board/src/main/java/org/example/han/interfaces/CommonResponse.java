package org.example.han.interfaces;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CommonResponse<T> {
    private Result result;
    private T data;
    private String errorCode;
    private String errorMessage;

    public static <T> CommonResponse<T> success(T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .errorCode(CustomError.SUCCESS.errorCode)
                .errorMessage(CustomError.SUCCESS.errorMessage)
                .data(data)
                .build();
    }

    public static CommonResponse successWithError(CustomError customError) {
        return CommonResponse.builder()
                .result(Result.SUCCESS)
                .errorCode(customError.getErrorCode())
                .errorMessage(customError.getErrorMessage())
                .data(null)
                .build();
    }

    public static CommonResponse fail(String statusCode, String errorMessage) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .errorCode(statusCode)
                .errorMessage(errorMessage)
                .data(null)
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }

    @Getter
    @AllArgsConstructor
    public enum CustomError {

        SUCCESS("90000", "Request Success")

        // user
        , USER_LOGIN_ID_ALREADY_EXISTED("90100", "이미 존재하는 계정입니다.")
        , USER_FAIL_LOGIN("90101", "로그인에 실패하였습니다.")
        , NOT_FOUND_USER("90102", "조회되지 않는 사용자입니다.")

        // invalid param
        , INVALID_PARAMETER("90200", "유효하지 않은 파라미터입니다.")

        // auth
        , USER_FAIL_AUTHORIZATION("90300", "인증이 필요합니다.")
        , USER_FAIL_ACCESS("90301", "권한이 필요합니다.")

        // board
        , NOT_FOUND_BOARD("90400", "해당 게시글은 삭제되었거나 존재하지 않는 게시글입니다.")
        , INVALID_ACCESS_TO_BOARD("90401", "작성자 외에 게시물을 수정하거나 삭제할 수 없습니다.")
        , NOT_FOUND_COMMENT("90402", "해당 댓글은 이미 삭제되었거나 존재하지 않습니다.")
        , ALREADY_LIKED("90403", "이미 좋아요를 눌렀습니다.")
        , ALREADY_CANCELED("90404", "이미 취소되었습니다.")

        // file
        , INVALID_FILE("90500", "유효하지 않은 파일입니다.")

        // server error
        , INTERNAL_SERVER_ERROR("99999", "서버 내부 오류입니다.")
        ;

        private final String errorCode;
        private final String errorMessage;
    }
}
