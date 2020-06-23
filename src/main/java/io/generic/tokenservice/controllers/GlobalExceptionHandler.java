package io.generic.tokenservice.controllers;

import io.generic.tokenservice.exceptions.TokenGenerationException;
import io.generic.tokenservice.model.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.UnexpectedTypeException;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private String exceptionMsg = "Request failure due to Exception {} : {} occured.";

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {
        LOGGER.error("IOException handler executed");
    }

    @ExceptionHandler({TokenGenerationException.class})
    public final ResponseEntity<ErrorDetails> handleTokenExceptions(TokenGenerationException ex, WebRequest request) {
        LOGGER.error(exceptionMsg, ex.getClass(), ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, UnexpectedTypeException.class})
    public ResponseEntity<ErrorDetails> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        LOGGER.error(exceptionMsg, ex.getClass(), ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(
                request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

}