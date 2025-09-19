package com.example.gympool.service.impl;

import com.example.gympool.entity.ImportBill;
import com.example.gympool.entity.Product;
import com.example.gympool.repository.ImportBillRepository;
import com.example.gympool.repository.ProductRepository;
import com.example.gympool.service.ImportBillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImportBillServiceImpl implements ImportBillService {

    private final ImportBillRepository importBillRepository;
    private final ProductRepository productRepository;

    public ImportBillServiceImpl(ImportBillRepository importBillRepository,
                                 ProductRepository productRepository) {
        this.importBillRepository = importBillRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ImportBill createImportBill(ImportBill importBill) {
        // Lấy tất cả productId từ importedProducts
        List<Long> productIds = importBill.getImportedProducts().stream()
                .map(ip -> ip.getProduct().getId())
                .toList();

        // Fetch 1 lần tất cả product từ DB
        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // Gắn product + importBill + tăng stock
        importBill.getImportedProducts().forEach(ip -> {
            Product product = productMap.get(ip.getProduct().getId());
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + ip.getProduct().getId());
            }
            // gắn product & bill
            ip.setProduct(product);
            ip.setImportBill(importBill);

            // tăng tồn kho
            int newQuantity = product.getQuantity() + ip.getQuantity();
            product.setQuantity(newQuantity);
            // lưu product lại để cập nhật tồn kho
            productRepository.save(product);
        });

        // Tính total
        double total = importBill.getImportedProducts().stream()
                .mapToDouble(ip -> ip.getProduct().getPrice() * ip.getQuantity())
                .sum();
        importBill.setTotal(total);

        return importBillRepository.save(importBill);
    }


    @Override
    public ImportBill updateImportBill(Long id, ImportBill importBillDetails) {
        ImportBill bill = importBillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ImportBill not found with id: " + id));

        bill.setPaymentMethod(importBillDetails.getPaymentMethod());
        bill.setPaymentStatus(importBillDetails.getPaymentStatus());
        bill.setDate(importBillDetails.getDate());
        bill.setProvider(importBillDetails.getProvider());
        bill.setManager(importBillDetails.getManager());

        // Clear list cũ
        bill.getImportedProducts().clear();

        // Lấy tất cả productId mới
        List<Long> productIds = importBillDetails.getImportedProducts().stream()
                .map(ip -> ip.getProduct().getId())
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // Thêm importedProducts mới
        importBillDetails.getImportedProducts().forEach(ip -> {
            Product product = productMap.get(ip.getProduct().getId());
            ip.setProduct(product);
            ip.setImportBill(bill);
            bill.getImportedProducts().add(ip);
        });

        // Tính lại total
        double total = bill.getImportedProducts().stream()
                .mapToDouble(ip -> ip.getProduct().getPrice() * ip.getQuantity())
                .sum();
        bill.setTotal(total);

        return importBillRepository.save(bill);
    }

    @Override
    public void deleteImportBill(Long id) {
        importBillRepository.deleteById(id);
    }

    @Override
    public List<ImportBill> getAllImportBills() {
        return importBillRepository.findAll();
    }

    @Override
    public ImportBill getImportBillById(Long id) {
        return importBillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ImportBill not found with id: " + id));
    }
}
