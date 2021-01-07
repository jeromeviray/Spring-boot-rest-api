package com.project.eCommerce.permission.repository;

import com.project.eCommerce.permission.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByAuthority(String role);
}
