package com.example.assignment.service;

import com.example.assignment.model.RequestData;
import com.example.assignment.model.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class FetchDataImp implements FetchData {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${headerToken}")
    private String headerKey;

    public FetchDataImp(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseData fetchData(RequestData requestData) {
        String url = "https://api.kompany.com/api/v2/company/indexSearch";

        HttpHeaders headers = new HttpHeaders();
        headers.set("user_key", headerKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestData> entity = new HttpEntity<>(requestData, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            log.debug("Request to fetch data failed with status code: {}", response.getStatusCode());
            throw new RuntimeException("Request to fetch data failed with status code: " + response.getStatusCode());
        }
        return mappedData(response.getBody());
    }

    private ResponseData mappedData(String data) {
        ResponseData responseData = new ResponseData();
        try {
            responseData = objectMapper.readValue(data, ResponseData.class);
        } catch (IOException e) {
            log.debug("Mapping of response data failed: {}", e.getMessage());
        }
        return responseData;
    }
}
