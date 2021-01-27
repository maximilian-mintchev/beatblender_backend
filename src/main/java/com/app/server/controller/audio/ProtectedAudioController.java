package com.app.server.controller.audio;

import com.app.server.enums.AudioUnitType;
import com.app.server.enums.ResponseStatus;
import com.app.server.messages.response.ResponseMessage;
import com.app.server.model.audio.*;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.fileStorage.FileStorageService;
import com.app.server.services.security.KeycloakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private KeycloakService keycloakService;

    //    @CrossOrigin(origins = "http://localhost:9090")
//    @RolesAllowed({"beatblender-admin", "beatblender-user"})
    @PostMapping("/upload-samples")
    public ResponseEntity<?> uploadSamples(
            @RequestParam("audioUnitType") AudioUnitType audioUnitType,
            @RequestParam("artistAlias") String artistAliasString,
            @RequestParam("sampleTitle") String sampleTitle,
            @RequestParam("tempo") int tempo,
            @RequestParam("genre") String genre,
            @RequestParam("moods") Set<String> moods,
            @RequestParam("tags") Set<String> tags,
            @RequestParam("file") MultipartFile audioFile,
            @RequestParam("sampleImage") MultipartFile sampleImage
//             Use this Token as Auth Token
//            KeycloakAuthenticationToken authentication
    ) {
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
//        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
//        AccessToken token = account.getKeycloakSecurityContext().getToken();


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

        //Assign new Role To User
//        this.keycloakService.addRole(authentication.getPrincipal().toString(), "app-admin");

        //Mock authenticated user instead of using a token
        String principal = "046fcc75-58c4-4492-b9bb-5b84a396e760";
        String email = "user3@user3.com";
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
            artist = artistRepository.save(new Artist("Erika", "Musterfrau", LocalDate.now(),authenticatedUser));
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
        fileStorageService.storeFile(audioFile, email);
        fileStorageService.storeFile(sampleImage, email);
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
                            artistAlias
                    )
            );
        }
        switch (audioUnitType) {
            case Sample:
                sampleRepository.save(new Sample(
                                audioUnit
                        )
                );
                break;
            case Track:
                trackRepository.save(
                        new Track(
                                audioUnit
                        )
                );
                break;

        }
        return ResponseEntity.ok(
                new ResponseMessage(ResponseStatus.Success, "Ok")
        );
//        String audioFileName = fileStorageService.storeFile(audioFile, authenticatedUser.getBasicUserName());
//        String imageFileName = fileStorageService.storeFile(sampleImage, authenticatedUser.getBasicUserName());

    }
}
