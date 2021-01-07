package com.project.eCommerce.product.repository;

import com.project.eCommerce.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(int id);
    List<Product> findAll();
}
