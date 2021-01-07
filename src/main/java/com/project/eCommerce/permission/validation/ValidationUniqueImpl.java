//package com.project.eCommerce.permission.validation;
//
//import com.example.jsp.service.UserService;
//import com.project.eCommerce.permission.validation.annotation.Unique;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class ValidationUniqueImpl implements ConstraintValidator<Unique, String> {
//
//    Logger logger = LoggerFactory.getLogger(ValidationUniqueImpl.class);
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        return value != null && !userService.isUsernameAlreadyExisted(value);
//    }
//}
