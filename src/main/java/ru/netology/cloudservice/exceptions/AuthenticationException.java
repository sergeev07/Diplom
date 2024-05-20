package ru.netology.cloudservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Пользователь не авторизован");
    }
}
