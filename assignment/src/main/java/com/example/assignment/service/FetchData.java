package com.example.assignment.service;

import com.example.assignment.model.RequestData;
import com.example.assignment.model.ResponseData;

public interface FetchData {
    ResponseData fetchData(RequestData requestData);
}
