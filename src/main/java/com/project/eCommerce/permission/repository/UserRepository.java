package com.project.eCommerce.permission.repository;

import com.project.eCommerce.permission.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int id);

    @Override
    <S extends User> S save(S s);
}
