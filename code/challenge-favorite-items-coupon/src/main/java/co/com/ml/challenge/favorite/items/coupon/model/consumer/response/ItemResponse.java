package co.com.ml.challenge.favorite.items.coupon.model.consumer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponse {

    private String id;
    private String site_id;
    private String title;
    private String subtitle;
    private double seller_id;
    private String category_id;
    private double official_store_id;
    private Float price;
}
