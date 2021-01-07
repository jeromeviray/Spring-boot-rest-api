package com.project.eCommerce.product.service;

import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product findProductById(int id);
    Product save(Product product);
}

