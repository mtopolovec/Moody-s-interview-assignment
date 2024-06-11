package com.example.assignment.controller;

import com.example.assignment.model.RequestData;
import com.example.assignment.model.ResponseData;
import com.example.assignment.service.FetchData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/data")
public class DataApi {

    private final FetchData queryData;

    public DataApi(FetchData fetchData) {
        this.queryData = fetchData;
    }

    @PostMapping
    public ResponseEntity<ResponseData> getData(@Valid @RequestBody final RequestData requestData) {
        log.debug("Get data for next properties: {}", requestData);
        return new ResponseEntity<>(queryData.fetchData(requestData), HttpStatus.OK);
    }
}
