package com.example.examenuni1_rodriguez.controller.respuesta;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ErrorResponse {
    private String message;
    private HttpStatus error;
    private Integer status;
    private String path;
    private String metodo;
    private LocalDateTime timestamp;
}