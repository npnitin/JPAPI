package com.example.web;

import com.example.exceptions.ExceptionResponse;
import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(UserAlreadyExistsWithEmail.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody
    ExceptionResponse handleDuplicateEmail(final UserAlreadyExistsWithEmail exception,
                                             final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());
        return error;
    }

    @ExceptionHandler(InvalidCredentials.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ExceptionResponse handleInvalidCredentials(final InvalidCredentials exception,
                                             final HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());
        return error;
    }
}
