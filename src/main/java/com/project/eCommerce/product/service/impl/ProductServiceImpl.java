package com.project.eCommerce.product.service.impl;

import com.project.eCommerce.exception.ProjectRuntimeException;
import com.project.eCommerce.permission.model.User;
import com.project.eCommerce.permission.service.UserService;
import com.project.eCommerce.product.model.FileImage;
import com.project.eCommerce.product.model.Product;
import com.project.eCommerce.product.repository.ProductRepository;
import com.project.eCommerce.product.service.FileImageService;
import com.project.eCommerce.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileImageService fileImageService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final String rootFile = System.getProperty("user.dir")+"/src/main/webapp/WEB-INF/react/my-app/public/images";

//    public ProductServiceImpl(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product findProductById(int id) {
        Product product = productRepository.findById(id);
        return product;
    }

    @Override
    public void save(MultipartFile[] files, String name, String description, int price, int stock) {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        if(getUserPrinciple() != null){
            logger.info("product processing...");
            product.setUser(getUserPrinciple());
            productRepository.save(product);
            if (saveFileImage(files, product.getId()) == null){
                throw new ProjectRuntimeException("File image list null", new NullPointerException());
            }
            logger.info("Success");
        }

      //  throw new ProjectRuntimeException("Forbidden", new HttpClientErrorException.Forbidden("Forbidden",403));
    }
    public User getUserPrinciple(){

        if(SecurityContextHolder.getContext().getAuthentication() != null ){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null){
//                Role role = (Role) authentication.getAuthorities().stream().map((userRole) -> userRole.getAuthority());
                boolean role = authentication.getAuthorities()
                                            .stream()
                                            .anyMatch(
                                                    (userRole) -> userRole
                                                    .getAuthority()
                                                    .equalsIgnoreCase("ROLE_SELLER")
                                            );
                if (role) {
                    String username = authentication.getName();
                    User user = userService.findByUsername(username);
                    return user;
                }
            }
        }
        return null;
    }
    public List<FileImage> saveFileImage(MultipartFile[] files, int productId){
        Product product = productRepository.findById(productId);

        List<FileImage> fileImageList = new ArrayList<>();
        // reading all the image file and getting the details of the image
        for (MultipartFile file : files){
            Path path = Paths.get(rootFile, file.getOriginalFilename());
            String filename = file.getOriginalFilename();
            logger.info(file.getOriginalFilename());

            try{
                Files.write(path, file.getBytes());
            }catch (IOException e) {
                e.printStackTrace();
                throw new MultipartException("You got an Error men");
            }
            FileImage fileImage = new FileImage();
            fileImage.setFileName(filename);
            fileImage.setSize(file.getSize());


            // checking when product is available on database
            // if not the saving of image for specific product will not save on database
            if(product != null){
                logger.info("image saving...");
                fileImage.setProduct(product);
                fileImageService.save(fileImage);

                fileImageList.add(fileImage);
            }

        }
        return fileImageList;
    }
    @Override
    public void delete(int id){ // deleting the product with the image
        Product product = productRepository.findById(id);
        List<FileImage> fileImage = product.getImage();
        logger.info("{}"+fileImage);
        for (FileImage file : fileImage){
            fileImageService.delete(file.getId());  // send the message id to the file class
        }
        productRepository.delete(product);
    }

    @Override
    public void update(int productId, int imageId) {
        Product product = findProductById(productId);

    }
}
