package org.example.han.interfaces.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.han.domain.user.UserService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/sing-up")
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
}
