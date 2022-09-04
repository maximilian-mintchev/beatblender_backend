package com.app.server.controller.user;


import com.app.server.enums.ResponseStatus;
import com.app.server.messages.response.ResponseMessage;
import com.app.server.messages.response.UserDataMessage;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.fileStorage.FileStorageService;
import com.app.server.services.security.KeycloakService;
import com.app.server.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/web/protected/user")
@Slf4j
public class ProtectedUserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    KeycloakService keycloakService;

    @Autowired
    UserService userService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ArtistAliasRepository artistAliasRepository;


    @GetMapping("/user-data")
    public ResponseEntity<UserDataMessage> getUserData(
            KeycloakAuthenticationToken authentication
    ) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        String principal = authentication.getPrincipal().toString();
        if (principal.isEmpty()) {
            throw new NullPointerException("Principal is null");
        }
        Optional<User> optUser;
        User authenticatedUser;
        optUser = userRepository.findById(principal);
        if (optUser.isEmpty()) {
            //Retrieve email from Token with token.getEmail()
            throw new NullPointerException("User is NUll");
        } else {
            authenticatedUser = optUser.get();
        }
        Optional<Artist> optionalArtist = artistRepository.findByUser(authenticatedUser);
        Artist artist;

        if (optionalArtist.isPresent()) {
            artist = optionalArtist.get();
        } else {
            throw new NullPointerException("Artist is NULL");
        }
        Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(artist.getCurrentArtistAliasID());
        ArtistAlias artistAlias;
        if (optionalArtist.isPresent()) {
            artistAlias = optionalArtistAlias.get();
        } else {
            throw new NullPointerException("Artist Alias is null");
        }
        UserDataMessage userDataMessage = new UserDataMessage(artistAlias);
        return ResponseEntity.ok(userDataMessage);
    }

    @PostMapping("/set-artist-image")
    public ResponseEntity<?> setArtistImage(
            @RequestParam("artistImage") MultipartFile artistImage,
            KeycloakAuthenticationToken authentication
    ) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        Artist artist;
        ArtistAlias artistAlias;
        artist = userService.findArtist(authentication);
        if (artist != null) {
            String currentArtistAliasID = artist.getCurrentArtistAliasID();
            if (currentArtistAliasID == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose your Artist Name before uploading your Image.", new IllegalStateException());
            } else {
                Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(currentArtistAliasID);
                if (optionalArtistAlias.isPresent()) {
                    artistAlias = optionalArtistAlias.get();
                    String artistImageFileName = fileStorageService.uploadFile(artistImage);

                    artistAlias.setArtistImageFileName(artistImageFileName);
                    artistAliasRepository.save(artistAlias);
                } else {
                    throw new NullPointerException("ArtistALias doesn't exist");
                }
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist is Null", new IllegalStateException());

        }

        return ResponseEntity.ok(
                new ResponseMessage(ResponseStatus.Success, "Ok")
        );

    }


    @PostMapping("/set-artist-name")
    public ResponseEntity<?> setArtistName(
            @RequestParam("artistName") String artistName,
            KeycloakAuthenticationToken authentication
    ) {
//        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
//        AccessToken token = account.getKeycloakSecurityContext().getToken();
        userService.tryCreateArtist(authentication);
        Artist artist;
        ArtistAlias artistAlias;
        artist = userService.findArtist(authentication);
        String currentArtistAliasID = artist.getCurrentArtistAliasID();

        if (currentArtistAliasID == null) {
            artistAlias = new ArtistAlias(artistName, artist);
        } else {
            Optional<ArtistAlias> optionalArtistAlias = artistAliasRepository.findById(currentArtistAliasID);
            if (optionalArtistAlias.isPresent()) {
                artistAlias = optionalArtistAlias.get();
                artistAlias.setArtistName(artistName);
            } else {
                throw new NullPointerException("ArtistALias doesn't exist");
            }
        }
        artistAliasRepository.save(artistAlias);
        artist.setCurrentArtistAliasID(artistAlias.getArtistALiasID());
        artistRepository.save(artist);

        return ResponseEntity.ok(
                new ResponseMessage(ResponseStatus.Success, "Ok")
        );
    }

    @PostMapping("/try-create-user")
    public ResponseEntity<?> tryCreateUser(
//            @RequestParam("artistName") String artistName,
            //KeycloakAuthenticationToken authentication
            Authentication authentication


    ) {
        System.out.println(authentication);
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        String principal = authentication.getPrincipal().toString();

         if (principal.isEmpty()) {
            throw new NullPointerException("Principal is null");
        }
        String email = token.getEmail();
        if (email.isEmpty()) {
            throw new NullPointerException("Email is null");
        }


        /*User user = userService.findAuthenticatedUser(authentication);
        if(user == null) {
            user = userRepository.save(new User(principal, email));
            // fileStorageService.createUserDirectory(user.getUuid());
        }*/
        return ResponseEntity.ok("Successfull Try");
    }

//
//    @PostMapping("/try-create-artist")
//    public ResponseEntity<?> tryCreateArtist(
////            @RequestParam("artistName") String artistName,
//            KeycloakAuthenticationToken authentication
//    ) {
//
//        return ResponseEntity.ok("Successfull Try");
//    }


}
