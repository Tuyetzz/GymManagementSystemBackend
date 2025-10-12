package com.example.gympool.service.impl;

import com.example.gympool.entity.Coupon;
import com.example.gympool.repository.CouponRepository;
import com.example.gympool.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findByCode(code);
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        if (couponRepository.existsByCode(coupon.getCode())) {
            throw new RuntimeException("Mã coupon đã tồn tại");
        }
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Long id, Coupon updatedCoupon) {
        return couponRepository.findById(id).map(coupon -> {
            coupon.setCode(updatedCoupon.getCode());
            coupon.setDiscountType(updatedCoupon.getDiscountType());
            coupon.setDiscountValue(updatedCoupon.getDiscountValue());
            coupon.setStartDate(updatedCoupon.getStartDate());
            coupon.setEndDate(updatedCoupon.getEndDate());
            coupon.setStatus(updatedCoupon.getStatus());
            coupon.setScope(updatedCoupon.getScope());
            return couponRepository.save(coupon);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy coupon với id " + id));
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}
