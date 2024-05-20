package ru.netology.cloudservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.dto.FileDescriptionInResponse;
import ru.netology.cloudservice.dto.FileNameInRequest;
import ru.netology.cloudservice.service.FileService;
import ru.netology.cloudservice.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class FileController {

    private final FileService fileService;
    private final UserService authService;

    @PostMapping("/file")
    public ResponseEntity<?> addFile(@RequestHeader("auth-token") String authToken,
                                     @RequestParam("filename") String fileName,
                                     @RequestBody MultipartFile file) throws IOException {
        fileService.addFile(
                fileName,
                file.getBytes(),
                file.getSize(),
                authService.getUserIdByToken(authToken)
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String fileName) {
        fileService.deleteFile(fileName, authService.getUserIdByToken(authToken));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/file")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestHeader("auth-token") String authToken,
                                                          @RequestParam("filename") String fileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ByteArrayResource(
                        fileService.getFile(fileName, authService.getUserIdByToken(authToken))));
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFilename(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String fileName,
                                          @RequestBody @Validated @JsonProperty("filename") FileNameInRequest newFilename) {
        fileService.editFilename(fileName, newFilename.getFilename(), authService.getUserIdByToken(authToken));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDescriptionInResponse>> getFileList(@RequestHeader("auth-token") String authToken,
                                                                       @RequestParam("limit") int limit) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(fileService.getFileList(limit, authService.getUserIdByToken(authToken)));
    }
}
