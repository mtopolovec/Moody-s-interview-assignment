package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponseData {
    @JsonIgnore
    private String countryCode;
    @JsonIgnore
    private String searchMethod;
    @JsonIgnore
    private String searchValue;
    @JsonIgnore
    private Object searchId;
    @JsonIgnore
    private String status;
    @JsonIgnore
    private Date requestTime;
    @JsonIgnore
    private Date responseTime;
    @JsonIgnore
    private int limit;
    private List<SearchResult> searchResults;
}
