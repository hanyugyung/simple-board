package org.example.han.domain.file;

import java.io.IOException;

public interface FileService {

    FileDomainDto.UploadFileInfo uploadFile(FileDomainDto.UploadFileCommand command, Long requesterId) throws IOException;

}
