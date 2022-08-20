package com.app.server.controller.audio;

import com.app.server.enums.AudioUnitType;
import com.app.server.enums.ResponseStatus;
import com.app.server.messages.response.Release;
import com.app.server.messages.response.ResponseMessage;
import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.MixedIn;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.MixedInRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.audio.AudioService;
import com.app.server.services.fileStorage.FileStorageService;
import com.app.server.services.security.KeycloakService;
import com.app.server.services.user.UserService;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/web/protected/audio")
public class ProtectedAudioController {

    Logger logger = LoggerFactory.getLogger(ProtectedAudioController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistAliasRepository artistAliasRepository;

    @Autowired
    AudioUnitRepository audioUnitRepository;

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    MixedInRepository mixedInRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private UserService userService;

    @Autowired
    private AudioService audioService;

    @GetMapping("/get-samples-from-artist/{artistAliasID}")
    public ResponseEntity<Map<String, Object>> getSamplesFromArtistAlias(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @PathVariable("artistAliasID") String audioUnitID


    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(audioUnitID);
        Page<Sample> samplePage = null;
        if (optionalArtistAlias.isPresent()) {
            samplePage = audioService.findSamplesByArtistAlias(optionalArtistAlias.get(), pageable);
        } else {
            throw new NullPointerException("ArtistAlias with provided ID doesnt exist");
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("samples", samplePage.getContent());
        responseMap.put("currentPage", samplePage.getNumber());
        responseMap.put("totalItems", samplePage.getTotalElements());
        responseMap.put("totalPages", samplePage.getTotalPages());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);

    }

    @GetMapping("/get-releases")
    public ResponseEntity<List<Release>> getMyReleases(
            KeycloakAuthenticationToken authentication
    ) {
        Artist artist = userService.findArtist(authentication);
        Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(artist.getCurrentArtistAliasID());
        ArtistAlias artistAlias;
        if (optionalArtistAlias.isPresent()) {
            artistAlias = optionalArtistAlias.get();
        } else {
            throw new NullPointerException("ArtistAlias does not exist");
        }
        List<Release> releaseList = new ArrayList<>();
        Optional<List<Track>> optionalTracks = trackRepository.findByArtistAlias(artistAlias);
        List<Track> trackList;
        if (optionalTracks.isPresent()) {
            trackList = optionalTracks.get();
        } else {
            return ResponseEntity.ok(releaseList);
        }
        trackList.stream().forEach((track) -> {
            Optional<List<MixedIn>> optionalMixedIns = mixedInRepository.findAllByTrack(track);
            List<MixedIn> mixedIns = new ArrayList<>();
            if (optionalMixedIns.isPresent()) {
                mixedIns = optionalMixedIns.get();
            }
            List<Sample> sampleList;
            sampleList = mixedIns.stream().map((mixedIn) -> mixedIn.getSample()).collect(Collectors.toList());
            releaseList.add(new Release(
                    track,
                    sampleList
            ));
        });

        return ResponseEntity.ok(releaseList);

    }


//    @GetMapping("/get-uploads")
//    public ResponseEntity<MyUploadsResponse> getUploads(
//            KeycloakAuthenticationToken authentication
//    ) {
//
//        Artist artist = userService.findArtist(authentication);
//        List<Track> tracks = audioService.findTracksByArtist(artist);
//        List<Sample> samples = audioService.findSamplesByArtist(artist);
//        Set<Set<Object>> sets = new HashSet<>();
//        for (Track track : tracks) {
//            for (Sample sample : samples) {
//                if (track.getAudioUnit().equals(sample.getAudioUnit())) {
//                    tracks = tracks.stream().filter(track1 -> !track1.equals(track)).collect(Collectors.toList());
//                    samples = samples.stream().filter(sample1 -> !sample1.equals(sample)).collect(Collectors.toList());
//                    Set<Object> sampleTrackSet = new HashSet<>();
//                    sampleTrackSet.add(sample);
//                    sampleTrackSet.add(track);
//                    sets.add(sampleTrackSet);
//                }
//            }
//        }
//        MyUploadsResponse myUploadsResponse = new MyUploadsResponse(sets, samples, tracks);
//        return ResponseEntity.ok(myUploadsResponse);
//    }

    @PostMapping("/register-track")
    public ResponseEntity<?> registerTrack(
//            @RequestParam("audioUnitType") AudioUnitType audioUnitType,
//            @RequestParam("sampleType") Boolean sampleType,
//            @RequestParam("trackType") Boolean trackType,
//            @RequestParam("artistAlias") String artistAliasString,
            @RequestParam("releaseArtistName") String releaseArtistName,
            @RequestParam("releaseTitle") String releaseTitle,
            @RequestParam("licenseeName") String licenseeName,
            @RequestParam("mixedIns") List<String> mixedInIDs,
            @RequestParam("file") MultipartFile audioFile,
            @RequestParam("image") MultipartFile sampleImage,

//            @RequestParam("tempo") int tempo,
//            @RequestParam("genre") String genre,
//            @RequestParam("moods") Set<String> moods,
//            @RequestParam("tags") Set<String> tags,
//            @RequestParam("licenseType") String licenseTypeAsString,
//            @RequestParam("mixedInID") Set<String> mixedInIDs,

//             Use this Token as Auth Token
            KeycloakAuthenticationToken authentication
    ) {

        if (releaseTitle == null
                || licenseeName == null
                || releaseTitle == null
                || releaseArtistName == null
                || mixedInIDs == null
                || audioFile == null
                || sampleImage == null
//                        || authentication ==null
        ) {
            throw new NullPointerException("Param is null");
        }
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        // TODO: 19.08.2022 Rollencheck einfügen

        /* if (!keycloakService.hasArtistRole(account)) {
            //Assign new Role To User
            this.keycloakService.addRole(authentication.getPrincipal().toString(), "app-admin");
        } */



        Artist artist = userService.findArtist(authentication);
        User authenticatedUser = userService.findAuthenticatedUser(authentication);
        if (artist.equals(null)) {
            artist = artistRepository.save(new Artist(authenticatedUser));

        }
        Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(artist.getCurrentArtistAliasID());
        ArtistAlias artistAlias;
        if (optionalArtistAlias.isPresent()) {
            artistAlias = optionalArtistAlias.get();
        } else {
            throw new NullPointerException("ArtistAlias does not exist");
        }

//        String audioFileName = fileStorageService.storeTrackAudioFile(audioFile, artist);
  //      String imageFileName = fileStorageService.storeTrackImageFile(sampleImage, artist);

//        String audioFileName = fileStorageService.storeFile(audioFile, artist.getUser());
  //      String imageFileName = fileStorageService.storeFile(sampleImage, artist.getUser());
        String audioFileName = fileStorageService.uploadFile(audioFile);
        String imageFileName = fileStorageService.uploadFile(sampleImage);


        AudioUnit audioUnit = audioUnitRepository.save(
                new AudioUnit(
                        artistAlias,
                        releaseTitle,
                        audioFileName,
                        imageFileName
                )
        );

        Track track = trackRepository.save(
                new Track(
                        audioUnit,
                        releaseArtistName,
                        releaseTitle,
                        licenseeName
                )
        );
//        List<Sample> sampleList = new ArrayList<>();
        mixedInIDs.stream().forEach((mixedInID) -> {
            Optional<Sample> optionalSample = sampleRepository.findById(mixedInID);
            if (optionalSample.isPresent()) {
                Sample sample = optionalSample.get();
                MixedIn mixedIn = mixedInRepository.save(
                        new MixedIn(
                                sample,
                                track
                        )
                );

//                sampleList.add(sample);
            } else {
                throw new NullPointerException("Sample doesnt exist");
            }
        });

        return ResponseEntity.ok(
                new ResponseMessage(ResponseStatus.Success, "Ok")
        );

    }

    //    @CrossOrigin(origins = "http://localhost:9090")
//    @RolesAllowed({"beatblender-admin", "beatblender-user"})
    @PostMapping("/upload-samples")
    public ResponseEntity<?> uploadSamples(
            @RequestParam("audioUnitType") AudioUnitType audioUnitType,
            @RequestParam("sampleType") Boolean sampleType,
            @RequestParam("trackType") Boolean trackType,
            @RequestParam("artistAlias") String artistAliasString,
            @RequestParam("sampleTitle") String sampleTitle,
            @RequestParam("tempo") int tempo,
            @RequestParam("genre") String genre,
            @RequestParam("moods") Set<String> moods,
            @RequestParam("tags") Set<String> tags,
            @RequestParam("licenseType") String licenseTypeAsString,
            @RequestParam("file") MultipartFile audioFile,
            @RequestParam("sampleImage") MultipartFile sampleImage,
//            @RequestParam("mixedInID") Set<String> mixedInIDs,

//             Use this Token as Auth Token
            KeycloakAuthenticationToken authentication
    ) {
//        mixedInIDs.forEach(mixedInID -> {
//            System.out.println(mixedInID);
//        });

        if (audioUnitType == null
                || artistAliasString == null
                || sampleTitle == null
                || genre == null
                || moods == null
                || tags == null
                || audioFile == null
                || sampleImage == null
//                        || authentication ==null
        ) {
            throw new NullPointerException("Param is null");
        }
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        //        //Username, other way
//        logger.info(authentication.getPrincipal().toString());
//        //Email
//        logger.info(authentication.getCredentials().toString());
//        logger.info(authentication.toString());
//        logger.info(token.toString());


//        account.getRoles().stream().forEach((role) -> {
//            logger.info(role);
//        });
//        logger.info(token.getEmail());
//        logger.info("upload success");

        // TODO: 19.08.2022 Rollencheck einfügen
        /* if (!keycloakService.hasArtistRole(account)) {
            //Assign new Role To User
            this.keycloakService.addRole(authentication.getPrincipal().toString(), "app-admin");
        } */

        //Mock authenticated user instead of using a token
//        String principal = "046fcc75-58c4-4492-b9bb-5b84a396e760";
//        String email = "user3@user3.com";
        String principal = authentication.getPrincipal().toString();
        if (principal.isEmpty()) {
            throw new NullPointerException("Principal is null");
        }
        String email = token.getEmail();
        if (email.isEmpty()) {
            throw new NullPointerException("Email is null");
        }

        //get principal from auth token
//        String principal = (String) authentication.getPrincipal();

        Optional<User> optUser;
        User authenticatedUser;
        Artist artist;
        ArtistAlias artistAlias;
        Path userDataPath;

        optUser = userRepository.findById(principal);
        if (!optUser.isPresent()) {
            //Retrieve email from Token with token.getEmail()
            authenticatedUser = userRepository.save(new User(principal, email));
            //userDataPath = fileStorageService.createUserDirectory(authenticatedUser.getUuid());
        } else {
            authenticatedUser = optUser.get();
        }
        Optional<Artist> optionalArtist = artistRepository.findByUser(authenticatedUser);
        if (optionalArtist.isPresent()) {
            artist = optionalArtist.get();
        } else {
            artist = artistRepository.save(new Artist(authenticatedUser));
        }
        if (moods == null) {
            throw new NullPointerException();
        }
        if (moods.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //Save new Artist Alias
//        artistAlias = new ArtistAlias(artistAliasString, artist);
//        artistAliasRepository.save(artistAlias);
//        Optional<List<ArtistAlias>> optionalArtistAliasList = artistAliasRepository.findByArtist(artist);
        Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(artist.getCurrentArtistAliasID());
        if (optionalArtistAlias.isPresent()) {
//            List<ArtistAlias> artistAliasList = optionalArtistAliasList.get();
//            artistAlias = optionalArtistAliasList.get().stream().filter((artistAlias1) -> artistAlias1.getArtistName().equals(artistAliasString)).collect(Collectors.toList()).get(0);
            artistAlias = optionalArtistAlias.get();
        } else {
            throw new NullPointerException("No Artist Alias found");
        }


        //String audioFileName = fileStorageService.storeFile(audioFile, authenticatedUser);
        //String imageFileName = fileStorageService.storeFile(sampleImage, authenticatedUser);

        String audioFileName = fileStorageService.uploadFile(audioFile);
        String imageFileName = fileStorageService.uploadFile(sampleImage);


        AudioUnit audioUnit;
//        Optional<AudioUnit> optionalAudioUnit = audioUnitRepository.findByArtistAlias(artistAlias);


        audioUnit = audioUnitRepository.save(
                new AudioUnit(
                        artistAlias,
                        sampleTitle,
                        audioFileName,
                        imageFileName
                )
        );
//        if (optionalAudioUnit.isPresent()) {
//            audioUnit = optionalAudioUnit.get();
//        } else {
////            switch (licenseTypeAsString) {
////                case "BB-100":
////                    licenseType = LicenseType.BB100;
////                    break;
////                case "BB-70":
////                    licenseType = LicenseType.BB70;
////                    break;
////                case "BB-30":
////                    licenseType = LicenseType.BB30;
////                    break;
////                default:
////                    throw new IllegalArgumentException("Wrong LicenseType submitted");
////            }
////            audioUnit = audioUnitRepository.save(
////                    new AudioUnit(
////                            artist,
////                            sampleTitle,
////                            genre,
////                            tempo,
////                            licenseType,
////                            moods,
////                            tags,
////                            audioFileName,
////                            imageFileName,
////                            artistAlias
////                    )
////            );
//        }
//        if (mixedInIDs != null) {
//
//            Set<AudioUnit> mixedIns = new HashSet<AudioUnit>();
//            mixedInIDs.stream().forEach(mixedInID -> {
//
//                Optional<Sample> optionalMixedIn = sampleRepository.findById(mixedInID);
//                AudioUnit mixedIn;
//                if (optionalMixedIn.isPresent()) {
//                    mixedIn = optionalMixedIn.get().getAudioUnit();
//                    mixedIns.add(mixedIn);
//                } else {
//                    throw new NullPointerException("MixedIn does not exist");
//                }
//
//            });
//
//            mixedIns.stream().forEach(audioUnitMixedIn -> {
//                MixedIn mixedIn = new MixedIn(audioUnit, audioUnitMixedIn);
//                mixedInRepository.save(mixedIn);
//            });
//        }

        if (sampleType) {
            sampleRepository.save(new Sample(
                            audioUnit,
                            genre,
                            tempo,
                            moods,
                            tags
                    )
            );
        }
//        if (trackType) {
//            trackRepository.save(
//                    new Track(
//                            audioUnit
//                    )
//            );
//        }
//        switch (audioUnitType) {
//            case Sample:
//                sampleRepository.save(new Sample(
//                                audioUnit
//                        )
//                );
//                break;
//            case Track:
//                trackRepository.save(
//                        new Track(
//                                audioUnit
//                        )
//                );
//                break;
//
//        }
        return ResponseEntity.ok(
                new ResponseMessage(ResponseStatus.Success, "Ok")
        );
//        String audioFileName = fileStorageService.storeFile(audioFile, authenticatedUser.getBasicUserName());
//        String imageFileName = fileStorageService.storeFile(sampleImage, authenticatedUser.getBasicUserName());

    }

//    @PatchMapping("/update-title")
//    public ResponseEntity<Object> updateTitle(
//            @RequestParam("title") String title,
//            @RequestParam("audioUnitID") String audioUnitID,
//            KeycloakAuthenticationToken authentication
//    ) {
//        Optional<AudioUnit> optAudioUnit = audioUnitRepository.findByAudioUnitID(audioUnitID);
//        AudioUnit audioUnit;
//        if (optAudioUnit.isPresent()) {
//            audioUnit = optAudioUnit.get();
//            audioUnit.setTitle(title);
//            audioUnitRepository.save(audioUnit);
//        } else {
//            throw new NullPointerException();
//        }
//        Optional<Track> optionalTrack = audioService.findTrackByAudioUnit(audioUnit);
//        Optional<Sample> optionalSample = audioService.findSampleByAudioUnit(audioUnit);
//
//        if (optionalTrack.isPresent() && optionalSample.isPresent()) {
//            Set<Object> sampleTrackSet = new HashSet<>();
//            sampleTrackSet.add(optionalSample.get());
//            sampleTrackSet.add(optionalTrack.get());
//            return ResponseEntity.ok(sampleTrackSet);
//        } else if (optionalSample.isPresent()) {
//            return ResponseEntity.ok(optionalSample.get());
//        } else if (optionalTrack.isPresent()) {
//            return ResponseEntity.ok(optionalTrack.get());
//        } else {
//            throw new NullPointerException("Update Attribute Error: No Sample or Track found");
//        }
//    }

//    @PatchMapping("/update-tempo")
//    public ResponseEntity<Object> updateTempo(
//            @RequestParam("tempo") Integer tempo,
//            @RequestParam("audioUnitID") String audioUnitID,
//            KeycloakAuthenticationToken authentication
//    ) {
//        Optional<AudioUnit> optAudioUnit = audioUnitRepository.findByAudioUnitID(audioUnitID);
//        AudioUnit audioUnit;
//        if (optAudioUnit.isPresent()) {
//            audioUnit = optAudioUnit.get();
//            audioUnit.setTempo(tempo);
//            audioUnitRepository.save(audioUnit);
//        } else {
//            throw new NullPointerException();
//        }
//        Optional<Track> optionalTrack = audioService.findTrackByAudioUnit(audioUnit);
//        Optional<Sample> optionalSample = audioService.findSampleByAudioUnit(audioUnit);
//
//        if (optionalTrack.isPresent() && optionalSample.isPresent()) {
//            Set<Object> sampleTrackSet = new HashSet<>();
//            sampleTrackSet.add(optionalSample.get());
//            sampleTrackSet.add(optionalTrack.get());
//            return ResponseEntity.ok(sampleTrackSet);
//        } else if (optionalSample.isPresent()) {
//            return ResponseEntity.ok(optionalSample.get());
//        } else if (optionalTrack.isPresent()) {
//            return ResponseEntity.ok(optionalTrack.get());
//        } else {
//            throw new NullPointerException("Update Attribute Error: No Sample or Track found");
//        }
//    }


//    @PatchMapping("/update-genre")
//    public ResponseEntity<Object> updateGenre(
//            @RequestParam("genre") String genre,
//            @RequestParam("audioUnitID") String audioUnitID,
////             Use this Token as Auth Token
//            KeycloakAuthenticationToken authentication
//    ) {
//        Optional<AudioUnit> optAudioUnit = audioUnitRepository.findByAudioUnitID(audioUnitID);
//        AudioUnit audioUnit;
//        if (optAudioUnit.isPresent()) {
//            audioUnit = optAudioUnit.get();
//            audioUnit.setGenre(genre);
//            audioUnitRepository.save(audioUnit);
//        } else {
//            throw new NullPointerException();
//        }
//        Optional<Track> optionalTrack = audioService.findTrackByAudioUnit(audioUnit);
//        Optional<Sample> optionalSample = audioService.findSampleByAudioUnit(audioUnit);
//
//        if (optionalTrack.isPresent() && optionalSample.isPresent()) {
//            Set<Object> sampleTrackSet = new HashSet<>();
//            sampleTrackSet.add(optionalSample.get());
//            sampleTrackSet.add(optionalTrack.get());
//            return ResponseEntity.ok(sampleTrackSet);
//        } else if (optionalSample.isPresent()) {
//            return ResponseEntity.ok(optionalSample.get());
//        } else if (optionalTrack.isPresent()) {
//            return ResponseEntity.ok(optionalTrack.get());
//        } else {
//            throw new NullPointerException("Update Attribute Error: No Sample or Track found");
//        }
//    }


}
