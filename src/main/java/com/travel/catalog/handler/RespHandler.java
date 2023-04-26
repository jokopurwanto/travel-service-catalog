package com.travel.catalog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RespHandler {
    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus status, Object responseObject){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("massage", message);
        response.put("data",responseObject);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Object> responseBuilderUpdateCatalog(String message, HttpStatus status, Object responseObject){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("massage", message);
        response.put("data",responseObject);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Object> responseBuilderDeleteCatalog(String message, HttpStatus status, Object responseObject){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("massage", message);
        response.put("data",responseObject);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Object> responseBuilderCreateCatalog(String message, HttpStatus status, Object responseObject){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("massage", message);
        response.put("data",responseObject);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Object> responseBuilderGetAllCatalog(String message, HttpStatus status, Object responseObject){
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("massage", message);
        response.put("data",responseObject);

        return new ResponseEntity<>(response, status);
    }
}
