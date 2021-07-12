package co.com.ml.challenge.favorite.items.coupon.web.controller.handler.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Object> erroresControlados(RestException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getApiError(),
                new HttpHeaders(), ex.getApiError().getStatus(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> erroresNoControlados(Exception ex, WebRequest request) {
        log.error("Excepción no controlada --------->", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Error en el servidor");
        return handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
