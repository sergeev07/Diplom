package ru.netology.cloudservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.cloudservice.dto.FileDescriptionInResponse;
import ru.netology.cloudservice.entity.FileEntity;
import ru.netology.cloudservice.repository.FileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public synchronized void addFile(String filename, byte[] file, long size, long userId) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(filename);
        fileEntity.setFileContent(file);
        fileEntity.setFileSize(size);
        fileEntity.setUserId(userId);
        fileRepository.save(fileEntity);
        logger.info("Загружен файл " + filename + " пользователем " + userId);
    }

    public synchronized void deleteFile(String filename, long userId) {
        fileRepository.deleteByFileNameAndUserId(filename, userId);
        logger.info("Файл " + filename + " удален пользователем " + userId);
    }

    public byte[] getFile(String filename, long userId) {
        logger.info("Скачивание файла " + filename + " пользователем " + userId);
        return fileRepository.findByFileNameAndUserId(filename, userId).get().getFileContent();
    }

    public void editFilename(String oldFilename, String newFilename, long userId) {
        fileRepository.updateFilename(oldFilename, newFilename, userId);
        logger.info("Файл " + oldFilename + " переименован в " +newFilename+ " пользователем " + userId);
    }

    public List<FileDescriptionInResponse> getFileList(int limit, long userId) {
        final List<FileEntity> files = fileRepository.findAllByUserId(userId);
        return files
                .stream()
                .limit(limit)
                .map(file -> new FileDescriptionInResponse(file.getFileName(),
                        file.getFileContent().length))
                .collect(Collectors.toList());
    }
}
