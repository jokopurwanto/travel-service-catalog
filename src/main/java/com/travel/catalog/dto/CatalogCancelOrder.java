package com.travel.catalog.dto;

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
public class CatalogCancelOrder {

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    private String name;

    @NotNull(message = "Invalid Start date: Start date is NULL")
    private Date startDate;

    @NotNull(message = "Invalid End date: End date is NULL")
    private Date endDate;

    @NotNull(message = "Invalid Total Person: Total Person is NULL")
    private Integer totalPerson;
}

