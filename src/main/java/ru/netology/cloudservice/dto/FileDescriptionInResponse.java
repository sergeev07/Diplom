package ru.netology.cloudservice.dto;

import lombok.Data;

@Data
public class FileDescriptionInResponse {
    private final String filename;
    private final int size;
}
