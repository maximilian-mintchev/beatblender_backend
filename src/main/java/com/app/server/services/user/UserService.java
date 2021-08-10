package com.app.server.services.user;

import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public User findAuthenticatedUser(KeycloakAuthenticationToken authenticationToken) {
       String principal = authenticationToken.getAccount().getPrincipal().getName();
         //String principal = "0dad8f18-60e3-4871-b91c-eb600ae1413c";
//        String principal ="  "
//        String downloaderID = client.getUser(user.getName()).getId();
//        String downloaderID = "a9d1a4dd-83fd-4d22-88db-9a6dbe48628a";
        Optional<User> optUser = userRepository.findById(principal);
        User user;
        if(optUser.isEmpty()) {
            throw new NullPointerException("User not found.");
        } else {
            user = optUser.get();
        }
        return user;
    }

    public Artist findArtist(KeycloakAuthenticationToken authenticationToken) {
        User user = findAuthenticatedUser(authenticationToken);
        Optional<Artist> optArtist;
        Artist artist;
        optArtist = artistRepository.findByUser(user);
        if (optArtist.isEmpty()) {
            throw new NullPointerException("Downloader User is null");
        } else {
            artist = optArtist.get();
        }
        return artist;
    }




}
