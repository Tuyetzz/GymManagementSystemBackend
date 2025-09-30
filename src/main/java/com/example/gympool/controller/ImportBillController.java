package com.example.gympool.controller;

import com.example.gympool.entity.ImportBill;
import com.example.gympool.service.ImportBillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/import-bills")
public class ImportBillController {

    private final ImportBillService importBillService;

    public ImportBillController(ImportBillService importBillService) {
        this.importBillService = importBillService;
    }

    @GetMapping
    public List<ImportBill> getAllImportBills() {
        return importBillService.getAllImportBills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImportBill> getImportBillById(@PathVariable Long id) {
        return ResponseEntity.ok(importBillService.getImportBillById(id));
    }

    @PostMapping
    public ResponseEntity<ImportBill> createImportBill(@RequestBody ImportBill importBill) {
        return ResponseEntity.ok(importBillService.createImportBill(importBill));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportBill> updateImportBill(
            @PathVariable Long id,
            @RequestBody ImportBill importBill
    ) {
        return ResponseEntity.ok(importBillService.updateImportBill(id, importBill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImportBill(@PathVariable Long id) {
        importBillService.deleteImportBill(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file,
                                            @RequestParam("providerId") Long providerId,
                                            @RequestParam("managerId") Long managerId) {
        importBillService.importFromCsv(file, providerId, managerId);
        return ResponseEntity.ok("CSV imported successfully");
    }
}
