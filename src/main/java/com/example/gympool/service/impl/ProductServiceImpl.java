package com.example.gympool.service.impl;

import com.example.gympool.entity.Product;
import com.example.gympool.repository.ProductRepository;
import com.example.gympool.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        product.setStatus(false);
        // Trả về đối tượng đã được lưu (sẽ chứa ID)
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        Product existing = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(product.getName());
        existing.setType(product.getType());
        existing.setPrice(product.getPrice());
        existing.setImportPrice(product.getImportPrice());
        existing.setBrand(product.getBrand());
        existing.setQuantity(product.getQuantity());
        existing.setStatus(product.isStatus());

        // Trả về đối tượng đã được cập nhật
        return productRepository.save(existing);
    }

    @Override
    @Transactional
    public void softDeleteProduct(Long id) {
        productRepository.softDeleteById(id);
    }

    @Override
    @Transactional
    public void restoreProduct(Long id) {
        productRepository.restoreById(id);
    }

    @Override
    public Product getAvailableProductById(Long id) {
        return productRepository.findByIdAndStatusFalse(id)
                .orElseThrow(() -> new RuntimeException("Product not found or is deleted"));
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllAvailableProducts() {
        return productRepository.findAllByStatusFalse();
    }

    @Override
    public List<Product> getAllProductsForAdmin() {
        return productRepository.findAll();
    }
}