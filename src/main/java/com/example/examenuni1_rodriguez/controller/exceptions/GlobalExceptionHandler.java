package com.example.examenuni1_rodriguez.controller.exceptions;

import com.example.examenuni1_rodriguez.controller.respuesta.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 405 – Method Not Allowed
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.METHOD_NOT_ALLOWED)
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 500 – Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 404 – Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.NOT_FOUND)
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.NOT_FOUND)
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 400 – Bad Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message("Validación fallida: " + String.join(", ", errors.values()))
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        String mensaje = String.format("El parámetro '%s' debe ser de tipo %s",
                exception.getName(), exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "desconocido");
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(mensaje)
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 422 – Unprocessable Entity
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.UNPROCESSABLE_ENTITY)
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 409 – Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.CONFLICT)
                .status(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 400 – Bad Request para ResourceValidationException
    @ExceptionHandler(ResourceValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ResourceValidationException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.BAD_REQUEST)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }

    // 500 – Internal Server Error para ServiceException
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException exception, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.builder()
                .message(exception.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .metodo(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
    }
}