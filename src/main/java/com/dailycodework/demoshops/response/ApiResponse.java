package com.dailycodework.demoshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class ApiResponse {

    private String message;
    private Object data;

    public ApiResponse(String message, Object data)
    {
        this.message = message;
        this.data = data;
    }

}
// This class is used to what we are going to return the data;
// to our frontend;
