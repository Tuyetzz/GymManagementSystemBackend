package com.example.gympool.service.impl;

import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.service.BillService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final StaffRepository staffRepository;
    private final ReceptionistRepository receptionistRepository;
    private final MemberRepository memberRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    public BillServiceImpl(BillRepository billRepository,
                           ProductRepository productRepository,
                           StaffRepository staffRepository,
                           MemberRepository memberRepository,
                           ReceptionistRepository receptionistRepository,
                           IssuedCouponRepository issuedCouponRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        this.staffRepository = staffRepository;
        this.receptionistRepository = receptionistRepository;
        this.memberRepository = memberRepository;
        this.issuedCouponRepository = issuedCouponRepository;
    }

    @Override
    public Bill createBill(Bill bill) {
        // reception id
        Receptionist receptionist = receptionistRepository.findById(bill.getReceptionist().getId())
                .orElseThrow(() -> new RuntimeException("Receptionist not found"));
        bill.setReceptionist(receptionist);
        // member id
        if (bill.getMember() != null && bill.getMember().getId() != null) {
            Member member = memberRepository.findById(bill.getMember().getId())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            bill.setMember(member);
        } else {
            bill.setMember(null);
        }

        // product id
        List<Long> productIds = bill.getListSoldProduct().stream()
                .map(sp -> sp.getProduct().getId())
                .toList();
        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        bill.getListSoldProduct().forEach(sp -> {
            Product product = productMap.get(sp.getProduct().getId());
            if (product == null) throw new RuntimeException("Product not found");
            sp.setProduct(product);
            sp.setBill(bill);

            int newQuantity = product.getQuantity() - sp.getQuantity();
            if (newQuantity < 0) throw new RuntimeException("Not enough stock for product: " + product.getName());
            product.setQuantity(newQuantity);
            productRepository.save(product);
        });

        // staff id
        if (bill.getListStaffAssigned() != null) {
            bill.getListStaffAssigned().forEach(sa -> {
                Staff staff = staffRepository.findById(sa.getStaff().getId())
                        .orElseThrow(() -> new RuntimeException("Staff not found"));
                sa.setStaff(staff);
                sa.setBill(bill);
            });
        }

        // tong tien tam thoi
        double subtotal = bill.getListSoldProduct().stream()
                .mapToDouble(sp -> sp.getProduct().getPrice() * sp.getQuantity())
                .sum();
        //coupon
        double discount = 0.0;
        if (bill.getIssuedCoupon() != null && bill.getIssuedCoupon().getId() != null) {
            IssuedCoupon issuedCoupon = issuedCouponRepository.findById(bill.getIssuedCoupon().getId())
                    .orElseThrow(() -> new RuntimeException("Issued Coupon not found with ID: " + bill.getIssuedCoupon().getId()));
            //ngoai le
            Coupon coupon = issuedCoupon.getCoupon();
            if (coupon == null) {
                throw new RuntimeException("Coupon template not found for the issued coupon.");
            }
            if (!"AVAILABLE".equalsIgnoreCase(issuedCoupon.getStatus())) {
                throw new RuntimeException("This coupon is no longer available. Status: " + issuedCoupon.getStatus());
            }
            if (issuedCoupon.getRemainingUses() <= 0) {
                throw new RuntimeException("This coupon has no remaining uses.");
            }
            if (!"ACTIVE".equalsIgnoreCase(coupon.getStatus())) {
                throw new RuntimeException("The coupon promotion is no longer active.");
            }
            Date now = new Date();
            if (now.before(coupon.getStartDate()) || now.after(coupon.getEndDate())) {
                throw new RuntimeException("The coupon is not valid at this time.");
            }

            String discountType = coupon.getDiscountType();
            double discountValue = coupon.getDiscountValue();

            if ("PERCENTAGE".equalsIgnoreCase(discountType)) {
                discount = subtotal * (discountValue / 100.0);
            } else if ("FIXED_AMOUNT".equalsIgnoreCase(discountType)) {
                discount = discountValue;
            } else {
                throw new RuntimeException("Unsupported discount type: " + discountType);
            }

            // Tru luot dung
            issuedCoupon.setRemainingUses(issuedCoupon.getRemainingUses() - 1);
            if (issuedCoupon.getRemainingUses() == 0) {
                issuedCoupon.setStatus("USED");
            }
            issuedCouponRepository.save(issuedCoupon);

            bill.setIssuedCoupon(issuedCoupon);
        }

        double finalTotal = subtotal - discount;
        bill.setTotal(Math.max(0, finalTotal)); // Đảm bảo tổng tiền không bị âm

        return billRepository.save(bill);
    }

    @Override
    public Bill updateBill(Long id, Bill billDetails) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));

        //revert-coupon
        if (bill.getIssuedCoupon() != null) {
            IssuedCoupon oldCoupon = issuedCouponRepository.findById(bill.getIssuedCoupon().getId())
                    .orElse(null);
            if (oldCoupon != null) {
                oldCoupon.setRemainingUses(oldCoupon.getRemainingUses() + 1);
                if ("USED".equalsIgnoreCase(oldCoupon.getStatus())) {
                    oldCoupon.setStatus("AVAILABLE");
                }
                issuedCouponRepository.save(oldCoupon);
            }
        }

        //revert-inventory
        bill.getListSoldProduct().forEach(sp -> {
            Product product = sp.getProduct();
            product.setQuantity(product.getQuantity() + sp.getQuantity());
            productRepository.save(product);
        });

        bill.getListSoldProduct().clear();
        bill.getListStaffAssigned().clear();

        //update-info
        bill.setPaymentMethod(billDetails.getPaymentMethod());
        bill.setPaymentStatus(billDetails.getPaymentStatus());
        bill.setDate(billDetails.getDate());

        //member
        if (billDetails.getMember() != null && billDetails.getMember().getId() != null) {
            Member member = memberRepository.findById(billDetails.getMember().getId())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            bill.setMember(member);
        } else {
            bill.setMember(null);
        }

        //product
        if (billDetails.getListSoldProduct() != null) {
            billDetails.getListSoldProduct().forEach(sp -> {
                Product product = productRepository.findById(sp.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                int newQuantity = product.getQuantity() - sp.getQuantity();
                if (newQuantity < 0) throw new RuntimeException("Not enough stock for product: " + product.getName());
                product.setQuantity(newQuantity);
                productRepository.save(product);

                sp.setProduct(product);
                sp.setBill(bill);
                bill.getListSoldProduct().add(sp);
            });
        }

        //staff
        if (billDetails.getListStaffAssigned() != null) {
            billDetails.getListStaffAssigned().forEach(sa -> {
                Staff staff = staffRepository.findById(sa.getStaff().getId())
                        .orElseThrow(() -> new RuntimeException("Staff not found"));
                sa.setStaff(staff);
                sa.setBill(bill);
                bill.getListStaffAssigned().add(sa);
            });
        }

        //total
        double subtotal = bill.getListSoldProduct().stream()
                .mapToDouble(sp -> sp.getProduct().getPrice() * sp.getQuantity())
                .sum();

        //issued coupon
        double discount = 0.0;
        bill.setIssuedCoupon(null);
        if (billDetails.getIssuedCoupon() != null && billDetails.getIssuedCoupon().getId() != null) {
            IssuedCoupon issuedCoupon = issuedCouponRepository.findById(billDetails.getIssuedCoupon().getId())
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));
            if (!"AVAILABLE".equalsIgnoreCase(issuedCoupon.getStatus()) || issuedCoupon.getRemainingUses() <= 0) {
                throw new RuntimeException("Coupon is not valid or has no remaining uses.");
            }
            //coupon
            Coupon coupon = issuedCoupon.getCoupon();
            String discountType = coupon.getDiscountType();
            double discountValue = coupon.getDiscountValue();

            if ("PERCENTAGE".equalsIgnoreCase(discountType)) {
                discount = subtotal * (discountValue / 100.0);
            } else if ("FIXED_AMOUNT".equalsIgnoreCase(discountType)) {
                discount = discountValue;
            } else {
                throw new RuntimeException("Unsupported discount type: " + discountType);
            }

            issuedCoupon.setRemainingUses(issuedCoupon.getRemainingUses() - 1);
            if (issuedCoupon.getRemainingUses() == 0) issuedCoupon.setStatus("USED");
            issuedCouponRepository.save(issuedCoupon);
            bill.setIssuedCoupon(issuedCoupon);
        }

        double finalTotal = subtotal - discount;
        bill.setTotal(Math.max(0, finalTotal));

        return billRepository.save(bill);
    }

    @Override
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
    }

    @Override
    public List<Bill> getBillsByReceptionistId(Long receptionistId) {
        return billRepository.findByReceptionist_Id(receptionistId);
    }

    @Override
    public List<Bill> getBillByMemberId(Long memberId) {
        return billRepository.findByMember_id(memberId);
    }
}
