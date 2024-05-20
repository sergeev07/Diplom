package ru.netology.cloudservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
@NoArgsConstructor
public class FileNameInRequest {
    @NotBlank
    private String filename;
}
