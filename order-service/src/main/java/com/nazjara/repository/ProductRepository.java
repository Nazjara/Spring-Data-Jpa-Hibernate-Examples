package com.nazjara.repository;

import com.nazjara.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByDescription(String description);
}