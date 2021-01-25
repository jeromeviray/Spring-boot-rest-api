package com.project.eCommerce.seller.service;

import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.product.model.Product;

import java.util.List;

public interface SellerService {
    List<Product> getSellerProduct();
    void save(User user);
}
