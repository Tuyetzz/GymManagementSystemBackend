package com.example.gympool.service.impl;

import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.service.BillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final StaffRepository staffRepository;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    public BillServiceImpl(BillRepository billRepository,
                           ProductRepository productRepository,
                           StaffRepository staffRepository,
                           MemberRepository memberRepository,
                           ManagerRepository managerRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        this.staffRepository = staffRepository;
        this.memberRepository = memberRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public Bill createBill(Bill bill) {
        // lấy member + manager từ DB (tránh bị detached entity)
        Member member = memberRepository.findById(bill.getMember().getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Manager manager = managerRepository.findById(bill.getManager().getId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        bill.setMember(member);
        bill.setManager(manager);

        // lấy productId
        List<Long> productIds = bill.getListSoldProduct().stream()
                .map(sp -> sp.getProduct().getId())
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        // gắn product + giảm stock
        bill.getListSoldProduct().forEach(sp -> {
            Product product = productMap.get(sp.getProduct().getId());
            if (product == null) throw new RuntimeException("Product not found");
            sp.setProduct(product);
            sp.setBill(bill);

            // giảm tồn kho
            int newQuantity = product.getQuantity() - sp.getQuantity();
            if (newQuantity < 0) throw new RuntimeException("Not enough stock for product: " + product.getName());
            product.setQuantity(newQuantity);
            productRepository.save(product);
        });

        // gắn staff
        if (bill.getListStaffAssigned() != null) {
            bill.getListStaffAssigned().forEach(sa -> {
                Staff staff = staffRepository.findById(sa.getStaff().getId())
                        .orElseThrow(() -> new RuntimeException("Staff not found"));
                sa.setStaff(staff);
                sa.setBill(bill);
            });
        }

        // tính total
        double total = bill.getListSoldProduct().stream()
                .mapToDouble(sp -> sp.getProduct().getPrice() * sp.getQuantity())
                .sum();
        bill.setTotal(total);

        return billRepository.save(bill);
    }

    @Override
    public Bill updateBill(Long id, Bill billDetails) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));

        bill.setPaymentMethod(billDetails.getPaymentMethod());
        bill.setPaymentStatus(billDetails.getPaymentStatus());
        bill.setDate(billDetails.getDate());

        // clear list cũ
        bill.getListSoldProduct().clear();
        bill.getListStaffAssigned().clear();

        // xử lý product mới
        List<Long> productIds = billDetails.getListSoldProduct().stream()
                .map(sp -> sp.getProduct().getId())
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        billDetails.getListSoldProduct().forEach(sp -> {
            Product product = productMap.get(sp.getProduct().getId());
            sp.setProduct(product);
            sp.setBill(bill);
            bill.getListSoldProduct().add(sp);
        });

        // xử lý staff mới
        billDetails.getListStaffAssigned().forEach(sa -> {
            Staff staff = staffRepository.findById(sa.getStaff().getId())
                    .orElseThrow(() -> new RuntimeException("Staff not found"));
            sa.setStaff(staff);
            sa.setBill(bill);
            bill.getListStaffAssigned().add(sa);
        });

        // tính lại total
        double total = bill.getListSoldProduct().stream()
                .mapToDouble(sp -> sp.getProduct().getPrice() * sp.getQuantity())
                .sum();
        bill.setTotal(total);

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
    public List<Bill> getBillsByMemberId(Long memberId) {
        return billRepository.findByMember_Id(memberId);
    }

    @Override
    public List<Bill> getBillsByManagerId(Long managerId) {
        return billRepository.findByManager_Id(managerId);
    }
}
