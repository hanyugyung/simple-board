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
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${board.file.upload-path}")
    private String uploadPath;

    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileDomainDto.UploadFileInfo uploadFile(
            FileDomainDto.UploadFileCommand command
            , Long requesterId) throws IOException {

        User createUser = userRepository.findById(requesterId).orElseThrow(
                () -> new IllegalStateException("논리적으로 절대 오면 안되는 곳...그치만 올 수도 있는 곳")
        );

        String relativePath =uploadPath+"/"+UUID.randomUUID() + command.getExtension();
        String savePath = new File(Paths.get("").toAbsolutePath().toFile(), relativePath).getAbsolutePath();
        System.out.println("savePath : " + savePath);


        File uploadedFile = new File(savePath);
        if(!uploadedFile.exists()) {
            uploadedFile.mkdirs();
        }

        command.getFile().transferTo(uploadedFile);

        return FileDomainDto.UploadFileInfo.of(
                fileRepository.save(command.toEntity(relativePath, createUser))
        );
    }
}
