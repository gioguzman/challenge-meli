package co.com.ml.challenge.favorite.items.coupon.web.controller.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RestException extends RuntimeException {

    ApiError apiError;

    public RestException(HttpStatus status, String mensaje) {
        super(mensaje);
        apiError = new ApiError(status, mensaje);
        apiError.setTimestamp(LocalDateTime.now());
    }

    public RestException(HttpStatus status, String mensaje, List errors) {
        super(mensaje);
        apiError = new ApiError(status, mensaje);
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setErrors(errors);
    }
}