package com.project.eCommerce.security;

import com.project.eCommerce.permission.model.Role;
import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.permission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "customUserDetailsService")
public class CustomUserServiceDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepository.findByUsername(username);

//        if(user.getUsername().equals(username) ){
//            List<GrantedAuthority> authority = new ArrayList<>();
//            for(Role role : user.getRole()){
//                authority.add(new SimpleGrantedAuthority("ROLE_"+role.getAuthority()));
//            }
//            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authority);
//        }else{
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
            if (user == null){
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            List<GrantedAuthority> authority = new ArrayList<>();
            for(Role role : user.getRole()){
                authority.add(new SimpleGrantedAuthority("ROLE_"+role.getAuthority()));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authority);
    }
}
