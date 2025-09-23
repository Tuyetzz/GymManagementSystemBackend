package com.example.gympool.service;

import com.example.gympool.entity.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    Product getProductByName(String name);
    List<Product> getAllProducts();
}
