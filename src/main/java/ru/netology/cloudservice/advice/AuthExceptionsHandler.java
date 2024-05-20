package ru.netology.cloudservice.advice;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.cloudservice.dto.ErrorInResponse;

@RestControllerAdvice
public class AuthExceptionsHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorInResponse> handlerAuthExceptions(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorInResponse(e.getMessage(), 400));
    }
}