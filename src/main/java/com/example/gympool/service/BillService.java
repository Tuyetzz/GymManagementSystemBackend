package com.example.gympool.service;

import com.example.gympool.entity.Bill;

import java.util.List;

public interface BillService {
    Bill createBill(Bill bill);
    Bill updateBill(Long id, Bill billDetails);
    void deleteBill(Long id);
    List<Bill> getAllBills();
    Bill getBillById(Long id);
    List<Bill> getBillsByMemberId(Long memberId);
    List<Bill> getBillsByManagerId(Long managerId);
}
