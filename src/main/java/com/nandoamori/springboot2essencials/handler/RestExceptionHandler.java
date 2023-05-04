package com.nandoamori.springboot2essencials.handler;

import com.nandoamori.springboot2essencials.exception.BadRequestException;
import com.nandoamori.springboot2essencials.exception.BadRequestExceptionDetails;
import com.nandoamori.springboot2essencials.exception.ValidationExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
        return new ResponseEntity<>(
          BadRequestExceptionDetails.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .title("Bad Request Exception, check the documentation.")
                  .details(bre.getMessage())
                  .developerMessage(bre.getClass().getName())
                  .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException
            (MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .title("Bad Request Exception, invalid fields.")
                  .details("Check the field(s) error.")
                  .developerMessage(exception.getClass().getName())
                  .fields(fields)
                  .fieldsMessage(fieldsMessage)
                  .build(), HttpStatus.BAD_REQUEST);
    }
}
