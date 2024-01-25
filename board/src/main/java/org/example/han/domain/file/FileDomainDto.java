package org.example.han.domain.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.han.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public class FileDomainDto {

    @Getter
    @AllArgsConstructor
    public static class UploadFileCommand {
        private String fileName;
        private String extension;
        private MultipartFile file;

        public UploadFile toEntity(String filePath, User createUser) {
            return new UploadFile(this.fileName, filePath, createUser);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UploadFileInfo {
        private Long id;
        private ZonedDateTime createdAt;
        private String fileName;
        private String filePath;

        public static FileDomainDto.UploadFileInfo of(UploadFile file) {
            return new FileDomainDto.UploadFileInfo(file.getId()
                    , file.getCreatedAt()
                    , file.getFileName()
                    , file.getFilePath()
            );
        }
    }

}
