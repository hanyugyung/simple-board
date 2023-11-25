package org.example.han.domain.file;

import lombok.RequiredArgsConstructor;
import org.example.han.domain.user.User;
import org.example.han.infrastructure.FileRepository;
import org.example.han.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    private static final String FILE_DELIMITER = "\\";

    @Value("${file.upload.location}")
    private String location;

    @Override
    @Transactional
    public FileDomainDto.UploadFileInfo uploadFile(FileDomainDto.UploadFileCommand command, Long requesterId) throws IOException {

        User createUser = userRepository.findById(requesterId).orElseThrow(
                () -> new IllegalStateException("논리적으로 절대 오면 안되는 곳...그치만 올 수도 있는 곳")
        );

        String filePath = location + FILE_DELIMITER + UUID.randomUUID() + command.getExtension();

        File uploadedFile = new File(filePath);
        if(!uploadedFile.exists()) {
            uploadedFile.mkdirs();
        }

        command.getFile().transferTo(uploadedFile);

        return FileDomainDto.UploadFileInfo.of(
                fileRepository.save(command.toEntity(filePath, createUser))
        );
    }
}
