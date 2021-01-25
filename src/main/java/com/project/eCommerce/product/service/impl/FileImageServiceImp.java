package com.project.eCommerce.product.service.impl;

import com.project.eCommerce.exception.ProjectRuntimeException;
import com.project.eCommerce.product.model.FileImage;
import com.project.eCommerce.product.repository.FileImageRepository;
import com.project.eCommerce.product.service.FileImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class FileImageServiceImp implements FileImageService {
    @Autowired
    private FileImageRepository fileImageRepository;

    private final String rootFile = System.getProperty("user.dir")+
                                                        "/src/main/webapp/WEB-INF/react/my-app/public/images";

    Logger logger = LoggerFactory.getLogger( FileImageServiceImp.class );

    @Override
    public void save(FileImage fileImage) {
        fileImageRepository.save(fileImage);
    }

    @Override
    public void delete(int id) { // delete the image files on local device and the data on the database
        FileImage file = fileImageRepository.findById(id);
        if(deleteImage(file.getFileName())){
            fileImageRepository.delete(file);
        }else{
            throw new ProjectRuntimeException("Unable to delete the files");
        }
    }
    public boolean deleteImage(String fileName){
        File files = new File(rootFile+""+fileName);
        try{
            if(files.delete()){
                logger.info("Image file has been deleted " + fileName);
                return true;
            }else{
                logger.info("Image has been already deleted " + fileName);
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();    //prints exception if any
        }
        return false;
    }
    @Override
    public FileImage findImageById(int id) {
        return fileImageRepository.findById(id);
    }

    @Override
    public FileImage update(FileImage fileImageUpdate, int id) {
        FileImage fileImage = findImageById(id);

        if(deleteImage(fileImage.getFileName())){
            fileImage.setFileName(fileImageUpdate.getFileName());
            fileImage.setSize(fileImageUpdate.getSize());
            save(fileImage);
            return fileImage;
        }
        return null;

    }
}
