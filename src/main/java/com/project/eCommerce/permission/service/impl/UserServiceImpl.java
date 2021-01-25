package com.project.eCommerce.permission.service.user.impl;

import com.project.eCommerce.permission.model.Role;
import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.permission.repository.RoleRepository;
import com.project.eCommerce.permission.repository.UserRepository;
import com.project.eCommerce.permission.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
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
    public List<User> getUser() {
        List<User> user = userRepository.findAll();
        return user;
    }
    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void changePassword(String username, String password) {

        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        update(user);
    }

    @Override
    public boolean comparePassword(String username, String currentPassword) {
        return passwordEncoder.matches(currentPassword, userRepository.findByUsername(username).getPassword());
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
