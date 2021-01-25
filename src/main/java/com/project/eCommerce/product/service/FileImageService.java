package com.project.eCommerce.product.service;

import com.project.eCommerce.product.model.FileImage;

public interface FileImageService {
    void save(FileImage fileImage);
    void delete(int id);
    FileImage findImageById(int id);
    FileImage update(FileImage fileImage, int id);
}
