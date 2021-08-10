package com.app.server.controller.audio;

import com.app.server.enums.AudioUnitType;
import com.app.server.enums.ResponseStatus;
import com.app.server.messages.response.MyUploadsResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

    @GetMapping("/get-uploads")
    public ResponseEntity<MyUploadsResponse> getUploads(
            KeycloakAuthenticationToken authentication
    ) {
//        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
//        AccessToken token = account.getKeycloakSecurityContext().getToken();
//        String principal = authentication.getPrincipal().toString();
        Artist artist = userService.findArtist(authentication);
        List<Track> tracks = audioService.findTracksByArtist(artist);
        List<Sample> samples = audioService.findSamplesByArtist(artist);
        Set<Set<Object>> sets = new HashSet<>();
        for (Track track: tracks) {
            for(Sample sample: samples) {
                if(track.getAudioUnit().equals(sample.getAudioUnit())) {
                    tracks = tracks.stream().filter(track1 -> !track1.equals(track)).collect(Collectors.toList());
                    samples = samples.stream().filter(sample1 -> !sample1.equals(sample)).collect(Collectors.toList());
                    Set<Object> sampleTrackSet = new HashSet<>();
                    sampleTrackSet.add(sample);
                    sampleTrackSet.add(track);
                    sets.add(sampleTrackSet);
                }
            }
//        if(samples.stream().map(sample -> sample.getAudioUnit()).collect(Collectors.toList()).contains(track)) {
//
//        }

        }

        MyUploadsResponse myUploadsResponse = new MyUploadsResponse(sets, samples, tracks);
        return ResponseEntity.ok(myUploadsResponse);
//        tracks.stream().filter((track -> {
////            AtomicReference<Boolean> isTrack = null;
////            samples.stream().map((sample -> sample.getAudioUnit())).filter((audioUnit -> {
////                if(audioUnit.equals(track.getAudioUnit())) {
////                    isTrack.set(true);
////                    return true;
////                } else {
////                    isTrack.set(true);
////                    return false;
////                }
////            }));
//        }));
//        return null;
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
            @RequestParam("samplePrice") int price,
            @RequestParam("file") MultipartFile audioFile,
            @RequestParam("sampleImage") MultipartFile sampleImage,
            @RequestParam("mixedInID") Set<String> mixedInIDs,

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
        if(!keycloakService.hasArtistRole(account)) {
        //Assign new Role To User
        this.keycloakService.addRole(authentication.getPrincipal().toString(), "app-admin");
        }

        //Mock authenticated user instead of using a token
//        String principal = "046fcc75-58c4-4492-b9bb-5b84a396e760";
//        String email = "user3@user3.com";
        String principal = authentication.getPrincipal().toString();
        if(principal.isEmpty()) {
            throw new NullPointerException("Principal is null");
        }
        String email = token.getEmail();
        if(email.isEmpty()) {
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
        if (optUser.isEmpty()) {
            //Retrieve email from Token with token.getEmail()
            authenticatedUser = userRepository.save(new User(principal, email));
            userDataPath = fileStorageService.createUserDirectory(authenticatedUser.getUuid());
        } else {
            authenticatedUser = optUser.get();
        }
        Optional<Artist> optionalArtist = artistRepository.findByUser(authenticatedUser);
        if (optionalArtist.isPresent()) {
            artist = optionalArtist.get();
        } else {
            artist = artistRepository.save(new Artist("Erika", "Musterfrau", LocalDate.now(), authenticatedUser));
        }
        if (moods == null) {
            throw new NullPointerException();
        }
        if (moods.isEmpty()) {
            throw new IllegalArgumentException();
        }
        //Save new Artist Alias
        artistAlias = new ArtistAlias(artistAliasString, artist);
        artistAliasRepository.save(artistAlias);

        // Store Sample Image and File
        //Retrieve email from Token with token.getEmail()
        String audioFileName = fileStorageService.storeFile(audioFile, authenticatedUser);
        String imageFileName = fileStorageService.storeFile(sampleImage, authenticatedUser);
        AudioUnit audioUnit;
        Optional<AudioUnit> optionalAudioUnit = audioUnitRepository.findByArtistAlias(artistAlias);



        if (optionalAudioUnit.isPresent()) {
            audioUnit = optionalAudioUnit.get();
        } else {
            audioUnit = audioUnitRepository.save(
                    new AudioUnit(
                            artist,
                            sampleTitle,
                            genre,
                            tempo,
                            moods,
                            tags,
                            audioFileName,
                            imageFileName,
                            price,
                            artistAlias
                    )
            );
        }
        if(mixedInIDs != null) {

        Set<AudioUnit> mixedIns = new HashSet<AudioUnit>();
        mixedInIDs.stream().forEach(mixedInID -> {

            Optional<Sample> optionalMixedIn = sampleRepository.findById(mixedInID);
            AudioUnit mixedIn;
            if(optionalMixedIn.isPresent()) {
                mixedIn = optionalMixedIn.get().getAudioUnit();
                mixedIns.add(mixedIn);
            } else {
                throw new NullPointerException("MixedIn does not exist");
            }

        });

        mixedIns.stream().forEach(audioUnitMixedIn -> {
            MixedIn mixedIn = new MixedIn(audioUnit, audioUnitMixedIn);
            mixedInRepository.save(mixedIn);
        });
        }

        if(sampleType) {
                            sampleRepository.save(new Sample(
                                audioUnit
                        )
                );
        }
        if(trackType) {
                            trackRepository.save(
                        new Track(
                                audioUnit
                        )
                );
        }
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

    @PatchMapping("/update-title")
    public ResponseEntity<Object> updateTitle(
            @RequestParam("title") String title,
            @RequestParam("audioUnitID") String audioUnitID,
//             Use this Token as Auth Token
            KeycloakAuthenticationToken authentication
    ) {
        Optional<AudioUnit> optAudioUnit = audioUnitRepository.findByAudioUnitID(audioUnitID);
        AudioUnit audioUnit;
        if(optAudioUnit.isPresent()) {
            audioUnit = optAudioUnit.get();
            audioUnit.setTitle(title);
            audioUnitRepository.save(audioUnit);
        }
        else {
            throw new NullPointerException();
        }
        Optional<Track> optionalTrack = audioService.findTrackByAudioUnit(audioUnit);
        Optional<Sample> optionalSample = audioService.findSampleByAudioUnit(audioUnit);

        if(optionalTrack.isPresent() && optionalSample.isPresent()) {
            Set<Object> sampleTrackSet = new HashSet<>();
            sampleTrackSet.add(optionalSample.get());
            sampleTrackSet.add(optionalTrack.get());
            return ResponseEntity.ok(sampleTrackSet);
        } else if (optionalSample.isPresent()) {
            return ResponseEntity.ok(optionalSample.get());
        } else if(optionalTrack.isPresent()) {
            return ResponseEntity.ok(optionalTrack.get());
        } else {
            throw new NullPointerException("Update Attribute Error: No Sample or Track found");
        }
    }

    @PatchMapping("/update-tempo")
    public ResponseEntity<Object> updateTempo(
            @RequestParam("tempo") Integer tempo,
            @RequestParam("audioUnitID") String audioUnitID,
//             Use this Token as Auth Token
            KeycloakAuthenticationToken authentication
    ) {
        Optional<AudioUnit> optAudioUnit = audioUnitRepository.findByAudioUnitID(audioUnitID);
        AudioUnit audioUnit;
        if(optAudioUnit.isPresent()) {
            audioUnit = optAudioUnit.get();
            audioUnit.setTempo(tempo);
            audioUnitRepository.save(audioUnit);
        }
        else {
            throw new NullPointerException();
        }
        Optional<Track> optionalTrack = audioService.findTrackByAudioUnit(audioUnit);
        Optional<Sample> optionalSample = audioService.findSampleByAudioUnit(audioUnit);

        if(optionalTrack.isPresent() && optionalSample.isPresent()) {
            Set<Object> sampleTrackSet = new HashSet<>();
            sampleTrackSet.add(optionalSample.get());
            sampleTrackSet.add(optionalTrack.get());
            return ResponseEntity.ok(sampleTrackSet);
        } else if (optionalSample.isPresent()) {
            return ResponseEntity.ok(optionalSample.get());
        } else if(optionalTrack.isPresent()) {
            return ResponseEntity.ok(optionalTrack.get());
        } else {
            throw new NullPointerException("Update Attribute Error: No Sample or Track found");
        }
    }


    @PatchMapping("/update-genre")
    public ResponseEntity<Object> updateGenre(
            @RequestParam("genre") String genre,
            @RequestParam("audioUnitID") String audioUnitID,
//             Use this Token as Auth Token
            KeycloakAuthenticationToken authentication
    ) {
        Optional<AudioUnit> optAudioUnit = audioUnitRepository.findByAudioUnitID(audioUnitID);
        AudioUnit audioUnit;
        if(optAudioUnit.isPresent()) {
            audioUnit = optAudioUnit.get();
            audioUnit.setGenre(genre);
            audioUnitRepository.save(audioUnit);
        }
        else {
            throw new NullPointerException();
        }
        Optional<Track> optionalTrack = audioService.findTrackByAudioUnit(audioUnit);
        Optional<Sample> optionalSample = audioService.findSampleByAudioUnit(audioUnit);

        if(optionalTrack.isPresent() && optionalSample.isPresent()) {
            Set<Object> sampleTrackSet = new HashSet<>();
            sampleTrackSet.add(optionalSample.get());
            sampleTrackSet.add(optionalTrack.get());
            return ResponseEntity.ok(sampleTrackSet);
        } else if (optionalSample.isPresent()) {
            return ResponseEntity.ok(optionalSample.get());
        } else if(optionalTrack.isPresent()) {
            return ResponseEntity.ok(optionalTrack.get());
        } else {
            throw new NullPointerException("Update Attribute Error: No Sample or Track found");
        }
    }



}
