package com.barin.stockexchangeapplication.adapter.rest.exception.handler;

import com.barin.stockexchangeapplication.domain.process.exception.StockException;
import com.barin.stockexchangeapplication.domain.process.exception.StockExchangeNotFoundException;
import com.barin.stockexchangeapplication.domain.process.exception.StockNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static ErrorBody getErrorBody(Exception e, HttpStatus status) {
        return ErrorBody.builder().status(status).error(e.getCause() == null ? e.getClass().getSimpleName() : e.getCause().getClass().getSimpleName()).message(e.getCause() == null ? e.getMessage() : e.getCause().getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorBody> handle(final RuntimeException e) {
        ErrorBody errorBody = getErrorBody(e, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Cannot process request, Reason : " + e.getMessage(), e);
        return errorBody.toResponseBody();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorBody> handle(final IOException e) {
        ErrorBody errorBody = getErrorBody(e, HttpStatus.FAILED_DEPENDENCY);
        log.warn("Cannot process request. Reason: " + e.getCause());
        return errorBody.toResponseBody();
    }

    @ExceptionHandler({JsonProcessingException.class})
    public ResponseEntity<ErrorBody> handle(final JsonProcessingException e) {
        ErrorBody errorBody = getErrorBody(e, HttpStatus.BAD_REQUEST);
        log.error("Cannot process request, invalid data format, Reason : " + e.getMessage(), e);
        return errorBody.toResponseBody();
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorBody> handle(final ConstraintViolationException e) {
        ErrorBody errorBody = getErrorBody(e, HttpStatus.BAD_REQUEST);
        log.error("Cannot request event, invalid data format, Reason : " + e.getMessage(), e);
        return errorBody.toResponseBody();
    }

    @ExceptionHandler({StockNotFoundException.class, StockExchangeNotFoundException.class})
    public ResponseEntity<ErrorBody> handleBusinessException(final StockException e) {
        ErrorBody errorBody = getErrorBody(e, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(e.getMessage());
        return errorBody.toResponseBody();
    }

}
