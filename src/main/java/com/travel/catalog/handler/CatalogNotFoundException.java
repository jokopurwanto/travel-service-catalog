package com.travel.catalog.handler;

public class CatalogNotFoundException extends RuntimeException{

    public CatalogNotFoundException(String message){
        super(message);
    }

    public CatalogNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
