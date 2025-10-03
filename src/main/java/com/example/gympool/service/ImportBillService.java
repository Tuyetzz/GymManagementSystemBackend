package com.example.gympool.service;

import com.example.gympool.entity.ImportBill;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportBillService {
    List<ImportBill> getAllImportBills();
    ImportBill getImportBillById(Long id);
    ImportBill createImportBill(ImportBill importBill);
    ImportBill updateImportBill(Long id, ImportBill importBill);
    void deleteImportBill(Long id);
    void importFromCsv(MultipartFile file, Long providerId, Long managerId);

}
