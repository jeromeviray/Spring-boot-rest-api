package com.project.eCommerce.permission.restController;

import com.project.eCommerce.exception.ProjectRuntimeException;
import com.project.eCommerce.jwtUtil.JwtResponse;
import com.project.eCommerce.jwtUtil.JwtUtil;
import com.project.eCommerce.permission.model.ChangePassword;
import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.permission.repository.UserRepository;
import com.project.eCommerce.permission.service.user.UserService;
import com.project.eCommerce.security.CustomUserServiceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/user")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserServiceDetails userServiceDetails;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    Logger loggerFactory = LoggerFactory.getLogger( UserRestController.class );

    @PostMapping(value = "/register")
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @PostMapping( value = "/authenticate" )
    public ResponseEntity<?> createAuthentication(@RequestBody User user) throws Exception{ //generating JWT token for user

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        }catch (DisabledException disabledException){
                throw new Exception("USER_DISABLED", disabledException);
        }catch (BadCredentialsException badCredentialsException){
            throw new Exception("INVALID_CREDENTIALS", badCredentialsException);
        }
        final UserDetails userDetails = userServiceDetails.loadUserByUsername(user.getUsername());
        User findBYID = userRepository.findByUsername(userDetails.getUsername());

        String userRole = userDetails.getAuthorities().toString();
        final String token = jwtUtil.generateToken(userDetails);
        loggerFactory.info(String.valueOf(userRole));

        return ResponseEntity.ok(new JwtResponse(token, findBYID.getId() , userDetails.getUsername(), userRole));
    }
    @GetMapping(value = "/fetch")
    public List<User> fetchUser(){
        return userService.getUser();
    }
    @GetMapping(value = "/{id}")
    public User getUserDetails(@PathVariable int id){
        return userService.findById(id);
    }

    @PutMapping(value = "user/update/{id}")
    public void update(@PathVariable int id, @RequestBody User user){
        User updateUser = userService.findById(id);
        updateUser.setEmail(user.getEmail());
        updateUser.setUsername(user.getUsername());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setGender(user.getGender());
        userService.update(updateUser);
    }
    @DeleteMapping(value = "account/delete/{id}")
    public void delete(@PathVariable int id){
        User user = userService.findById(id);
        userService.delete(user);
    }
    @RequestMapping(value = "/change/password",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.POST)
    public void changePassword(@RequestBody ChangePassword changePassword){
        loggerFactory.info( "{}",
                userService.comparePassword( changePassword.getUsername(),
                changePassword.getCurrentPassword() ) );
        if(!userService.comparePassword( changePassword.getUsername(), changePassword.getCurrentPassword() )){
            throw new ProjectRuntimeException("Mismatch Current Password");
        }else if( !changePassword.getPassword().equals(changePassword.getConfirmPassword() )){
            throw new ProjectRuntimeException("Not match New Password");
        }else {
            userService.changePassword(changePassword.getUsername(), changePassword.getPassword());
        }
    }
}
