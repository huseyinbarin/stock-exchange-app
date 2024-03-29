package com.barin.stockexchangeapplication.adapter.rest.exception.handler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@JsonDeserialize
public class ErrorBody {
    HttpStatus status;
    String error;
    String message;

    public ResponseEntity<ErrorBody> toResponseBody() {
        return new ResponseEntity<>(this, this.status);
    }
}
