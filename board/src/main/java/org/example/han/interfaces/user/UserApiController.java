package org.example.han.interfaces.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.han.common.auth.AccessUser;
import org.example.han.domain.user.UserService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<Long>> userSignUp(
            @RequestBody @Valid UserApiDto.UserSignUpRequest request
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(userService.signUp(request.toDomainDto()))
        );
    }

    @Operation(summary = "토큰 발급 요청", description = "토큰 발급 요청 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> userLogin(
            @RequestBody @Valid UserApiDto.UserLoginRequest request
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(userService.login(request.toDomainDto()))
        );
    }

    @Operation(summary = "특정 사용자 정보 조회", description = "특정 사용자 정보 조회 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserApiDto.GetUserResponse>> getUser(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @PathVariable(name = "id") Long id
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(
                        UserApiDto.GetUserResponse.from(
                                userService.getUser(id))
                )
        );
    }

    @Operation(summary = "내 정보 조회", description = "내 정보 조회 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/my-page")
    public ResponseEntity<CommonResponse<UserApiDto.GetUserResponse>> getUser(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(
                        UserApiDto.GetUserResponse.from(
                                userService.getUser(accessUser.getId()))
                )
        );
    }

    @Operation(summary = "내 정보 수정", description = "요청자의 정보 수정 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PatchMapping("/my-page")
    public ResponseEntity<CommonResponse<Long>> updateUser(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @RequestBody @Valid UserApiDto.UpdateUserRequest request
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return ResponseEntity.ok(
                CommonResponse.success(
                        userService.updateUser(request.toDomainDto(), accessUser.getId())
                )
        );
    }
}
