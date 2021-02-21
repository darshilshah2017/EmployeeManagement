package com.infy.exception;

import com.infy.dto.ErrorInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfoDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        logger.error("MethodArgumentNotValidException occurred with message:{} and stackTrace:{}",e.getMessage(),e.getStackTrace());
        ErrorInfoDTO errorInfo = new ErrorInfoDTO();
        errorInfo.setCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setMessage(e.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.joining(",")));
        logger.error("MethodArgumentNotValidException response:{}",errorInfo);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfoDTO> constraintViolationExceptionHandler(ConstraintViolationException e){
        logger.error("ConstraintViolationException occurred with message:{} and stackTrace:{}",e.getMessage(),e.getStackTrace());
        ErrorInfoDTO errorInfo = new ErrorInfoDTO();
        errorInfo.setCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setMessage(e.getConstraintViolations().stream().map(x->x.getMessage()).collect(Collectors.joining(",")));
        logger.error("ConstraintViolationException response:{}",errorInfo);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfoDTO> generalExceptionHandler(Exception e){
        logger.error("Exception occurred with message:{} and stackTrace:{}",e.getMessage(),e.getStackTrace());
        ErrorInfoDTO errorInfo = new ErrorInfoDTO();
        errorInfo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.setMessage(e.getMessage());
        logger.error("ConstraintViolationException response:{}",errorInfo);
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
