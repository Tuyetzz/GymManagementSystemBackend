package com.example.gympool.service.impl;

import com.example.gympool.entity.Coupon;
import com.example.gympool.entity.IssuedCoupon;
import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.repository.CouponRepository;
import com.example.gympool.repository.IssuedCouponRepository;
import com.example.gympool.repository.CustomerMembershipRepository;
import com.example.gympool.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final CustomerMembershipRepository customerMembershipRepository;

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public void createCouponAndIssueToMembers(Coupon couponRequest) {
        // 1. Lưu coupon
        Coupon savedCoupon = couponRepository.save(couponRequest);

        // 2. Tìm tất cả CustomerMembership có tier.name = coupon.scope
        List<CustomerMembership> matchedMemberships =
                customerMembershipRepository.findByMembershipPlan_MembershipTier_Name(couponRequest.getScope());

        // 3. Với mỗi member, tạo IssuedCoupon
        for (CustomerMembership cm : matchedMemberships) {
            IssuedCoupon issued = new IssuedCoupon();
            issued.setCoupon(savedCoupon);
            issued.setMember(cm.getMember());
            issued.setRemainingUses(3); // ví dụ mặc định 3 lần
            issued.setStatus("AVAILABLE");
            issuedCouponRepository.save(issued);
        }
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

    @Override
    public List<IssuedCoupon> getIssuedCouponsByCouponId(Coupon coupon) {
        return issuedCouponRepository.findByCoupon(coupon);
    }
}
