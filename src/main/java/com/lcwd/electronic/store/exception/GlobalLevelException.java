package com.lcwd.electronic.store.exception;

import com.lcwd.electronic.store.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalLevelException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandel(ResourceNotFoundException ex)
    {
        String message = ex.getMessage();

        ApiResponse apiResponse=new ApiResponse(message, false);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException ex)
    {
        Map<String, String> map=new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{

            String fieldName = ((FieldError)error).getField();

            String defaultMessage = error.getDefaultMessage();

            map.put(fieldName, defaultMessage);
        });

        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }
}
