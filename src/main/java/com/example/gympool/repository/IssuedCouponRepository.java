package com.example.gympool.repository;

import com.example.gympool.entity.Coupon;
import com.example.gympool.entity.IssuedCoupon;
import com.example.gympool.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {
    List<IssuedCoupon> findByMember(Member member);
    List<IssuedCoupon> findByCoupon(Coupon coupon);
    Optional<IssuedCoupon> findByMemberAndCoupon(Member member, Coupon coupon);
    Optional<IssuedCoupon> findByCouponIdAndMemberIdAndStatus(Long couponId, Long memberId, String status);
}