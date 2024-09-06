package com.SpringSecurityAuthantication.UserAndAdminAuthantication.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.response.ApiResponseProduct;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponseProduct<?>> handleGlobalExceptions(Exception ex) {
        return new ResponseEntity<>(new ApiResponseProduct<>(500, "Unexpected error occurred: " + ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

