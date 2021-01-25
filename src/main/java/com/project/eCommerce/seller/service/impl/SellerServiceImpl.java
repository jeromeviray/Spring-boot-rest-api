package com.project.eCommerce.seller.service.impl;

import com.project.eCommerce.permission.model.Role;
import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.permission.repository.RoleRepository;
import com.project.eCommerce.permission.repository.UserRepository;
import com.project.eCommerce.product.model.Product;
import com.project.eCommerce.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(User user) {
        user.setPassword( passwordEncoder.encode(user.getPassword()) );
        Role role = roleRepository.findByAuthority("SELLER");
        List<Role> authority = new ArrayList<>();
        authority.add(role);
        user.setRole(authority);

        userRepository.save(user);
    }

    @Override
    public List<Product> getSellerProduct() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            boolean role = authentication.getAuthorities()
                    .stream()
                    .anyMatch(
                            (userRole) -> userRole
                                    .getAuthority()
                                    .equalsIgnoreCase("ROLE_SELLER")
                    );
            if(role){
                User user = userRepository.findByUsername(authentication.getName());
                return user.getProducts();
            }
        }
        return null;
    }
}
