package ru.netology.cloudservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloudservice.dto.FileDescriptionInResponse;
import ru.netology.cloudservice.entity.FileEntity;
import ru.netology.cloudservice.repository.FileRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    public static final String FILE_NAME = "test_file.txt";
    public static final byte[] CONTENT = "content in test file".getBytes();
    public static final String NEW_FILE_NAME = "824.txt";

    @Mock
    private FileRepository mockFileRepository;

    private FileService fileServiceUnderTest;

    @BeforeEach
    void setUp() {
        fileServiceUnderTest = new FileService(mockFileRepository);
    }

    @Test
    void testAddFile() {
        fileServiceUnderTest.addFile(FILE_NAME, CONTENT, 20L, 1L);
        final FileEntity entity = new FileEntity();
        entity.setFileName(FILE_NAME);
        entity.setFileContent(CONTENT);
        entity.setFileSize(20L);
        entity.setUserId(1L);
        verify(mockFileRepository).save(entity);
    }

    @Test
    void testDeleteFile() {
        fileServiceUnderTest.deleteFile(FILE_NAME, 1L);
        verify(mockFileRepository).deleteByFileNameAndUserId(FILE_NAME, 1L);
    }

    @Test
    void testGetFile() {
        final FileEntity fileEntity1 = new FileEntity();
        fileEntity1.setId(1L);
        fileEntity1.setFileName(FILE_NAME);
        fileEntity1.setFileContent(CONTENT);
        fileEntity1.setFileSize(20L);
        fileEntity1.setUserId(1L);
        final Optional<FileEntity> fileEntity = Optional.of(fileEntity1);
        when(mockFileRepository.findByFileNameAndUserId(FILE_NAME, 1L)).thenReturn(fileEntity);
        final byte[] result = fileServiceUnderTest.getFile(FILE_NAME, 1L);
        assertThat(result).isEqualTo(CONTENT);
    }

    @Test
    void testGetFile_FileRepositoryReturnsAbsent() {
        when(mockFileRepository.findByFileNameAndUserId(FILE_NAME, 1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> fileServiceUnderTest.getFile(FILE_NAME, 1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testEditFilename() {
        fileServiceUnderTest.editFilename(FILE_NAME, NEW_FILE_NAME, 1L);
        verify(mockFileRepository).updateFilename(FILE_NAME, NEW_FILE_NAME, 1L);
    }

    @Test
    void testGetFileList() {
        final List<FileDescriptionInResponse> expectedResult = List.of(new FileDescriptionInResponse(FILE_NAME, 20));
        final FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);
        fileEntity.setFileName(FILE_NAME);
        fileEntity.setFileContent(CONTENT);
        fileEntity.setFileSize(20L);
        fileEntity.setUserId(1L);
        final List<FileEntity> fileEntities = List.of(fileEntity);
        when(mockFileRepository.findAllByUserId(1L)).thenReturn(fileEntities);
        final List<FileDescriptionInResponse> result = fileServiceUnderTest.getFileList(3, 1L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFileList_FileRepositoryReturnsNoItems() {
        when(mockFileRepository.findAllByUserId(1L)).thenReturn(Collections.emptyList());
        final List<FileDescriptionInResponse> result = fileServiceUnderTest.getFileList(3, 1L);
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
