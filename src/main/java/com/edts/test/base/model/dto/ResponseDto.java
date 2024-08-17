package com.edts.test.base.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ResponseDto<T> {

    @JsonIgnore
    private ObjectMapper objectMapper = new ObjectMapper();
    private boolean success = false;
    private String message;
    private Pagedata pagedata;
    private List<T> data = new ArrayList<T>();
    private Error error;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z", timezone="UTC")
    private Date timestamp;

    public ResponseDto() {
        this.success = true;
    }

    public ResponseDto(String message) {
        this.success = true;
        this.message = message;
    }

    public ResponseDto(String message, List<T> data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public ResponseDto(List<T> data) {
        this.success = true;
        this.data = data;
    }

    public ResponseDto(String errorCode, String errorMessage) {
        this.success = false;
        this.error = new Error(errorCode, errorMessage);
    }

    @Getter
    @Setter
    public static class Pagedata {

        private int totalCount = 0;
        private int currentPage = 1;
        private int pageCount = 1;
        private int pageSize = 1;

        public Pagedata(
                @JsonProperty("totalCount") int totalCount,
                @JsonProperty("currentPage") int currentPage,
                @JsonProperty("pageCount") int pageCount,
                @JsonProperty("pageSize") int pageSize) {
            this.totalCount = totalCount;
            this.currentPage = currentPage;
            this.pageCount = pageCount;
            this.pageSize = pageSize;
        }

    }

    @Getter
    @Setter
    public static class Error {

        private String code;
        private String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    public void setPagedata(int totalCount, int currentPage, int pageCount, int pageSize) {
        this.pagedata = new Pagedata(totalCount, currentPage, pageCount, pageSize);
    }

    public List<T> getData() {
        if(this.data!=null && this.data.isEmpty()) {
            return new ArrayList<>();
        }
        return this.data;
    }

    public Date getTimestamp() {
        return new Date();
    }

    public void addData(T data) {
        this.data.add(data);
    }

    public String toString() {
        String json = "{\"success\":false,\"error\":{\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Internal Server Error\"}}";
        objectMapper.registerModule(new JavaTimeModule());
        try {
            json = objectMapper.writeValueAsString(this);
        } catch(Exception e) {
            log.error("Error when parsing response model.", e);
        }
        return json;
    }

}
