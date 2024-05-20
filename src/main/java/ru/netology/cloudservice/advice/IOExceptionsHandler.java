package ru.netology.cloudservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.cloudservice.dto.ErrorInResponse;

import java.io.IOException;

@RestControllerAdvice
public class IOExceptionsHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorInResponse> handlerIOExceptions(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorInResponse("Внутренняя ошибка сервера", 500));
    }
}
