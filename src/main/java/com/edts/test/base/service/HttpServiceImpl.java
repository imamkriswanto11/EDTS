package com.edts.test.base.service;

import com.edts.test.base.model.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HttpServiceImpl implements HttpService {

    @Override
    public ResponseEntity<String> getSuccessResponse(String message) {
        return new ResponseEntity<String>(new ResponseDto<String>(message).toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getErrorResponse(String code, String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>(new ResponseDto<String>(code, message).toString(), httpStatus);
    }

    @Override
    public ResponseEntity<String> getErrorResponse(String code, String message) {
        return new ResponseEntity<String>(new ResponseDto<String>(code, message).toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getResponseEntity(ResponseDto<?> response) {
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

}
