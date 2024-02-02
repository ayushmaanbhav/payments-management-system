package com.ayushmaanbhav.commonsspring.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.exception.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestAppControllerAdvice {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorData handleUnknownException(HttpServletRequest req, Throwable t) {
        t.printStackTrace();
        ErrorData data = new ErrorData();
        data.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
        data.setMessage("Internal error");
        return data;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorData handleMissingRequestBody(HttpMessageNotReadableException ex) {
        ErrorData data = new ErrorData();
        data.setCode(ErrorCode.BAD_REQUEST);
        data.setMessage(ex.getMostSpecificCause().getMessage());
        return data;
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ErrorData handleApiException(HttpServletRequest req, ApiException e, HttpServletResponse response) {
        e.printStackTrace();
        setResponseStatus(response, e.getErrorCode());
        ErrorData data = new ErrorData();
        data.setCode(e.getErrorCode());
        data.setMessage(e.getMessage());
        return data;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorData handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletResponse response) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().stream().findAny().get();
        ErrorData data = new ErrorData();
        data.setCode(ErrorCode.BAD_REQUEST);
        data.setMessage(fieldError.getDefaultMessage());
        setResponseStatus(response, data.getCode());
        return data;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorData handleConstraintViolation(ConstraintViolationException e, HttpServletResponse response) {
        ErrorData data = new ErrorData();
        data.setCode(ErrorCode.BAD_REQUEST);
        data.setMessage(e.getMessage());
        setResponseStatus(response, data.getCode());
        return data;
    }

    private void setResponseStatus(HttpServletResponse response, ErrorCode errorCode) {
        switch (errorCode) {
            case UNAUTHORISED:
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                break;
            case CONFLICT:
                response.setStatus(HttpStatus.CONFLICT.value());
                break;
            case NOT_FOUND:
                response.setStatus(HttpStatus.NOT_FOUND.value());
                break;
            case BAD_REQUEST:
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                break;
            case TOO_MANY_REQUESTS:
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            case INTERNAL_SERVER_ERROR:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            default:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}
