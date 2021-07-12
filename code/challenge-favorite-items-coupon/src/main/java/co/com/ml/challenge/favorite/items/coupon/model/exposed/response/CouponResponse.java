package co.com.ml.challenge.favorite.items.coupon.model.exposed.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponResponse {

    private List<String> item_ids;
    private float total;
}
