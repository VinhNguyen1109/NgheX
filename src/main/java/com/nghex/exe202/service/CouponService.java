package com.nghex.exe202.service;


import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.Coupon;
import com.nghex.exe202.entity.User;

import java.util.List;

public interface CouponService {
    Cart applyCoupon(String code, double orderValue, User user) throws Exception;
    Cart removeCoupon(String code, User user) throws Exception;
    Coupon createCoupon(Coupon coupon);
    void deleteCoupon(Long couponId);
    List<Coupon> getAllCoupons();
    Coupon getCouponById(Long couponId);
}
