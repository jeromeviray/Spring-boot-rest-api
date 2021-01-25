package com.project.eCommerce.product.repository;

import com.project.eCommerce.product.model.FileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileImageRepository extends JpaRepository<FileImage, Integer> {
    FileImage findById(int id);
}
