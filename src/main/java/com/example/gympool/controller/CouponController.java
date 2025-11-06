package com.example.gympool.controller;

import com.example.gympool.entity.Coupon;
import com.example.gympool.entity.IssuedCoupon;
import com.example.gympool.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCouponDetail(@PathVariable Long id) {
        Optional<Coupon> couponOpt = couponService.getCouponById(id);

        if (couponOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy coupon với id: " + id);
        }

        Coupon coupon = couponOpt.get();
        List<IssuedCoupon> issuedList = couponService.getIssuedCouponsByCouponId(coupon);

        Map<String, Object> response = new HashMap<>();
        response.put("coupon", coupon);
        response.put("issuedCoupons", issuedList);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createCoupon(@RequestBody Coupon couponRequest) {
        try {
            couponService.createCouponAndIssueToMembers(couponRequest);
            return ResponseEntity.ok("Coupon created and issued successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        return ResponseEntity.ok(updatedCoupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
