package org.example.han.interfaces.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.file.FileDomainDto;
import org.example.han.interfaces.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;

public class FileApiDto {

    private final static List<String> allowContentType = List.of("image/jpeg", "image/png", "image/gif");

    @Schema(description = "파일 업로드 API 요청")
    @Getter
    @AllArgsConstructor
    public static class UploadFileRequest {

        @Schema(description = "파일명")
        @NotBlank(message = "파일명은 필수 입력 값 입니다.")
        private String fileName;

        @Schema(description = "파일명")
        private MultipartFile file;

        public FileDomainDto.UploadFileCommand toDomainDto() {

            String originalFileExtension;

            if(file.getContentType().contains("image/jpeg")){
                originalFileExtension = ".jpg";
            }
            else if(file.getContentType().contains("image/png")){
                originalFileExtension = ".png";
            }
            else if(file.getContentType().contains("image/gif")){
                originalFileExtension = ".gif";
            }
            else throw new InvalidParameterException(CommonResponse.CustomError.INVALID_FILE); // 앞서 검증하므로 여기 올 일 없음.

            return new FileDomainDto.UploadFileCommand(this.fileName + originalFileExtension, originalFileExtension, this.file);
        }

        public void validateFile() {
            if (this.file.getContentType() == null
                    || file.isEmpty()
                    || !allowContentType.contains(file.getContentType())) {
                throw new InvalidParameterException(CommonResponse.CustomError.INVALID_FILE);
            }
        }
    }

    @Schema(description = "파일 업로드 API 응답")
    @Getter
    @AllArgsConstructor
    public static class UploadFileResponse {
        private Long id;
        private String fileName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime createdAt;
        private String filePath;

        public static UploadFileResponse from(FileDomainDto.UploadFileInfo dto) {
            return new UploadFileResponse(
                dto.getId(), dto.getFileName(), dto.getCreatedAt(), dto.getFilePath()
            );
        }
    }
}
