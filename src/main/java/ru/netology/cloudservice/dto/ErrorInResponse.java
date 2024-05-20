package ru.netology.cloudservice.dto;

import lombok.Data;

@Data
public class ErrorInResponse {
    private final String message;
    private final  int id;
}
