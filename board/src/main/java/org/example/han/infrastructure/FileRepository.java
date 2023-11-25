package org.example.han.infrastructure;

import org.example.han.domain.file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<UploadFile, Long> {
}
