package co.com.ml.challenge.favorite.items.coupon.business;

import co.com.ml.challenge.favorite.items.coupon.busicapa.ItemRestClient;
import co.com.ml.challenge.favorite.items.coupon.model.consumer.response.ItemResponse;
import co.com.ml.challenge.favorite.items.coupon.model.exposed.request.CouponRequest;
import co.com.ml.challenge.favorite.items.coupon.model.exposed.response.CouponResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CouponBusinessTest {

    @Mock
    private ItemRestClient itemRestClient;

    @InjectMocks
    private CouponBusiness couponBusiness;


    @Test
    public void getCalculateCouponOK() {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId("MLA651394869");
        itemResponse.setSite_id("MLA");
        itemResponse.setTitle("Cubierta 140 60 17 Trasera Yamaha Fz 16 Fi 2.0 Gixxer  Fas");
        itemResponse.setSubtitle(null);
        itemResponse.setSeller_id(89575112);
        itemResponse.setCategory_id("MLA45529");
        itemResponse.setOfficial_store_id(89575112);
        itemResponse.setPrice((float) 8599);

        CouponRequest couponRequest = new CouponRequest();
        List<String> listItems = new ArrayList<>();
        listItems.add("MLA923198114");
        listItems.add("MLA868907697");
        listItems.add("MLA884534125");
        listItems.add("MLA651394869");
        couponRequest.setItem_ids(listItems);
        couponRequest.setAmount((float) 8599);

        
        //Given
        when(itemRestClient.getItemDetail(anyString())).thenReturn(itemResponse);

        //When
        CouponResponse couponResponse = couponBusiness.calculateCoupon(couponRequest);

        //Then
        assertNotNull(couponResponse.getItem_ids());

    }
}