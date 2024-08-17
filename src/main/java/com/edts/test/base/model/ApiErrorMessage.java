package com.edts.test.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class ApiErrorMessage {

    private UUID id = UUID.randomUUID();
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;


    public ApiErrorMessage(int value, String name, String message, String path) {
    }
}
