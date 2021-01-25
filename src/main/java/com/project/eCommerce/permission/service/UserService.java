package com.project.eCommerce.permission.service.user;

import com.project.eCommerce.permission.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> getUser();
    User findById(int id);
    void update( User user);
    void delete(User user);
    void changePassword(String username, String password);
    boolean comparePassword(String username, String currentPassword);
    User findByUsername(String username);
}
