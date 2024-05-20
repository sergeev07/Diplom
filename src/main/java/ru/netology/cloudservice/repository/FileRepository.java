package ru.netology.cloudservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.netology.cloudservice.entity.FileEntity;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByFileNameAndUserId(String filename, long userid);

    @Transactional
    void deleteByFileNameAndUserId(String filename, long userId);

    @Modifying
    @Transactional
    @Query(value = "update files set file_name = :newFilename where file_name = :oldFilename and user_id = :userId",
            nativeQuery = true)
    void updateFilename(@Param("oldFilename") String oldFilename, @Param("newFilename") String newFilename,
                        @Param(value = "userId") long userId);

    List<FileEntity> findAllByUserId(Long id);
}
