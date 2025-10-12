package com.example.gympool.service.impl;

import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.service.ImportBillService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImportBillServiceImpl implements ImportBillService {

    private final ImportBillRepository importBillRepository;
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final ManagerRepository managerRepository;

    public ImportBillServiceImpl(ImportBillRepository importBillRepository,
                                 ProductRepository productRepository,
                                 ProviderRepository providerRepository,
                                 ManagerRepository managerRepository) {
        this.importBillRepository = importBillRepository;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public ImportBill createImportBill(ImportBill importBill) {
        List<Long> productIds = importBill.getImportedProducts().stream()
                .map(ip -> ip.getProduct().getId())
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        importBill.getImportedProducts().forEach(ip -> {
            Product product = productMap.get(ip.getProduct().getId());
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + ip.getProduct().getId());
            }
            ip.setProduct(product);
            ip.setImportBill(importBill);

            int newQuantity = product.getQuantity() + ip.getQuantity();
            product.setQuantity(newQuantity);

            Double importPrice = ip.getProduct().getImportPrice();
            product.setImportPrice(importPrice);

            productRepository.save(product);
        });

        return importBillRepository.save(importBill);
    }


    @Override
    public ImportBill updateImportBill(Long id, ImportBill importBillDetails) {
        ImportBill bill = importBillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ImportBill not found with id: " + id));

        bill.setDate(importBillDetails.getDate());
        bill.setProvider(importBillDetails.getProvider());
        bill.setManager(importBillDetails.getManager());

        bill.getImportedProducts().forEach(ip -> {
            Product product = ip.getProduct();
            product.setQuantity(product.getQuantity() - ip.getQuantity());
            productRepository.save(product);
        });

        bill.getImportedProducts().clear();

        List<Long> productIds = importBillDetails.getImportedProducts().stream()
                .map(ip -> ip.getProduct().getId())
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        importBillDetails.getImportedProducts().forEach(ip -> {
            Product product = productMap.get(ip.getProduct().getId());
            ip.setProduct(product);
            ip.setImportBill(bill);
            bill.getImportedProducts().add(ip);

            int newQuantity = product.getQuantity() + ip.getQuantity();
            product.setQuantity(newQuantity);

            Double importPrice = ip.getProduct().getImportPrice();
            product.setImportPrice(importPrice);

            productRepository.save(product);
        });

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

    @Override
    public void importFromCsv(MultipartFile file, Long providerId, Long managerId) {
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<ImportedProduct> importedProducts = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                Long productId = Long.parseLong(record.get("productId"));
                int quantity = Integer.parseInt(record.get("quantity"));

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

                // cập nhật tồn kho
                product.setQuantity(product.getQuantity() + quantity);
                productRepository.save(product);

                ImportedProduct importedProduct = new ImportedProduct();
                importedProduct.setProduct(product);
                importedProduct.setQuantity(quantity);
                importedProducts.add(importedProduct);
            }

            // Lấy provider + manager
            Provider provider = providerRepository.findById(providerId)
                    .orElseThrow(() -> new RuntimeException("Provider not found with id: " + providerId));

            Manager manager = managerRepository.findById(managerId)
                    .orElseThrow(() -> new RuntimeException("Manager not found with id: " + managerId));

            // Tạo ImportBill
            ImportBill importBill = new ImportBill();
            importBill.setDate(new Date());
            importBill.setProvider(provider);
            importBill.setManager(manager);

            // gắn quan hệ
            importedProducts.forEach(ip -> ip.setImportBill(importBill));
            importBill.setImportedProducts(importedProducts);

            importBillRepository.save(importBill);

        } catch (Exception e) {
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }
}
