package com.mq.task.controller;
import com.mq.task.dto.ErrorResponse;
import com.mq.task.dto.ValidationErrorResponse;
import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice(assignableTypes = MqController.class)
public class MqControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        Sentry.captureException(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Server Error"));
    }

    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseEntity<?> handleConstraintViolation(Exception ex, WebRequest request) {
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
           List<String> messages = constraintViolations.stream().map(each -> each.getMessage()).collect(Collectors.toList());
            ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("validation error",messages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorResponse);

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Server Error"));

    }

}
