package org.example.han.interfaces.file;

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
import lombok.extern.slf4j.Slf4j;
import org.example.han.common.auth.AccessUser;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.file.FileService;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileApiController {

    private final FileService fileService;

    @Operation(summary = "파일 업로드 API", description = "파일 업로드 API 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
            , MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<CommonResponse<FileApiDto.UploadFileResponse>> uploadFile(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @ModelAttribute @Valid FileApiDto.UploadFileRequest request
            , @AuthenticationPrincipal AccessUser accessUser
    ) throws IOException {

//        request.validateFile();

        return ResponseEntity.ok(
                CommonResponse.success(
                        FileApiDto.UploadFileResponse.from(fileService.uploadFile(request.toDomainDto(), accessUser.getId()))
                )
        );
    }
}
