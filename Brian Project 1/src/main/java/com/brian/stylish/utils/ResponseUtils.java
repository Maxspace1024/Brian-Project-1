package com.brian.stylish.utils;

import com.brian.stylish.dto.MessageWrapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {
    public ResponseEntity<Object> responseData(HttpStatusCode code, Object data) {
        return ResponseEntity.status(code).body(
            MessageWrapper.builder()
                .data(data)
                .build()
        );
    }

    public ResponseEntity<Object> responseError(HttpStatusCode code, String message) {
        return ResponseEntity.status(code).body(
            MessageWrapper.builder()
                .error(message)
                .build()
        );
    }
}
