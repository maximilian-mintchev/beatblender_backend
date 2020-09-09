package com.app.server.services;

import com.app.server.exceptions.FileStorageException;
import com.app.server.exceptions.MyFileNotFoundException;
import com.app.server.model.BasicUser;
import com.app.server.property.FileStorageProperties;
import com.app.server.repository.BasicUserRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Optional;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    BasicUserRepository basicUserRepository;

    public String storeFile(MultipartFile file, String userName) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetUserLocation = Paths.get(String.valueOf(this.fileStorageLocation), userName);
            Path targetLocation = targetUserLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String userName)  throws  Exception {
//        try {
        Path target = Paths.get(String.valueOf(this.fileStorageLocation), userName);
        Path filePath = target.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            throw new MyFileNotFoundException("File not found " + fileName);
        }
//        }
        /*catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public Resource loadBasicLicenseAsResource(Long downloaderID, Long sampleID) throws Exception {
        Optional<BasicUser> optDownloader = basicUserRepository.findById(downloaderID);
        BasicUser downloader;
        Path target;
        if(optDownloader.isPresent()) {
            downloader = optDownloader.get();
            target = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getBasicUserName(), "basicLicenses", String.valueOf(sampleID) + ".pdf").normalize();
            Resource resource = new UrlResource(target.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("URL PATH couldnt be downloaded: " + target);
            }
        } else {
            throw  new FileNotFoundException("Error happend while Basic License Download");
        }

    }


    public String loadFileAsEncodedString(String fileName) {
        String mypath ="/home/mintch/files/";
        //Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        String encodedString;
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(mypath + fileName));
            encodedString = Base64.getEncoder().encodeToString(fileContent);
            logger.info(encodedString);
        } catch (Exception e) {
            logger.info("Error during encoding process");
            encodedString= "Ok";
        }

        return encodedString;
    }

    public Path createUserDirectory(String userName) {
        Path userDirectory;
        try {
            userDirectory = Files.createDirectories(Paths.get(String.valueOf(this.fileStorageLocation), userName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }

        return userDirectory;
    }
}
