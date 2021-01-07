package com.project.eCommerce.product.service.impl;

import com.project.eCommerce.product.model.Product;
import com.project.eCommerce.product.repository.ProductRepository;
import com.project.eCommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product findProductById(int id) {
        Product product = productRepository.findById(id);
        return product;
    }

    @Override
    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }
}
