package com.microservice.pruebatecnica.handler;

import com.microservice.pruebatecnica.model.dtos.ErrorResponse;
import com.microservice.pruebatecnica.model.dtos.ErrorsResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.net.ssl.SSLException;
import java.util.ArrayList;

/**
 * The GlobalErrorWebExceptionHandler.
 * <p>
 * Handle the differents Errors.
 * <p>
 * handle methods:
 * - handleException                       -->    handle INTERNAL_SERVER_ERROR
 * - handleHttpMessageNotReadableException -->    handle MissingServletRequestParameterExceptions
 * - handleHttpMessageNotReadableException -->    handle HttpMessageNotReadableException
 */
@ControllerAdvice @ResponseBody @Log4j2 public class GlobalErrorWebExceptionHandler {

    /**
     * The constant LEVEL_EXCEPTION.
     */
    // Any exception not controlled
    private static final String LEVEL_EXCEPTION = "EXCEPTION";
    /**
     * The constant LEVEL_ERROR.
     */
    // Errors produces by backends or any consumers
    private static final String LEVEL_ERROR = "ERROR";

    /**
     * The constant MESSAGE.
     */
    private static final String MESSAGE = "Ops! Somethig was wrong :-(\n Message: , {}";
    /**
     * The constant CAUSE.
     */
    private static final String CAUSE = "Cause: , {}";

    /**
     * The A.
     */
    private final ArrayList<ErrorResponse> a = new ArrayList<>();

    /**
     * System exception Expected exceptions
     *
     * @param ex handleException
     * @return ErrorsResponse. errors response
     */
    @ExceptionHandler(SSLException.class) @ResponseStatus(value = HttpStatus.BAD_REQUEST) public ErrorsResponse handleException (
            SSLException ex) {
        log.error(MESSAGE, ex.getMessage());
        log.error(CAUSE, ex.getCause());

        a.clear();
        a.add(createErrorResponse(ex, HttpStatus.BAD_REQUEST, LEVEL_ERROR));

        return new ErrorsResponse(a);
    }

    /**
     * System exception Expected exceptions
     *
     * @param ex handleException
     * @return ErrorsResponse. errors response
     */
    @ExceptionHandler(Exception.class) @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) public ErrorsResponse handleException (
            Exception ex) {
        log.error(MESSAGE, ex.getMessage());
        log.error(CAUSE, ex.getCause());
        a.clear();
        a.add(createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, LEVEL_EXCEPTION));

        return new ErrorsResponse(a);
    }

    /**
     * Missing request message exception
     *
     * @param ex HttpMessageNotReadableException
     * @return ErrorsResponse errors response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class) @ResponseStatus(value = HttpStatus.BAD_REQUEST) public ErrorsResponse handleHttpMessageNotReadableException (
            HttpMessageNotReadableException ex) {
        log.error(MESSAGE, ex.getMessage());
        log.error(CAUSE, ex.getCause());
        a.clear();

        a.add(createErrorResponse(ex, HttpStatus.BAD_REQUEST, LEVEL_ERROR));

        return new ErrorsResponse(a);
    }


    /**
     * Create error response error response.
     *
     * @param ex     the ex
     * @param status the status
     * @param level  the level
     * @return the error response
     */
    private ErrorResponse createErrorResponse (Exception ex, HttpStatus status, String level) {
        ErrorResponse er = new ErrorResponse();
        er.setCode(status.toString());
        er.setDescription(ex.getMessage());
        er.setMessage(status.getReasonPhrase());
        er.setLevel(level);
        return er;
    }

}
