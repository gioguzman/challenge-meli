package co.com.ml.challenge.favorite.items.coupon.web.controller;

import co.com.ml.challenge.favorite.items.coupon.business.CouponBusiness;
import co.com.ml.challenge.favorite.items.coupon.model.exposed.request.CouponRequest;
import co.com.ml.challenge.favorite.items.coupon.model.exposed.response.CouponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CouponController {

    @Autowired
    private CouponBusiness couponBusiness;

    @PostMapping("coupon/")
    public ResponseEntity<CouponResponse> calculateCoupon (@RequestBody CouponRequest couponRequest){
        return ResponseEntity.ok().body(couponBusiness.calculateCoupon(couponRequest));
    }
}
