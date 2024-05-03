package kr.co.tbell.mm.advice;

import io.jsonwebtoken.ExpiredJwtException;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("[handleValidationErrors]: Error: ", ex);

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errorMessages = fieldErrors
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(
                new Response<>(false, errorMessages.toString(), null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Void>> handleJsonParseErrors(HttpMessageNotReadableException ex) {
        log.error("[handleJsonParseErrors]: Error: ", ex);

        return new ResponseEntity<>(
                new Response<>(false, ex.getMessage(), null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Response<?>> handleExpiredJwtException(ExpiredJwtException ex) {

        log.error("[handleExpiredJwtException]: Error: ", ex);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new Response<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Response<?>> handleInvalidTokenException(InvalidTokenException ex) {
        log.error("[handleInvalidTokenException]: Error: ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Response<?>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error("[handleUserAlreadyExistsException]: Error: ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(InstanceCreationAlreadyExistsException.class)
    public ResponseEntity<Response<?>> handleInstanceCreationAlreadyExistsException(
            InstanceCreationAlreadyExistsException ex) {
        log.error("[handleInstanceCreationAlreadyExistsException]: Error: ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(InstanceDoesNotExistException.class)
    public ResponseEntity<Response<?>> handleInstanceDeleteNotFoundException(InstanceDoesNotExistException ex) {
        log.error("[handleInstanceDeleteNotFoundException]: Error: ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Response<?>> handleInvalidDataException(InvalidDataException ex) {
        log.error("[handleInvalidDataException]: Error: ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(false, ex.getMessage(), null));
    }
}
