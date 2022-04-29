package com.meli.mutant.finder.infraestructure.diven_adapters.exception_manager;

import com.meli.mutant.finder.domain.model.util.exception.MutantFinderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MutantExceptionHandler {
    @ExceptionHandler(value = {MutantFinderException.class})
    public ResponseEntity<Object> handleApiRequestException(MutantFinderException apiException) {
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
