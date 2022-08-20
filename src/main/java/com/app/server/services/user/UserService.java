package com.app.server.services.user;

import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.audio.AudioService;
import com.app.server.services.security.KeycloakService;
import com.sun.xml.bind.v2.TODO;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AudioService audioService;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistAliasRepository artistAliasRepository;

    public User findAuthenticatedUser(KeycloakAuthenticationToken authenticationToken) {
       String principal = authenticationToken.getAccount().getPrincipal().getName();
         //String principal = "0dad8f18-60e3-4871-b91c-eb600ae1413c";
//        String principal ="  "
//        String downloaderID = client.getUser(user.getName()).getId();
//        String downloaderID = "a9d1a4dd-83fd-4d22-88db-9a6dbe48628a";
        Optional<User> optUser = userRepository.findById(principal);
        User user;
        if(!optUser.isPresent()) {
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
        if (!optArtist.isPresent()) {
            throw new NullPointerException("Downloader User is null");
        } else {
            artist = optArtist.get();
        }
        return artist;
    }

//    public List<Artist> findArtistsByLicenseType(LicenseType licenseType) {
//        List<Artist> artistList = artistRepository.findAll();
//        artistList.stream().filter(artist -> {
//            List<Sample> samplesList = audioService.findSamplesByArtist(artist);
//            if(audioService.filterSamplesByLicenseType(licenseType, samplesList).size() > 0) {
//                return true;
//            } else {
//                return false;
//            }
//        }).collect(Collectors.toList());
//        return artistList;
//    }

    public List<ArtistAlias> findArtistAliasByArtists(List<Artist> artistList) {
        List<ArtistAlias> artistAliasList = new ArrayList<>();
                ;
        artistList.stream().forEach(artist -> {
            Optional<List<ArtistAlias>> optionalArtistList = artistAliasRepository.findByArtist(artist);
            if(optionalArtistList.isPresent()) {
                artistAliasList.addAll(optionalArtistList.get());
            }

        });
        return artistAliasList;
    }


    public Artist tryCreateArtist(KeycloakAuthenticationToken authentication) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();


        // TODO: 19.08.2022 Rollencheck einfügen
        /* if (!keycloakService.hasArtistRole(account)) {
            //Assign new Role To User
            this.keycloakService.addRole(authentication.getPrincipal().toString(), "app-admin");
        } */

        User user = userService.findAuthenticatedUser(authentication);
        if(user == null) {
            throw new IllegalStateException("Cannot create Artist because User Account doensnt exist.");
        }

        Artist artist = userService.findArtist(authentication);
        if(artist == null) {
            artist = artistRepository.save(new Artist(user));
        }

        return artist;
    }






}
