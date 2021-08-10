package com.app.server.controller.license;


import com.app.server.enums.ResponseStatus;
import com.app.server.exceptions.FileStorageException;
import com.app.server.messages.response.FullLicenseResponse;
import com.app.server.messages.response.SampleResponse;
import com.app.server.messages.response.TrackResponse;
import com.app.server.model.audio.*;
import com.app.server.model.license.BasicLicense;
import com.app.server.model.license.FullLicense;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.MixedInRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import com.app.server.repository.license.BasicLicenseRepository;
import com.app.server.repository.license.FullLicenseRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.audio.AudioService;
import com.app.server.services.license.LicenseService;
import com.app.server.services.pdf.PDFService;
import com.app.server.services.user.UserService;
import javassist.NotFoundException;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private AudioUnitRepository audioUnitRepository;

    @Autowired
    private TrackRepository trackRepository;


    @Autowired
    private LicenseService licenseService;

    @Autowired
    private AudioService audioService;


    @Autowired
    private UserService userService;

    @Autowired
    private PDFService pdfService;

//    @RolesAllowed("beatblender-artist")
    @PostMapping("/full-license/withdraw-extension-option")
//    @Transactional
    public ResponseEntity downloadFullLicense(
            KeycloakAuthenticationToken authentication,
            @RequestParam(value = "trackID") String trackID
    ) {

        User uploader;
        Artist downloaderArtist = null;
        User downloaderUser;
        Optional<User> optDownloaderUser= null;
        Optional<Artist> optionalDownloaderArtist;
        Track track;
        Optional<BasicLicense> optBasicLicense;
        BasicLicense basicLicense;
        String basicLicensePath;

        String downloaderID = authentication.getAccount().getPrincipal().getName();
//        String downloaderID = "0dad8f18-60e3-4871-b91c-eb600ae1413c";

//        String downloaderID = client.getUser(user.getName()).getId();
//        String downloaderID = "a9d1a4dd-83fd-4d22-88db-9a6dbe48628a";
        optDownloaderUser = userRepository.findById(downloaderID);
        if(optDownloaderUser.isEmpty()) {
            throw new NullPointerException();
        } else {
            downloaderUser = optDownloaderUser.get();
        }
        optionalDownloaderArtist = artistRepository.findByUser(downloaderUser);
        if (optDownloaderUser.isEmpty()) {
            throw new NullPointerException("Downloader User is null");
        } else {
            downloaderArtist = optionalDownloaderArtist.get();
        }
        Optional<Track> optionalTrack = trackRepository.findById(trackID);
        if (optionalTrack.isEmpty()) {
            throw new NullPointerException("Sample is null");
        } else {
            track = optionalTrack.get();
        }
//        if (downloaderArtist.getArtistID().equals(track.getAudioUnit().getArtistAlias().getArtist().getArtistID())) {
//            throw new IllegalStateException("You cannot get a license for your own Track.");
//        }

        List<BasicLicense> basicLicenseList = licenseService.findBasicLicensesByTrack(track);
        Integer fullExtensionPrice = basicLicenseList.stream().map(license -> license.getLep()).collect(Collectors.summingInt(Integer::intValue));

        Optional<FullLicense> optionalFullLicense = fullLicenseRepository.findByTrack(track);
        if(optionalFullLicense.isEmpty()) {
            pdfService.createFullLicensePDF(track);
            fullLicenseRepository.save(new FullLicense(track));
            downloaderArtist.setLep(downloaderArtist.getLep() - fullExtensionPrice);
            artistRepository.save(downloaderArtist);
        } else {
            throw new IllegalStateException("You already own a Full License");
        }



//        if (downloaderArtist.getLep() < fullExtensionPrice) {
//            throw new IllegalStateException("Downloader has not enough LEP");
//        }



//        optBasicLicense = basicLicenseRepository.findByDownloaderAndSample(downloaderArtist.getUser(), sample);
//        if (optBasicLicense.isPresent()) {
//            basicLicense = optBasicLicense.get();
//        } else {
//            throw new NullPointerException();
//        }


        /*FullLicense fullLicense = fullLicenseRepository.save(new FullLicense(
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
        ));*/
//        basicLicenseRepository.save(basicLicense);

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
//    @CrossOrigin(origins = "http://localhost:9090")
    @PostMapping("/basic-license/get-basic-license")
    public ResponseEntity downloadBasicLicense(
            KeycloakAuthenticationToken authentication,
            @RequestParam("sampleID") String sampleID
//            @RequestParam("downloaderID") String downloaderID,
    ) {
        // todo paramter muss ArtistID sein;

        Artist uploaderArtist;
        User downloaderUser;
        Sample sample;
        BasicLicense basicLicense;
        String basicLicensePath;
        String downloaderID = authentication.getAccount().getPrincipal().getName();
//        String downloaderID = authentication.getAccount().getPrincipal().getName();
//        String downloaderID = client.getUser(user.getName()).getId();
//        String downloaderID = "a9d1a4dd-83fd-4d22-88db-9a6dbe48628a";

        Optional<Sample> optionalSample = sampleRepository.findById(sampleID);
        if (optionalSample.isEmpty()) {
            throw new NullPointerException();
        } else {
            sample = optionalSample.get();
        }
        String uploaderArtistID = sample.getAudioUnit().getCreator().getArtistID();
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot retrieve a license for your own sample.", new IllegalStateException());
//            return ResponseEntity.ok(new ResponseMessage(ResponseStatus.Fail, "You don't have enough License Points for a Full License!"));

//            throw new IllegalArgumentException("You cannot retrieve a license for your own sample");
        }

        Optional<BasicLicense> basicLicenseOptional = basicLicenseRepository.findByDownloaderAndSample(downloaderUser, sample);
        if (basicLicenseOptional.isEmpty()) {
            uploaderArtist.increaseLep();
            pdfService.createBasicLicensePDF(uploaderArtist, downloaderUser, sample);
            basicLicense = basicLicenseRepository.save(new BasicLicense(
                    downloaderUser, sample
            ));
            return ResponseEntity.ok(ResponseStatus.Success);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already owns a Basic License for the sample", new IllegalStateException());
//            throw new FileStorageException("User already owns a Basic License for the sample");
        }
    }


    @GetMapping("/basic-license/get-all")
    public ResponseEntity<List<BasicLicense>> downloadBasicLicense(
            KeycloakAuthenticationToken authentication
//            @RequestParam("sampleID") String sampleID
//            @RequestParam("downloaderID") String downloaderID,
    ) {

        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        String principal = authentication.getPrincipal().toString();
        Optional<User> optUser;
        User authenticatedUser;

        optUser = userRepository.findById(principal);
        if (optUser.isEmpty()) {
            throw new NullPointerException("User does not exist");
        } else {
            authenticatedUser = optUser.get();
        }
        Optional<Artist> optionalArtist = artistRepository.findByUser(authenticatedUser);
        Artist artist;

        if (optionalArtist.isPresent()) {
            artist = optionalArtist.get();
        } else {
            throw new NullPointerException("Artist does not exist");
//            artist = artistRepository.save(new Artist("Erika", "Musterfrau", LocalDate.now(), authenticatedUser));
        }

        Optional<List<BasicLicense>> optBasicLicenseSet =  basicLicenseRepository.findByDownloader(authenticatedUser);
        List<BasicLicense> basicLicenseSet;
        if(optBasicLicenseSet.isPresent()) {
            basicLicenseSet = optBasicLicenseSet.get();
            return ResponseEntity.ok(basicLicenseSet);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesnt have Basic Licenses", new IllegalStateException("User doesnt have Basic Licenses"));
        }


    }

    @GetMapping("/basic-license/get-all-unextended-tracks")
    public ResponseEntity<List<TrackResponse>> getAllUnextendedTracks(
            KeycloakAuthenticationToken authentication
    ) {
        Artist artist = userService.findArtist(authentication);
        List<Track> trackList = audioService.findTracksByArtist(artist);
        trackList = licenseService.findUnextendedTracks(trackList);
        List<TrackResponse> trackResponseList = licenseService.createTrackResponse(trackList);
        return ResponseEntity.ok(trackResponseList);
        //        }
    }


    @GetMapping("/basic-license/get-all-tracks")
    public ResponseEntity<List<TrackResponse>> getAllTracks(
            KeycloakAuthenticationToken authentication
            ) {
//        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
//        AccessToken token = account.getKeycloakSecurityContext().getToken();
//        String principal = authentication.getPrincipal().toString();
////        String principal = "0dad8f18-60e3-4871-b91c-eb600ae1413c";
//
//        Optional<User> optUser;
//        User authenticatedUser;
//
//        optUser = userRepository.findById(principal);
//        if (optUser.isEmpty()) {
//            throw new NullPointerException("User does not exist");
//        } else {
//            authenticatedUser = optUser.get();
//        }
//        Optional<Artist> optionalArtist = artistRepository.findByUser(authenticatedUser);
//        Artist artist;
//        if (optionalArtist.isPresent()) {
//            artist = optionalArtist.get();
//        } else {
//            throw new NullPointerException("Artist does not exist");
////            artist = artistRepository.save(new Artist("Erika", "Musterfrau", LocalDate.now(), authenticatedUser));
        Artist artist = userService.findArtist(authentication);
//        }
        List<Track> trackList = audioService.findTracksByArtist(artist);
//        Optional<List<AudioUnit>> optAudioUnits = audioUnitRepository.findByCreator(artist);
//        List<AudioUnit> audioUnits;
//        if(optAudioUnits.isPresent()) {
//            audioUnits = optAudioUnits.get();
//        } else {
//            throw new NullPointerException("No AudioUnits found");
//        }
//        List<TrackResponse> responseList = new ArrayList<>();

//        List<Track> trackList = new ArrayList<>();
//        audioUnits.stream().forEach(audioUnit -> {
//            Optional<Track> optTrack = trackRepository.findByAudioUnit(audioUnit);
//            if(optTrack.isPresent()) {
//                trackList.add(optTrack.get());
//            }
//        });
        List<TrackResponse> trackResponseList = licenseService.createTrackResponse(trackList);
//        trackList.stream().forEach(track -> {
//            List<BasicLicense> basicLicenseList = licenseService.findBasicLicensesByTrack(track);
//            responseList.add(new TrackResponse(track, basicLicenseList));
//        });

        return new ResponseEntity<List<TrackResponse>>(trackResponseList, HttpStatus.OK);







//        System.out.println(audioUnits.size());



//        return null;
    }

    @GetMapping("/full-license/get-full-licenses")
    public ResponseEntity<List<FullLicenseResponse>> getFullLicenses (
            KeycloakAuthenticationToken authentication
    ) {
//        String principal = authentication.getPrincipal().toString();
        Artist artist = userService.findArtist(authentication);
        List<FullLicense> fullLicenseList = licenseService.findFullLicensesByArtist(artist);
        List<FullLicenseResponse> fullLicenseResponses = new ArrayList<>();
        fullLicenseList.stream().forEach((fullLicense -> {
            List<BasicLicense> basicLicenseList = licenseService.findBasicLicensesByTrack(fullLicense.getTrack());

//            List<MixedIn> mixedIns = licenseService.findMixedInsByTrack(fullLicense.getTrack());
            fullLicenseResponses.add(new FullLicenseResponse(fullLicense, basicLicenseList));
        }));

        return ResponseEntity.ok(fullLicenseResponses);
    }

      /*  return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);*/


}
