package com.codegroup.portfolios.utils;

import com.codegroup.portfolios.dto.Response;

public class ResponseUtil {
    
    public Response success(Object data) {
        return new Response("Success", data);
    }

    public static Response error(String message) {
        return new Response("Error", message);
    }

}
