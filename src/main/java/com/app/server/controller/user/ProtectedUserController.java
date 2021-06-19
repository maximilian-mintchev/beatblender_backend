package com.app.server.controller.user;


import com.app.server.messages.response.UserDataMessage;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/web/protected/user")
public class ProtectedUserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/user-data")
    public ResponseEntity<UserDataMessage> getUserData(
            KeycloakAuthenticationToken authentication
    ) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        String principal = authentication.getPrincipal().toString();
        if(principal.isEmpty()) {
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
        UserDataMessage userDataMessage = new UserDataMessage(artist);
        return ResponseEntity.ok(userDataMessage);
    }



}
