package pl.estrix.zwroty.textextractor.adapters.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.estrix.zwroty.textextractor.common.application.exceptions.ErrorResponse;
import pl.estrix.zwroty.textextractor.application.exception.RequestNotFoundException;
import pl.estrix.zwroty.textextractor.domain.exception.TextextractorDomainException;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {TextextractorDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(TextextractorDomainException orderDomainException) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), orderDomainException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {RequestNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(RequestNotFoundException orderNotFoundException) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), orderNotFoundException.getMessage());
    }
}
