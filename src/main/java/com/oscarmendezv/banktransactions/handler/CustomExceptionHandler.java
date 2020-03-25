package com.oscarmendezv.banktransactions.handler;

import com.oscarmendezv.banktransactions.handler.exceptions.ChannelException;
import com.oscarmendezv.banktransactions.handler.exceptions.CreationException;
import com.oscarmendezv.banktransactions.handler.exceptions.EntityNotFoundException;
import com.oscarmendezv.banktransactions.web.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleAll(Exception ex) {
    return handleExceptions(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(RuntimeException ex) {
    return handleExceptions(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ChannelException.class)
  public ResponseEntity<ApiError> handleChannelException(RuntimeException ex) {
    return handleExceptions(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CreationException.class)
  public ResponseEntity<ApiError> handleCreationException(RuntimeException ex) {
    return handleExceptions(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<ApiError> handleRollbackException(RuntimeException ex) {
    return handleExceptions(ex.getCause().getCause().getMessage(), HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<ApiError> handleExceptions(String description, HttpStatus status) {
    ApiError error = new ApiError()
        .code(String.valueOf(status.value()))
        .message(status.getReasonPhrase())
        .description(description);

    return new ResponseEntity<>(error, status);
  }
}
