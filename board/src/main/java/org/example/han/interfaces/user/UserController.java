package org.example.han.interfaces.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.han.domain.user.UserService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sing-up")
    public ResponseEntity<CommonResponse<Long>> userSignUp(
            @RequestBody @Valid UserApiDto.UserSignUpRequest request
    ) {
        return ResponseEntity.ok(
                CommonResponse.from(
                        CommonResponse.DetailResponseCode.CODE_200_CREATED
                        , userService.signUp(request.toDomainDto())
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> userLogin(
            @RequestBody @Valid UserApiDto.UserLoginRequest request
    ) {
        return ResponseEntity.ok(
                CommonResponse.from(
                        CommonResponse.DetailResponseCode.CODE_200_CREATED
                        , userService.login(request.toDomainDto())
                )
        );
    }
}
