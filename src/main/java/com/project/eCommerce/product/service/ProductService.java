package com.project.eCommerce.product.service;

import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.product.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product findProductById(int id);
    void save(MultipartFile[] files, String name, String description, int price, int stock);
    void delete(int id);
    void update(int productId, int imageId);
}

