package com.example.gympool.service;

import com.example.gympool.entity.Coupon;
import com.example.gympool.entity.IssuedCoupon;
import com.example.gympool.entity.Member;

import java.util.List;
import java.util.Optional;

public interface IssuedCouponService {
    List<IssuedCoupon> getAllIssuedCoupons();
    List<IssuedCoupon> getIssuedCouponsByMember(Member member);
    List<IssuedCoupon> getIssuedCouponsByCoupon(Coupon coupon);
    Optional<IssuedCoupon> getByMemberAndCoupon(Member member, Coupon coupon);
    IssuedCoupon issueCouponToMember(IssuedCoupon coupon);
    void deleteIssuedCoupon(Long id);
}
