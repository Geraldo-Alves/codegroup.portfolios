package com.codegroup.portfolios.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codegroup.portfolios.dto.Response;

public class ResponseUtil {

    public Response success(Object data) {
        return new Response("Success", data);
    }

    public static Response error(String message) {
        return new Response("Error", message);
    }

    public static ResponseEntity<Response> created(Object data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Created", data));
    }

}
