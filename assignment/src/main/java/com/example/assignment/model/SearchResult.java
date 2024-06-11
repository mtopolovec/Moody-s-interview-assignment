package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    @JsonIgnore
    private String kompanyId;
    private String countryCode;
    private String countryName;
    private String registrationNumber;
    private String name;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<Object> additionalData;
    private Sources sources;
}
