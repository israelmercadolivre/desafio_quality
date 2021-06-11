package br.com.mercadolivre.challenge_quality.handler;

import br.com.mercadolivre.challenge_quality.exception.DistrictNotFoundException;
import br.com.mercadolivre.challenge_quality.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class PropertyHandler {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> getInvalidValues(MethodArgumentNotValidException e, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorMessage messageError = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                LocalDate.now(),
                errors,
                request.getDescription(false));
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DistrictNotFoundException.class})
    public ResponseEntity<ErrorMessage> getDistrictNotFound(DistrictNotFoundException e, WebRequest request) {
        ErrorMessage messageError = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                LocalDate.now(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }
}
