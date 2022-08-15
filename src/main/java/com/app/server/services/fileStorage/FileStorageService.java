package com.app.server.services.fileStorage;

import com.app.server.exceptions.FileStorageException;
import com.app.server.exceptions.MyFileNotFoundException;
import com.app.server.model.license.FullLicense;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.property.FileStorageProperties;
import com.app.server.repository.user.UserRepository;
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
import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;

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
    UserRepository userRepository;

    public String storeFile(MultipartFile file, User user) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            createUserDirectory(user.getUuid());
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetUserLocation = Paths.get(String.valueOf(this.fileStorageLocation), user.getUuid());
            Path targetLocation = targetUserLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeArtistImageFile(MultipartFile file, User user, Artist artist) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            createUserDirectory(user.getUuid());
            createAccountSettingsDirectory(user.getUuid(), artist);
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetProfileLocation = Paths.get(String.valueOf(this.fileStorageLocation), user.getUuid(), "profile");
//            Path targetHistoryProfileLocation = Paths.get(String.valueOf(this.fileStorageLocation), user.getUuid(), "profile", "history");
            Path targetUserLocation = targetProfileLocation.resolve(artist.getCurrentArtistAliasID());
//            Paths.get(String.valueOf(this.fileStorageLocation), user.getUuid(), "profile", artistName);
            Path targetLocation = targetUserLocation.resolve(fileName);
            if(!isDirEmpty(targetProfileLocation)) {
                FileUtils.cleanDirectory(new File(targetUserLocation.toString()));
//                Files.move(targetProfileLocation.resolve(artist.getCurrentArtistAlias().getArtistImageFileName()), targetHistoryProfileLocation.resolve(artist.getCurrentArtistAlias().getArtistImageFileName()), StandardCopyOption.REPLACE_EXISTING);
            }


            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeTrackAudioFile(MultipartFile file, Artist artist) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            createUserDirectory(artist.getUser().getUuid());
            createTrackAudioFileDirectory(artist.getUser().getUuid());
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetTrackLocation = Paths.get(String.valueOf(this.fileStorageLocation), artist.getUser().getUuid(), "track", "audio");
//            Path targetUserLocation = targetProfileLocation.resolve(artist.getCurrentArtistAliasID());
            Path targetLocation = targetTrackLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeTrackImageFile(MultipartFile file, Artist artist) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            createUserDirectory(artist.getUser().getUuid());
            createTrackImageFileDirectory(artist.getUser().getUuid());
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetTrackLocation = Paths.get(String.valueOf(this.fileStorageLocation), artist.getUser().getUuid(), "track", "image");
//            Path targetUserLocation = targetProfileLocation.resolve(artist.getCurrentArtistAliasID());
            Path targetLocation = targetTrackLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    private static boolean isDirEmpty(final Path directory) throws IOException {
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }
//    String fileName, String userName
    public Resource loadFileAsResource(Path targetPath, String fileName) throws Exception {
//        try {
//        Path target = Paths.get(String.valueOf(this.fileStorageLocation), userName);
        Path target = createPath(targetPath);
        Path filePath = target.resolve(fileName).normalize();
        System.out.println(filePath.toUri().toString());
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
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

    public Path createPath(Path filePath) {
        Path target = Paths.get(String.valueOf(this.fileStorageLocation), String.valueOf(filePath));
        return target;
    }

    public Resource loadBasicLicenseAsResource(String downloaderID, String sampleID) throws Exception {
//        Optional<User> optDownloader = userRepository.findById(downloaderID);
//        User downloader;
        Path target;
//        if(optDownloader.isPresent()) {
//        downloader = optDownloader.get();
//        target = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getBasicUserName(), "basicLicenses", String.valueOf(sampleID) + ".pdf").normalize();
        target = Paths.get(String.valueOf(this.fileStorageLocation), downloaderID, "basicLicenses", sampleID + ".pdf").normalize();
        Resource resource = new UrlResource(target.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new MyFileNotFoundException("URL PATH couldnt be downloaded: " + target);
        }
//        } else {
//            throw  new FileNotFoundException("Error happend while Basic License Download");
//        }

    }

//    public Resource loadFullLicenseAsResource(FullLicense fullLicense, User authUser) throws Exception {
//
//        Path target;
//
//        target = Paths.get(String.valueOf(this.fileStorageLocation), fullLicense.getTrack().getAudioUnit().getCreator().getUser().getUuid(), "fullLicenses", fullLicense.getTrack().getTrackID() + ".pdf").normalize();
//        Resource resource = new UrlResource(target.toUri());
//        if (resource.exists()) {
//            return resource;
//        } else {
//            throw new MyFileNotFoundException("URL PATH couldnt be downloaded: " + target);
//        }
//
//
//    }


    public String loadFileAsEncodedString(String fileName) {
        String mypath = "/home/mintch/files/";
        //Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        String encodedString;
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(mypath + fileName));
            encodedString = Base64.getEncoder().encodeToString(fileContent);
            logger.info(encodedString);
        } catch (Exception e) {
            logger.info("Error during encoding process");
            encodedString = "Ok";
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

    public Path createAccountSettingsDirectory(String userName, Artist artist) {
        Path userDirectory;
        try {
            userDirectory = Files.createDirectories(Paths.get(String.valueOf(this.fileStorageLocation), userName, "profile", artist.getCurrentArtistAliasID()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }

        return userDirectory;
    }

    public Path createTrackAudioFileDirectory(String userName) {
        Path trackDirectory;
        try {
            trackDirectory = Files.createDirectories(Paths.get(String.valueOf(this.fileStorageLocation), userName, "track", "audio"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }

        return trackDirectory;
    }

    public Path createTrackImageFileDirectory(String userName) {
        Path trackDirectory;
        try {
            trackDirectory = Files.createDirectories(Paths.get(String.valueOf(this.fileStorageLocation), userName, "track", "image"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }

        return trackDirectory;
    }

//    public void storeAudioFile(MultipartFile audioFile, UUID id) {
//        storeFile(audioFile, id.toString());
//    }
}
