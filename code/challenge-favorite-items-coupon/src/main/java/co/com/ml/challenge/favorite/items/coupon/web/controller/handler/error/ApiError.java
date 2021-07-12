package co.com.ml.challenge.favorite.items.coupon.web.controller.handler.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError extends Error {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("errors")
    private List errors;

    public ApiError(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiError(HttpStatus status, String code, String message) {
        super(code, message);
        this.status = status;
    }
}
