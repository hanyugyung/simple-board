package org.example.han.interfaces.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.example.han.domain.user.UserDomainDto;

public class UserApiDto {

    @Schema(description = "회원가입 API 요청")
    @Getter
    public static class UserSignUpRequest {

        @Schema(description = "로그인 아이디", example = "test00001")
        @NotBlank(message = "로그인 아이디는 필수 입력 값 입니다.")
        private String loginId;

        @Schema(description = "사용자 이름")
        @NotBlank(message = "사용자 이름은 필수 입력 값 입니다.")
        private String name;

        @Schema(description = "사용자 비밀번호, 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상", example = "test00001!!!!")
        @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
        @Pattern(regexp = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])" +
                "|(?=.*[A-Z])(?=.*[a-z])(?=.*[$@$!%*#?&])" +
                "|(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*#?&])" +
                "|(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&]))" +
                "[A-Za-z0-9$@$!%*#?&]{12,}$"
                , message = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상이어야 합니다.")
        private String password;

        @Schema(description = "사용자 프로필")
        private String base64EncodedString;

        public UserDomainDto.UserSignUpCommand toDomainDto() {
            return new UserDomainDto.UserSignUpCommand(this.loginId, this.name, this.password, this.base64EncodedString);
        }
    }

    @Schema(description = "토큰 발급 API 요청")
    @Getter
    public static class UserLoginRequest {

        @Schema(description = "로그인 아이디", example = "test00001")
        @NotBlank(message = "로그인 아이디는 필수 입력 값 입니다.")
        private String loginId;

        @Schema(description = "사용자 비밀번호", example = "test00001!!!!")
        @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
        private String password;

        public UserDomainDto.UserLoginCommand toDomainDto() {
            return new UserDomainDto.UserLoginCommand(this.loginId, this.password);
        }
    }
}
