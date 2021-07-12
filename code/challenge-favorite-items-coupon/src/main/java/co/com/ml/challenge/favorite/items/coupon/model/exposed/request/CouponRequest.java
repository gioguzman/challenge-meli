package co.com.ml.challenge.favorite.items.coupon.model.exposed.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponRequest {

    private List<String> item_ids;
    private float amount;
}
