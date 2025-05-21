package com.example.aplicacioncorporativa.controller;
import jakarta.el.MethodNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptions {
/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
            InformacionException info = new InformacionException();
        List<String> listaErrores = new ArrayList<>();
        StringBuilder mensaje = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error  )->{listaErrores.add(error.getDefaultMessage());});

        info.setStatus(400);
        info.setListaErrores(listaErrores);

        return new ResponseEntity<>(listaErrores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptions(Exception ex) {
        return new ResponseEntity<>("Error inesperado: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

 */

    @RestControllerAdvice public class CustomExceptionHandler {

/*

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleGeneralExceptions(MethodNotFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error inesperado: " + ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

 */
        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<Map<String, Object>> handleGeneralExceptions(NoResourceFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error inesperado: " + ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }}