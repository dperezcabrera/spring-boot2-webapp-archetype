#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.controllers;

import ${package}.architecture.dtos.MessageDto;
import ${package}.architecture.exceptions.DeniedPermissionException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {DeniedPermissionException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageDto deniedPermissionException(DeniedPermissionException ex) {
        return new MessageDto("Denied permission");
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto constraintViolationException(ConstraintViolationException ex) {
        return new MessageDto("Bad request");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageDto internarServerError(Exception ex) {
        return new MessageDto("Internal error");
    }
}
