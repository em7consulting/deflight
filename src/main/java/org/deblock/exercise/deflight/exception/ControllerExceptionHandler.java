package org.deblock.exercise.deflight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiError> badRequest(BadRequestException ex) {
        final var apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
