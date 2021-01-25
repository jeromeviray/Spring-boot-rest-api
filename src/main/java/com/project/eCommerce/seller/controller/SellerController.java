package com.project.eCommerce.seller.controller;

import com.project.eCommerce.permission.model.Role;
import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.product.model.Product;
import com.project.eCommerce.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @RequestMapping( value = "/register", method = RequestMethod.POST)
    public void registerSeller(@RequestBody User user){
        sellerService.save(user);
    }

    @GetMapping(value = "/product")
    public List<Product> products(){
        return sellerService.getSellerProduct();
    }
}
