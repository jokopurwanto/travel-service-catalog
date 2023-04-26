package com.travel.catalog.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class CatalogExceptionDto {
    private final Integer status;
    private final String message;
    private final Throwable throwable;
}
