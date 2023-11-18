package org.example.han.common.code;

import lombok.Getter;

@Getter
public enum CustomErrorMessage {

    USER_ID_ALREADY_EXISTED("이미 존재하는 계정입니다.")
    , USER_FAIL_LOGIN("로그인에 실패하였습니다.")
    , USER_FAIL_AUTHORIZATION("인증이 필요합니다.")
    , INVALID_PARAMETER("유효하지 않은 파라미터입니다.")
    , NOT_FOUND_BOARD("해당 게시글은 삭제되었거나 존재하지 않는 게시글입니다.")
    , INVALID_ACCESS_TO_BOARD("작성자 외에 게시물을 수정하거나 삭제할 수 없습니다.")
    ;

    private final String errorMessage;

    CustomErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
