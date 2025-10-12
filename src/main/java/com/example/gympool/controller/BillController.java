package com.example.gympool.controller;

import com.example.gympool.entity.Bill;
import com.example.gympool.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        return ResponseEntity.ok(billService.createBill(bill));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        return ResponseEntity.ok(billService.updateBill(id, bill));
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/receptionist/{receptionistId}")
    public ResponseEntity<List<Bill>> getBillsByReceptionist(@PathVariable Long ReceptionistId) {
        return ResponseEntity.ok(billService.getBillsByReceptionistId(ReceptionistId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Bill>> getBillsByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(billService.getBillByMemberId(memberId));
    }
}
