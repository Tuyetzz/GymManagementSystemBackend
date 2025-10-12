package com.example.gympool.service.impl;

import com.example.gympool.entity.Coupon;
import com.example.gympool.entity.IssuedCoupon;
import com.example.gympool.entity.Member;
import com.example.gympool.repository.CouponRepository;
import com.example.gympool.repository.IssuedCouponRepository;
import com.example.gympool.repository.MemberRepository;
import com.example.gympool.service.IssuedCouponService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IssuedCouponServiceImpl implements IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public IssuedCouponServiceImpl(IssuedCouponRepository issuedCouponRepository,
                                   MemberRepository memberRepository,
                                   CouponRepository couponRepository) {
        this.issuedCouponRepository = issuedCouponRepository;
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
    }

    @Override
    public List<IssuedCoupon> getAllIssuedCoupons() {
        return issuedCouponRepository.findAll();
    }

    @Override
    public List<IssuedCoupon> getIssuedCouponsByMember(Member member) {
        return issuedCouponRepository.findByMember(member);
    }

    @Override
    public List<IssuedCoupon> getIssuedCouponsByCoupon(Coupon coupon) {
        return issuedCouponRepository.findByCoupon(coupon);
    }

    @Override
    public Optional<IssuedCoupon> getByMemberAndCoupon(Member member, Coupon coupon) {
        return issuedCouponRepository.findByMemberAndCoupon(member, coupon);
    }

    @Override
    public IssuedCoupon issueCouponToMember(IssuedCoupon issuedCoupon) {
        if (issuedCoupon == null
                || issuedCoupon.getCoupon() == null
                || issuedCoupon.getCoupon().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "coupon.id is required");
        }
        if (issuedCoupon.getMember() == null || issuedCoupon.getMember().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "member.id is required");
        }
        // load coupon
        Coupon coupon = couponRepository.findById(issuedCoupon.getCoupon().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon does not exist"));
        if (!"ACTIVE".equalsIgnoreCase(coupon.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon is not active");
        }
        Date now = new Date();
        if (coupon.getStartDate() != null && now.before(coupon.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon not yet effective");
        }
        if (coupon.getEndDate() != null && now.after(coupon.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon expired");
        }

        // load member
        Member member = memberRepository.findById(issuedCoupon.getMember().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member does not exist"));

        Optional<IssuedCoupon> existing = issuedCouponRepository.findByMemberAndCoupon(member, coupon);
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Member already has this coupon");
        }

        if (issuedCoupon.getRemainingUses() == null) {
            issuedCoupon.setRemainingUses(1);
        }

        issuedCoupon.setCoupon(coupon);
        issuedCoupon.setMember(member);
        issuedCoupon.setStatus("AVAILABLE");
        issuedCoupon.setRemainingUses(coupon.getTotalUses());
        return issuedCouponRepository.save(issuedCoupon);
    }

    @Override
    public void deleteIssuedCoupon(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        if (!issuedCouponRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IssuedCoupon not found");
        }
        issuedCouponRepository.deleteById(id);
    }
}
