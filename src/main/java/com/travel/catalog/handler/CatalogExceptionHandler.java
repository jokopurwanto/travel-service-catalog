package com.travel.catalog.handler;

import com.travel.catalog.dto.CatalogExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CatalogExceptionHandler {

    @ExceptionHandler(value = {CatalogNotFoundException.class})
    public ResponseEntity<Object> handleCatalogNotFoundException (CatalogNotFoundException catalogNotFoundException){
        CatalogExceptionDto catalogExceptionDto = new CatalogExceptionDto(
                HttpStatus.NOT_FOUND.value(),
                catalogNotFoundException.getMessage(),
                catalogNotFoundException.getCause()

        );
        return new ResponseEntity<>(catalogExceptionDto, HttpStatus.NOT_FOUND);
    }
}
