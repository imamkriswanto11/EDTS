package com.edts.test.base.service;


import com.edts.test.base.model.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface HttpService {

    public ResponseEntity<String> getSuccessResponse(String message);

    public ResponseEntity<String> getErrorResponse(String code, String message, HttpStatus httpStatus);

    public ResponseEntity<String> getErrorResponse(String code, String message);

    public ResponseEntity<String> getResponseEntity(ResponseDto<?> response);

}

