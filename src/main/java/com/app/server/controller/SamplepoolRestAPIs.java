package com.app.server.controller;


import java.nio.file.Path;
import java.security.Principal;
import java.util.*;

import com.app.server.enums.AudioUnitType;
import com.app.server.enums.ResponseStatus;
import com.app.server.exceptions.MyFileNotFoundException;
import com.app.server.messages.response.*;
import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import com.app.server.repository.license.BasicLicenseRepository;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.fileStorage.FileStorageService;
import com.app.server.services.security.KeycloakService;
import com.app.server.services.UtilityService;
//import io.jsonwebtoken.Jwt;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.security.RolesAllowed;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/samplepool")
public class SamplepoolRestAPIs {

//    @Autowired
//    private FileStorageService fileStorageService;
//
//    @Autowired
//    AudioUnitRepository<AudioUnit> audioUnitRepository;
//
//    @Autowired
//    SampleRepository<Sample> sampleRepository;
//
//    @Autowired
//    TrackRepository<Track> trackRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ArtistRepository artistRepository;
//
//    @Autowired
//    UtilityService utilityService;
//
//
//
//    @Autowired
//    ArtistAliasRepository<ArtistAlias> artistArtistAliasRepository;
//




    Logger logger = LoggerFactory.getLogger(SamplepoolRestAPIs.class);


//    @GetMapping("/catalog")
//    public ResponseEntity<?> getCatalog() {
//        Optional<List<CatalogEntity>> optCatalog = catalogRepository.findCatalog();
//        List<CatalogEntity> catalogList = new ArrayList<CatalogEntity>();
//        if(optCatalog.isPresent()) {
//            catalogList = optCatalog.get();
//        } else {
//            System.out.println("Nothing Found");
//        }
//        return ResponseEntity.ok(catalogList);
//    }



    @GetMapping("/testGet")
////    @RolesAllowed("beatblender-admin")
    public void hero() {
    }

//    @PostMapping("/public/searchMusic")
//    public ResponseEntity<AudioFileResponse> searchMusic(
//            @RequestParam("genres") String[] genres,
//            @RequestParam("regions") String[] regions,
//            @RequestParam("trackTypes") String[] trackTypes,
//            @RequestParam("songKeys") String[] songKeys,
//            @RequestParam("bpmMinMax") String[] bpmMinMax,
//            @RequestParam("yearMinMax") String[] yearMinMax
//    ) {
//
//        List<String> genreList = Arrays.asList(genres);
//        List<String> regionList = Arrays.asList(regions);
//        List<String> trackTypeList = Arrays.asList(trackTypes);
//        List<String> songKeyList = Arrays.asList(songKeys);
//
//
//        List<Long> filteredSampleIDs = new ArrayList<>();
//        Optional<List<SampleDetails>> optionalGenre = this.sampleDetailsRepository.findBySampleDetailsGenre(genreList, regionList, trackTypeList, songKeyList);
//        FileResponse[] audioFileArray = null;
//        if(optionalGenre.isPresent()) {
//            List<SampleDetails> filteredSampleDetails = optionalGenre.get();
//            filteredSampleDetails.forEach((sampleDetail) -> {
//                // filteredSampleIDs.add(sampleDetail.getId());
//                filteredSampleIDs.add(sampleDetail.getId());
//                System.out.println(sampleDetail);
//            });
//            Optional<List<Sample>> optionalFilteredSamples = sampleRepository.findBySampleDetailsId(filteredSampleIDs);
//            if(optionalFilteredSamples.isPresent()) {
//                System.out.println("Found some samples");
//                List<Sample> filteredSamples = optionalFilteredSamples.get();
//                filteredSamples.forEach((sample) -> {
//                    System.out.println(sample);
//                });
//
//                audioFileArray = new FileResponse[filteredSamples.size()];
//
//                int i = 0;
//                for (Sample sample : filteredSamples) {
//                    audioFileArray[i] = new FileResponse(
//                            i,
//                            sample.getBasicUser().getBasicUserName(),
//                            sample.getArtistName(), sample.getSampleTitle(),
//                            sample.getAudioFilePath(),
//                            sample.getSampleImagePath(),
//                            sample.getAudioDownLoadUri(),
//                            sample.getImageDownLoadUri(),
//                            sample.getSampleDetails().getGenre(),
//                            sample.getSampleDetails().getTrackType(),
//                            sample.getSampleDetails().getSongKey(),
//                            sample.getSampleDetails().getRegion(),
//                            sample.getSampleDetails().getAudioDescription(),
//                            sample.getId(),
//                            sample.getSamplePrice()
//                    );
//                    i++;
//                }
//            } else {
//                System.out.println("Sorry, no samples found");
//            }
//            System.out.println("Successfull Search");
//        }
//        return ResponseEntity.ok(new AudioFileResponse(audioFileArray));
//    }

//    @PostMapping("/public/uploadSampleData")
//    public ResponseEntity<ResponseMessage> uploadSampleData(
//
//            @AuthenticationPrincipal Principal user,
//            @RequestParam("artistNames") String[] artistNames,
//            @RequestParam("sampleTitles") String[] sampleTitles,
//            @RequestParam("sampleImages") MultipartFile[] sampleImages,
//            @RequestParam("audioFiles") MultipartFile[] audioFiles,
//            @RequestParam("genres") String[] genres,
//            @RequestParam("trackTypes") String[] trackTypes,
//            @RequestParam("songKeys") String[] songKeys,
//            @RequestParam("regions") String[] regions,
//            @RequestParam("audioDescriptions") String[] audioDescriptions,
//            @RequestParam("realFirstName") String realFirstName,
//            @RequestParam("realLastName") String realLastName
//    ) {
//
//        System.out.println(artistNames[0]);
//        String clientId = client.getUser(user.getName()).getId();
//        BasicUser authenticatedUser;
//        Optional<BasicUser> optUser =
//                basicUserRepository.findByBasicUserName(clientId);
//
//        Path userDataPath;
//        if (optUser.isEmpty()) {
//            authenticatedUser = basicUserRepository.save(new BasicUser(clientId));
//            userDataPath = fileStorageService.createUserDirectory(authenticatedUser.getBasicUserName());
//            System.out.println(String.valueOf(userDataPath));
//            System.out.println("New user created: " + clientId);
//        } else {
//            authenticatedUser = optUser.get();
//        }
//
//        List<String> audioFileNames = new ArrayList<>();
//        for (int i = 0; i < audioFiles.length; i++) {
//            String fileName = fileStorageService.storeFile(audioFiles[i], authenticatedUser.getBasicUserName());
//            audioFileNames.add(fileName);
//        }
//
//
//        List<String> audioFileUris = new ArrayList<>();
//        audioFileNames.forEach((fileName) -> {
//                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                            .path("/api/samplepool/downloadFile/")
//                            .path(authenticatedUser.getBasicUserName() + "/")
//                            .path(fileName)
//                            .toUriString();
//                    audioFileUris.add(fileDownloadUri);
//                }
//
//        );
//
//        List<String> imagesNames = new ArrayList<>();
//        for (int i = 0; i < sampleImages.length; i++) {
//            String fileName = fileStorageService.storeFile(sampleImages[i], authenticatedUser.getBasicUserName());
//            imagesNames.add(fileName);
//        }
//
//
//        List<String> imageFileUris = new ArrayList<>();
//        imagesNames.forEach((fileName) -> {
//                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                            .path("/api/samplepool/downloadFile/")
//                            .path(authenticatedUser.getBasicUserName()+ "/")
//                            .path(fileName)
//                            .toUriString();
//                    imageFileUris.add(fileDownloadUri);
//                }
//        );
//
//
//        List<Sample> samples = new ArrayList<>();
//        for (int i = 0; i < artistNames.length; i++) {
//            Sample savedSample = sampleRepository.save(new Sample(
//                    artistNames[i],
//                    sampleTitles[i],
//                    audioFileNames.get(i),
//                    imagesNames.get(i),
//                    audioFileUris.get(i),
//                    imageFileUris.get(i),
//                    this.sampleDetailsRepository.save(new SampleDetails(
//                            utilityService.handleOptionalFormData(genres[i]),
//                            utilityService.handleOptionalFormData(trackTypes[i]),
//                            utilityService.handleOptionalFormData(songKeys[i]),
//                            utilityService.handleOptionalFormData(regions[i]),
//                            utilityService.handleOptionalFormData(audioDescriptions[i])
//                    )),
//                    authenticatedUser));
//            samples.add(savedSample);
//
//            //samples.add(new Sample(artistNames[i], sampleTitles[i], audioFileUris.get(i), imageFileUris.get(i), authenticatedUser));
//        }
//
//        System.out.println(samples);
//
//
//        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Success,"Ok"));
//
//    }
    // todo api evtl entfernen
//    @PostMapping("/uploadSample")
//    public ResponseEntity<ResponseMessage> uploadSampleFile(
//
//            @RequestParam("artistName") String artistName, @RequestParam("sampleTitle") String sampleTitle,
//            @RequestParam("file") MultipartFile sampleFile) {
//
//        logger.info("SampleFile received successfully");
//
//        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Success, sampleTitle));
//    }









//    @PostMapping("/public/basicUserDataUrl")
//    public ResponseEntity getBasicUser(@AuthenticationPrincipal Principal user) {
//
//        String userID = client.getUser(user.getName()).getId();
//        Optional<BasicUser> optUser = basicUserRepository.findByBasicUserName(userID);
//        BasicUserResponse basicUserResponse = new BasicUserResponse();
//        if(optUser.isPresent()) {
//            BasicUser basicUser = optUser.get();
//            basicUserResponse = new BasicUserResponse(basicUser.getLicensePoints());
//        } else {
//            logger.info("No User Found");
//        }
//
//        return ResponseEntity.ok(basicUserResponse);
//    }


//    @PostMapping("/public/downloadFullLicense")
//    public ResponseEntity downloadFullLicense(@AuthenticationPrincipal Principal user,
//                                              @RequestParam("sampleID") Long sampleID
//                                              ) {
//        BasicUser uploader;
//        BasicUser downloader;
//        Sample sample;
//        BasicLicense basicLicense;
//        Optional<BasicLicense> optBasicLicense;
//        String basicLicensePath;
//
//        String downloaderID = client.getUser(user.getName()).getId();
//        Optional<BasicUser> optDownloader = basicUserRepository.findByBasicUserName(downloaderID);
//
//            if(optDownloader.isPresent()) {
//                downloader = optDownloader.get();
//                optBasicLicense = basicLicenseRepository.findByDownloaderIDAndSampleID(downloader.getId(),sampleID);
//                if(optBasicLicense.isPresent()){
//                    basicLicense = optBasicLicense.get();
//                    if(basicLicense.getLicenseStatus().equals(LicenseStatus.BasicLicense)){
//                        System.out.println("Creating Full License");
//                        if(downloader.getLicensePoints() >= basicLicense.getFullLicensePrice()) {
//                            basicLicense.setLicenseStatus(LicenseStatus.FullLicense);
//                            downloader.setLicensePoints(downloader.getLicensePoints() - basicLicense.getFullLicensePrice());
//                            basicUserRepository.save(downloader);
//                            basicLicenseRepository.save(basicLicense);
//                              return ResponseEntity.ok(new FullLicenseResponse(ResponseStatus.Success, downloader.getLicensePoints()));
//                        } else {
//                            return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You don't have enough License Points for a Full License!"));
//                        }
//
//                    }
//                    if(basicLicense.getLicenseStatus().equals(LicenseStatus.FullLicense)) {
//                        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You already have a Full License for this sample"));
//                    }
//                } else {
//                    return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "Before you can get a Full License you have to get a basic license"));
//                }
//            } else {
//                return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "Please create a Account In"));
//            }
//
//
//
//        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "Something went wrong. Please try again"));
//
//
//
//
//
//
//
//
//
//    }


//    @PostMapping("/public/downloadBasicLicense")
//    public ResponseEntity downloadBasicLicense(@AuthenticationPrincipal Principal user,
//                                                    @RequestParam("userName") String uploaderName,
//                                                    @RequestParam("sampleID") Long sampleID
//                                                  ) {
//
//
//        BasicUser uploader;
//        BasicUser downloader;
//        Sample sample;
//        BasicLicense basicLicense;
//        String basicLicensePath;
//
//        String downloaderID = client.getUser(user.getName()).getId();
//        Optional<BasicUser> optUploader = basicUserRepository.findByBasicUserName(uploaderName);
//        Optional<BasicUser> optDownloader = basicUserRepository.findByBasicUserName(downloaderID);
//
//
//
//
//        //1. Check if UPLOADER exists
//        if(optUploader.isPresent()) {
//            uploader = optUploader.get();
//
////            2.check if DOWNLOADER exists
//            if(optDownloader.isPresent()) {
//               downloader = optDownloader.get();
//               Optional<Sample> optSample = sampleRepository.findById(sampleID);
//
//               if(uploader.getBasicUserName() != downloader.getBasicUserName()) {
//                   //               3. check if SAMPLE exists
//                   if(optSample.isPresent()) {
//                       sample = optSample.get();
//                       Optional<BasicLicense> optBasicLicense = basicLicenseRepository.findByDownloaderIDAndSampleID(downloader.getId(), sample.getId());
//
////                    4. check if a BASIC USER LICENSE already exists
//                       if(optBasicLicense.isEmpty()) {
//
////                        5. save new BASIC USER LICENSE
//                           uploader.increaseLicensePoints();
//                           basicLicensePath = utilityService.writePDF(uploader, downloader, sample);
//                           basicLicense = new BasicLicense(downloader, uploader, sample, basicLicensePath);
//                           basicLicenseRepository.save(basicLicense);
//                           logger.info("Successfully generated BasicLicense");
//
//                           return ResponseEntity.ok(new BasicLicenseResponse(ResponseStatus.Success, basicLicense.getSample().getId(), basicLicense.getDownloader().getId(), basicLicense.getUploader().getId(), basicLicense.getFullLicensePrice(), basicLicense.getBasicLicensePath()));
//
//                       }
//                       else {
////                           System.out.println("Basic License already exsited, a user cannot own more than one basic license for the same sample");
//                           return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You already have a Basic License. You can spend your License Points to purchase a Full License for this sample."));
//
//                       }
//
//                   } else  {
//                       logger.error("No Sample found while creating Basic User License");
//                   }
//               } else {
//                    return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You cannot retrieve a Basic License for your own sample."));
//               }
//
//            } else {
//                logger.error("No Downloader found while creating BasicLicense");
//            }
//
//
//            /*
//            sampleRepository.findByUsernameAndFileName();
//*/
//            basicUserRepository.save(uploader);
//            System.out.println(uploader.getLicensePoints());
//        } else {
//            logger.info("No Uplaoder found while creating basicLicense");
//        }
//
//        throw new FileStorageException("Could not create Basic License");
//
//
//
//
//      /*  return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);*/
//
//
//
//
//
//
//
//    }


    //	@GetMapping("/public")
//	public Collection<AudioFile> audioFiles() {
//		return audioFileRepository.findAll().stream().filter(audioFile -> true).collect(Collectors.toList());
//	}
//
//    @GetMapping("/importData")
//    public void importData(@AuthenticationPrincipal Principal user) {
//
//        String clientId = client.getUser(user.getName()).getId();
//        BasicUser authenticatedUser;
//        Optional<BasicUser> optUser =
//                basicUserRepository.findByBasicUserName(clientId);
//
//        Path userDataPath;
//
//        if (optUser.isEmpty()) {
//            authenticatedUser = basicUserRepository.save(new BasicUser(clientId));
//            userDataPath = fileStorageService.createUserDirectory(authenticatedUser.getBasicUserName());
//            System.out.println(String.valueOf(userDataPath));
//            System.out.println("New user created: " + clientId);
//        } else {
//            authenticatedUser = optUser.get();
//        }
//
//        List<String> artistNames = new ArrayList<String>(Arrays.asList("Aragorn", "Legolas", "Gimmly", "Samwise Gamgee","Bilbo", "Eomer", "Faramir"));
//        List<String> titles = new ArrayList<String>(Arrays.asList("They call me Streicher", "Friendship", "Der zählt trotzdem bloß als einer", "Yum Yum Lembas", "Legends never die", "And Rohan will answer", "Heart full of Pride"));
//        List<String> audioFileName = new ArrayList<String>(Arrays.asList("s1.mp3", "s3.mp3", "s4.mp3", "s5.mp3", "s6.mp3", "s7.mp3", "s9.mp3"));
//        List<String> imageNames = new ArrayList<String>(Arrays.asList("m17.jpg","m44.jpg","m8.jpg","m10.jpg","m41.jpg","m13.jpg","m44.jpg"));
//        List<String> audioDownLoadUri = new ArrayList<String>(Arrays.asList(
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s1.mp3",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s3.mp3",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s4.mp3",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s5.mp3",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s6.mp3",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s7.mp3",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/s9.mp3"
//        ));
//        List<String> imageDownLoadUri = new ArrayList<String>(Arrays.asList(
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m17.jpg",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m44.jpg",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m8.jpg",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m10.jpg",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m41.jpg",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m13.jpg",
//                "http://localhost:8080/api/samplepool/downloadFile/00uagk758iaov3E274x6/m44.jpg"
//        ));
//
//        List<String> genres = new ArrayList<String>(Arrays.asList(
//                "Blues", "Classical", "Country", "Electronic", "Hip Hop/Rap", "Jazz", "Latin"
//        ));
//
//        List<String> trackTypes = new ArrayList<String>(Arrays.asList(
//                "Accordion", "Bass", "Drum", "Edits", "Flute", "FX track", "Guitar"
//        ));
//        List<String> songKeys = new ArrayList<String>(Arrays.asList(
//                "A major", "A minor", "A flat major", "A flat minor", "B major", "B minor", "B flat major"
//        ));
//        List<String> regions = new ArrayList<String>(Arrays.asList(
//                "Northern Europe", "Western Europe", "Southern Europe", "Eastern Europe", "Middle East", "Caribbean", "Oceania, Pacific"
//        ));
//
//        List<String> audioDescriptions = new ArrayList<String>(Arrays.asList(
//                "Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet consectetur adipisicing.", "Lorem ipsum dolor sit.","Lorem ipsum dolor, sit amet consectetur adipisicing elit.","Lorem, ipsum.","Lorem ipsum dolor sit amet consectetur.","Lorem ipsum dolor sit amet."
//        ));
//
//        List<Sample> samples = new ArrayList<>();
//        for (int i = 0; i < artistNames.size(); i++) {
//            Sample savedSample = sampleRepository.save(new Sample(
//                    artistNames.get(i),
//                    titles.get(i),
//                    audioFileName.get(i),
//                    imageNames.get(i),
//                    audioDownLoadUri.get(i),
//                    imageDownLoadUri.get(i),
//                    this.sampleDetailsRepository.save(new SampleDetails(
//                            genres.get(i),
//                            trackTypes.get(i),
//                            songKeys.get(i),
//                            regions.get(i),
//                            audioDescriptions.get(i)
//                    )),
//                    authenticatedUser));
//            samples.add(savedSample);
//
//            //samples.add(new Sample(artistNames[i], sampleTitles[i], audioFileUris.get(i), imageFileUris.get(i), authenticatedUser));
//        }
//    }

    @GetMapping("/api/samplepool/messages")
    public void messages() {

        System.out.println("Frontend messages Backend succesfully");
    }

}

