package com.travel.catalog.handler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ReqExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //membyat message key value
        Map<String, Object> responeBody = new LinkedHashMap<>();
        responeBody.put("status", status.value());
        responeBody.put("message", "data belum sesuai");

        //versi 1 - membuat list error dari DTO validate (Length, NotNull, NotBlank, Min, Max, dll)
//        List<String> listErrors = new ArrayList<>();
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        for (FieldError fieldError : fieldErrors){
//            String errorMassage = fieldError.getDefaultMessage();
//            listErrors.add(errorMassage);
//        }
//        responeBody.put("errors", listErrors);

        //versi 2 - membuat list error dari DTO validate (Length, NotNull, NotBlank, Min, Max, dll)
        Map<String, String> errorMap = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        responeBody.put("data", errorMap);

        return new ResponseEntity<>(responeBody,headers,status);
    }

}
