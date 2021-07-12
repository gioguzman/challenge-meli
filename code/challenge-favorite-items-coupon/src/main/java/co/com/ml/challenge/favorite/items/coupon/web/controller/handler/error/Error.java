package co.com.ml.challenge.favorite.items.coupon.web.controller.handler.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Error {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    @NonNull
    private String message;


}

