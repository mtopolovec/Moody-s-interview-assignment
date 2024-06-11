package com.example.assignment.service;

import com.example.assignment.model.RequestData;
import com.example.assignment.model.ResponseData;
import com.example.assignment.model.SearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
@ExtendWith(MockitoExtension.class)
class FetchDataImpTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private FetchData fetchData;

    @BeforeEach
    void setUp() {
        fetchData = new FetchDataImp(restTemplate, objectMapper);
    }

    @Test
    void fetchData_isSuccessful() throws IOException {

        RequestData requestData = new RequestData();
        requestData.setCountryCode("HR");
        requestData.setSearchMethod("name");
        requestData.setSearchValue("d.o.o.");

        String responseDataJson = "{\"exampleField\": \"exampleValue\"}";
        ResponseData responseData = getResponseData();

        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseDataJson, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);
        when(objectMapper.readValue(responseDataJson, ResponseData.class)).thenReturn(responseData);

        ResponseData fetchedData = fetchData.fetchData(requestData);

        assertThat(fetchedData, is(notNullValue()));
        assertThat(responseData, equalTo(fetchedData));
    }

    @Test
    void fetchData_RequestFailed() {

        // Mock request data
        RequestData requestData = new RequestData();
        requestData.setCountryCode("HR");
        requestData.setSearchMethod("name");
        requestData.setSearchValue("d.o.o.");

        // Mock restTemplate.exchange() to return a non-OK status code
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR));

        // Initialize FetchDataImp with the mocked RestTemplate
        fetchData = new FetchDataImp(restTemplate, null); // ObjectMapper is not needed for this test

        // Verify that the method throws an exception when the request fails
        assertThrows(RuntimeException.class, () -> fetchData.fetchData(requestData));
    }

    private ResponseData getResponseData() {
        SearchResult searchResult = new SearchResult();
        searchResult.setKompanyId("testId");
        searchResult.setCountryCode("HR");
        searchResult.setCountryName("Croatia");
        searchResult.setRegistrationNumber("testRegistrationNumber");
        searchResult.setName("testName");
        searchResult.setStatus("CLOSED");
        searchResult.setAddress("testAddress");

        List<SearchResult> searchResults = Arrays.asList(searchResult);

        ResponseData responseData = new ResponseData();
        responseData.setSearchResults(searchResults);
        return responseData;
    }
}