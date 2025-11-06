package com.example.gympool.service;

import com.example.gympool.entity.Coupon;
import com.example.gympool.entity.IssuedCoupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    List<Coupon> getAllCoupons();
    Optional<Coupon> getCouponById(Long id);
    Coupon updateCoupon(Long id, Coupon coupon);
    void deleteCoupon(Long id);

    List<IssuedCoupon> getIssuedCouponsByCouponId(Coupon coupon);
    void createCouponAndIssueToMembers(Coupon coupon);
}
