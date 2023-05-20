package com.nazjara.repository;

import com.nazjara.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByDescription(String description);

    @Override
    @Transactional // without this, Spring will implicitly use read-only transaction that won't work with pessimistic write lock
    @Lock(LockModeType.PESSIMISTIC_WRITE) // pessimistic locking
    Optional<Product> findById(Long id);
}