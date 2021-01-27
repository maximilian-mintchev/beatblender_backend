package com.app.server.controller.license;


import com.app.server.enums.ResponseStatus;
import com.app.server.exceptions.FileStorageException;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Time;
import com.app.server.model.audio.TimeSnippet;
import com.app.server.model.license.BasicLicense;
import com.app.server.model.license.FullLicense;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.license.BasicLicenseRepository;
import com.app.server.repository.license.FullLicenseRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.pdf.PDFService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/web/protected/license")
public class ProtectedLicenseController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private BasicLicenseRepository basicLicenseRepository;

    @Autowired
    private FullLicenseRepository fullLicenseRepository;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private PDFService pdfService;

//    @RolesAllowed("beatblender-artist")
    @PostMapping("/full-license/get-full-license")
//    @Transactional
    public ResponseEntity downloadFullLicense(
//            KeycloakAuthenticationToken authentication,
            @RequestParam("sampleID") String sampleID
    ) {

        User uploader;
        Artist downloaderArtist = null;
        User downloaderUser;
        Optional<User> optDownloaderUser= null;
        Optional<Artist> optionalDownloaderArtist;
        Sample sample;
        Optional<BasicLicense> optBasicLicense;
        BasicLicense basicLicense;
        String basicLicensePath;


//        String downloaderID = client.getUser(user.getName()).getId();
        String downloaderID = "a9d1a4dd-83fd-4d22-88db-9a6dbe48628a";
        Optional<User> optDownloader = userRepository.findById(downloaderID);
        if(optDownloaderUser.isEmpty()) {
            throw new NullPointerException();
        } else {
            downloaderUser = optDownloader.get();
        }
        optionalDownloaderArtist = artistRepository.findById(downloaderArtist.getArtistID());
        if (optDownloaderUser.isEmpty()) {
            throw new NullPointerException("Downloader User is null");
        } else {
            downloaderArtist = optionalDownloaderArtist.get();
        }
        Optional<Sample> optionalSample = sampleRepository.findById(sampleID);
        if (optionalSample.isEmpty()) {
            throw new NullPointerException("Sample is null");
        } else {
            sample = optionalSample.get();
        }
        if (downloaderArtist.getArtistID().equals(sample.getAudioUnit().getArtistAlias().getArtist().getArtistID())) {
            throw new IllegalStateException("You cannot get a license for your own samples.");
        }

        optBasicLicense = basicLicenseRepository.findByDownloaderAndSample(downloaderArtist.getUser(), sample);
        if (optBasicLicense.isPresent()) {
            basicLicense = optBasicLicense.get();
        } else {
            throw new NullPointerException();
        }
        if (downloaderArtist.getLep() < basicLicense.getLep()) {
            throw new IllegalStateException("Downloader has not enough LEP");
        }

        downloaderArtist.setLep(downloaderArtist.getLep() - basicLicense.getLep());
        FullLicense fullLicense = fullLicenseRepository.save(new FullLicense(
                sample.getAudioUnit(),
                Arrays.asList(
                        new TimeSnippet(
                                new Time(
                                        0, 0, 24
                                ),
                                new Time(
                                        0, 0, 34
                                )
                        ),
                        new TimeSnippet(
                                new Time(
                                        0, 0, 59
                                ),
                                new Time(0, 1, 23)
                        )
                )
        ));
        artistRepository.save(downloaderArtist);
        basicLicenseRepository.save(basicLicense);

        return ResponseEntity.ok(ResponseStatus.Success);
//        if (optDownloader.isPresent()) {
//            downloader = optDownloader.get();
//            optBasicLicense = basicLicenseRepository.findByDownloaderIDAndSampleID(downloader.getId(), sampleID);
//            if (optBasicLicense.isPresent()) {
//                basicLicense = optBasicLicense.get();
//                if (basicLicense.getLicenseStatus().equals(LicenseStatus.BasicLicense)) {
//                    System.out.println("Creating Full License");
//                    if (downloader.getLicensePoints() >= basicLicense.getFullLicensePrice()) {
//                        basicLicense.setLicenseStatus(LicenseStatus.FullLicense);
//                        downloader.setLicensePoints(downloader.getLicensePoints() - basicLicense.getFullLicensePrice());
//                        basicUserRepository.save(downloader);
//                        basicLicenseRepository.save(basicLicense);
//                        return ResponseEntity.ok(new FullLicenseResponse(ResponseStatus.Success, downloader.getLicensePoints()));
//                    } else {
//                        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You don't have enough License Points for a Full License!"));
//                    }
//
//                }
//                if (basicLicense.getLicenseStatus().equals(LicenseStatus.FullLicense)) {
//                    return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You already have a Full License for this sample"));
//                }
//            } else {
//                return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "Before you can get a Full License you have to get a basic license"));
//            }
//        } else {
//            return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "Please create a Account In"));
//        }
//
//
//        return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "Something went wrong. Please try again"));
    }

    //    downloadBasicLicense
//    @RolesAllowed("{beatblender-user, beatblender-artist}")
    @PostMapping("/basic-license/get-basic-license")
    public ResponseEntity downloadBasicLicense(
//            KeycloakAuthenticationToken authentication,
            @RequestParam("uploaderArtistID") String uploaderArtistID,
            @RequestParam("sampleID") String sampleID
    ) {
        // todo paramter muss ArtistID sein;

        Artist uploaderArtist;
        User downloaderUser;
        Sample sample;
        BasicLicense basicLicense;
        String basicLicensePath;
//        String principal = authentication.getAccount().getPrincipal().getName();
//        String downloaderID = client.getUser(user.getName()).getId();
        String downloaderID = "a9d1a4dd-83fd-4d22-88db-9a6dbe48628a";
        Optional<Artist> optUploaderArtist = artistRepository.findById(uploaderArtistID);
        if (optUploaderArtist.isEmpty()) {
            throw new NullPointerException();
        } else {
            uploaderArtist = optUploaderArtist.get();
        }
        Optional<User> optDownloaderUser = userRepository.findById(downloaderID);
        if (optDownloaderUser.isEmpty()) {
            throw new NullPointerException("Downloader User is null");
        } else {
            downloaderUser = optDownloaderUser.get();
        }
        if (uploaderArtist.getUser().getUuid().equals(downloaderUser.getUuid())) {
            throw new IllegalArgumentException("You cannot retrieve a license for your own sample");
        }
        Optional<Sample> optionalSample = sampleRepository.findById(sampleID);
        if (optionalSample.isEmpty()) {
            throw new NullPointerException();
        } else {
            sample = optionalSample.get();
        }
        Optional<BasicLicense> basicLicenseOptional = basicLicenseRepository.findByDownloaderAndSample(downloaderUser, sample);
        if (basicLicenseOptional.isEmpty()) {
            uploaderArtist.increaseLep();
            pdfService.writePDF(uploaderArtist, downloaderUser, sample);
            basicLicense = basicLicenseRepository.save(new BasicLicense(
                    downloaderUser, sample
            ));
            return ResponseEntity.ok(ResponseStatus.Success);
        } else {
            throw new FileStorageException("User already owns a Basic License for the sample");
        }
    }

      /*  return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);*/


}
