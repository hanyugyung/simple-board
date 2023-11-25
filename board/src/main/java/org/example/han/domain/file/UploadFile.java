package org.example.han.domain.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.han.common.exception.InvalidParameterException;
import org.example.han.domain.Base;
import org.example.han.domain.user.User;
import org.example.han.interfaces.CommonResponse;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "upload-files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile extends Base {

    @NotBlank
    private String fileName;

    @NotBlank
    private String filePath;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "createUserId", updatable = false)
    private User createdBy;

    public UploadFile(String fileName, String filePath, User createUser) {
        if(!StringUtils.hasText(fileName)
        || !StringUtils.hasText(filePath)
        || createUser == null)
            throw new InvalidParameterException(CommonResponse.CustomErrorMessage.INVALID_PARAMETER);

        this.fileName = fileName;
        this.filePath = filePath;
        this.createdBy = createUser;
    }
}
