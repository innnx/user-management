package com.example.usermanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
//全局异常处理
public class GlobalExceptionHandler {
    //处理参数验证异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String defaultMessage = error.getDefaultMessage();
            map.put(fieldName,defaultMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
    //处理业务异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex){
        log.error("业务异常：{}",ex.getMessage(),ex);
        Map<String,String> map = new HashMap<>();
        map.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    //处理所有其他异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleAllException(Exception ex){
        log.error("系统异常{}",ex.getMessage(),ex);
        Map<String,String> map = new HashMap<>();
        map.put("message","系统繁忙，请稍后重试");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

}
