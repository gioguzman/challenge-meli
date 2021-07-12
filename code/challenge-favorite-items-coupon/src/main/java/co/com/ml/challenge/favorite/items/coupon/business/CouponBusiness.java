package co.com.ml.challenge.favorite.items.coupon.business;

import co.com.ml.challenge.favorite.items.coupon.busicapa.ItemRestClient;
import co.com.ml.challenge.favorite.items.coupon.model.exposed.request.CouponRequest;
import co.com.ml.challenge.favorite.items.coupon.model.exposed.response.CouponResponse;
import co.com.ml.challenge.favorite.items.coupon.web.controller.handler.error.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CouponBusiness {

    @Autowired
    private ItemRestClient itemRestClient;

    public CouponResponse calculateCoupon(CouponRequest couponRequest) {

        List<String> items = couponRequest.getItem_ids();
        Map<String, Float> mapItems = new HashMap<>();

        items.forEach((item) -> {
            mapItems.put(item, itemRestClient.getItemDetail(item).getPrice());
        });

        return calculate(mapItems, couponRequest.getAmount());

    }

    private CouponResponse calculate (Map<String, Float> items, Float amount) {

        Map<String, Float> sortedMap =  items.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError();},
                        LinkedHashMap::new
                ));

        Float amountList = Float.valueOf(0);
        List<String> listItems = new ArrayList<>();
        for (Map.Entry<String, Float> mapItems : sortedMap.entrySet()){
            if (Float.sum(amountList,mapItems.getValue()) <= amount){
                listItems.add(mapItems.getKey());
                amountList += mapItems.getValue();
            }
        }

        if (amountList == 0){
            throw new RestException(HttpStatus.NOT_FOUND,"No se encontraron productos con un valor valido para el bono");
        }

        CouponResponse couponResponse = new CouponResponse();
        couponResponse.setItem_ids(listItems);
        couponResponse.setTotal(amountList);

        return couponResponse;
    }
}
