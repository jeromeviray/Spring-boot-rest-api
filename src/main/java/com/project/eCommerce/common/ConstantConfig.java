package com.project.eCommerce.common;

public class ConstantConfig {
//    "/api/v1/user/authenticate",
//            "/api/v1/user/register",
//            "/api/v1/product/create",
//            "/api/v1/product/products",
//            "/api/v1/product/findById/**",
//            "/api/v1/product/delete/findById/**"
//    BASE PATH URI
    public final static String API_VERSION = "/v1";
    public final static String API_PREFIX = "/api"+API_VERSION;
    public final static String PRODUCT = API_PREFIX+"/product";
    public final static String USER = API_PREFIX+"/user";
    public final static String ID = "/{id}";
    public final static String FIND_BY_ID = "/search-user";

    // user endpoint
    public final static String ACCOUNT_PROFILE = "/profile";
    public final static String AUTHENTICATE = USER+"/authenticate";
    public final static String REGISTRATION = "/register";
    public final static String DELETE_USER = ACCOUNT_PROFILE+"/delete"+FIND_BY_ID+ID;
    public final static String UPDATE_USER = ACCOUNT_PROFILE+"/update"+FIND_BY_ID+ID;
    public final static String CHANGE_PASSWORD = ACCOUNT_PROFILE+"/change/password";

    //product endpoint
}
