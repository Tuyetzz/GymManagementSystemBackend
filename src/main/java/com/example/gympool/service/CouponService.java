package com.example.gympool.service;

import com.example.gympool.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    List<Coupon> getAllCoupons();
    Optional<Coupon> getCouponById(Long id);
    Optional<Coupon> getCouponByCode(String code);
    Coupon createCoupon(Coupon coupon);
    Coupon updateCoupon(Long id, Coupon coupon);
    void deleteCoupon(Long id);
}
