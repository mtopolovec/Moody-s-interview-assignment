package com.example.assignment.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestData {

    @Size(min = 2, max = 5)
    private String countryCode;

    @NotBlank(message = "Search method cannot be blank!")
    @NotNull(message = "Search method cannot be null!")
    private String searchMethod;

    @NotBlank(message = "Search method cannot be blank!")
    @NotNull(message = "Search method cannot be null!")
    private String searchValue;


    private String alternativeSource;

    private Boolean strictSearch;

    @Min(1)
    @Max(100)
    private Integer searchLimit;
}
