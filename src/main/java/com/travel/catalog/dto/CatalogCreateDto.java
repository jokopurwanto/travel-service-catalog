package com.travel.catalog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Data
@Builder
public class CatalogCreateDto {

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    private String name;

    @NotBlank(message = "Invalid Price: Empty price")
    @NotNull(message = "Invalid Price: Price is NULL")
    private String price;

    @NotNull(message = "Invalid Total availability: Total Person is availability")
    private Integer availability;

    @NotNull(message = "Invalid Start date: Start date is NULL")
    private Date date;
}
