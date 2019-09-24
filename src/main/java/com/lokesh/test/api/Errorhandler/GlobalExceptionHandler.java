package com.lokesh.test.api.Errorhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lokesh.test.Exception.UserNotFoundException;
import com.lokesh.test.model.ErrorResponse;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice(basePackages = {"com.lokesh.test"})
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidFormatException.class,
            DateTimeParseException.class,
            HttpMessageNotReadableException.class})
    public ErrorResponse handleBadRequestException(Exception ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    public ErrorResponse handleNotFoundException(UserNotFoundException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleInternalServerException(Exception ex) {
        return createErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ErrorResponse createErrorResponse(
            HttpStatus httpStatus, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setStatusCode(httpStatus.value());
        return errorResponse;
    }
}