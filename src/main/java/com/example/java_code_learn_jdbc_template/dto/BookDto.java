package com.example.java_code_learn_jdbc_template.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookDto {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @DateTimeFormat
    private LocalDate publishedDate;
}
