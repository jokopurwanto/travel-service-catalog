package com.travel.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Data
@Builder
public class CatalogDto {
    private Integer id;

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    private String name;

    @NotBlank(message = "Invalid Price: Empty price")
    @NotNull(message = "Invalid Price: Price is NULL")
    private String price;

    @NotNull(message = "Invalid Availability: availability is NULL")
    private Integer availability;

    @NotNull(message = "Invalid Start date: Start date is NULL")
    private Date startDate;

    @NotNull(message = "Invalid End date: End date is NULL")
    private Date endDate;

    @NotNull(message = "Invalid Total Person: Total Person is NULL")
    private Integer totalPerson;

    @NotNull(message = "Invalid Total Day: Total Day is NULL")
    private Integer totalDay;

}
