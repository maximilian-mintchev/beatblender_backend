package com.app.server.services;

import com.app.server.ServerApplication;
import com.app.server.enums.AudioUnitType;
import com.app.server.enums.LicenseType;
import com.app.server.exceptions.FileStorageException;
import com.app.server.model.audio.*;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.model.user.User;
import com.app.server.property.FileStorageProperties;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.repository.user.ArtistRepository;
import com.app.server.repository.user.UserRepository;
import com.app.server.services.security.KeycloakService;
import com.sun.xml.bind.v2.TODO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UtilityService {

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private UserRepository userRepository;

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

    Logger logger = LoggerFactory.getLogger(ServerApplication.class);


    private final Path fileStorageLocation;
    private final Path basicLicenseTempalteDir;

    //    @Autowired
    public UtilityService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.basicLicenseTempalteDir = Paths.get(fileStorageProperties.getUploadDir(), fileStorageProperties.getLicenseTemplateDir(), fileStorageProperties.getBasicLicenseTemplateName());
    }


    public String handleOptionalFormData(String data) {
        System.out.println(data);
        if (!data.equals("null")) {
            System.out.println("data");
            return data;
        } else {
            System.out.println("empty");
            return null;
        }
    }


    public void populateDataBase() {
        // TODO: 19.08.2022 Alle vorhandenen User aus der Datenbasis ziehen
        //keycloakService.getAllUsers()
        List<UserRepresentation> userList = new ArrayList<>();;
        List<User> users = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();
        List<ArtistAlias> artistAliases = new ArrayList<>();
        List<String> artistNames = Arrays.asList("AC/DC", "Pink Floyd", "The Rolling Stones");
        List<String> sampleTitles = Arrays.asList("Epic Guitar Riffs", "Sythy Sounds", "Mick Jagger Voiceover");
        List<String> genres = Arrays.asList("Rock", "Electronic", "Pop");

        List<Integer> tempoList = Arrays.asList(122, 240, 80);

//        List<List<Integer>> tempoListList = new ArrayList<>();
//        List<Integer> tempoList1 = Arrays.asList(122, 240, 80);
//        List<Integer> tempoList2 = Arrays.asList(60, 60, 90);
//        List<Integer> tempoList3 = Arrays.asList(130, 140, 100);
//        tempoListList.add(tempoList1);
//        tempoListList.add(tempoList2);
//        tempoListList.add(tempoList3);
//
        List<Set<String>> moodListList = new ArrayList<>();
//        List<String>  moodList1 = Arrays.asList("bouncy", "futuristic", "epic");
//        Arrays.asList("calming", "angry", "rowdy");
//                Arrays.asList("romantic", "rebellious", "complex");
        Set<String> moodList1 = Stream.of("bouncy", "futuristic", "epic").collect(Collectors.toSet());
        Set<String> moodList2 = Stream.of("calming", "angry", "rowdy").collect(Collectors.toSet());
        Set<String> moodList3 = Stream.of("romantic", "rebellious", "complex").collect(Collectors.toSet());

        moodListList.add(moodList1);
        moodListList.add(moodList2);
        moodListList.add(moodList3);

        List<Set<String>> tagListList = new ArrayList<>();
        //Arrays.asList("quit", "peacefull", "loveit");
//        Arrays.asList("synthislove", "fastforward", "nightdrives");
//        Arrays.asList("mickjagger", "smokyvoice", "voiceover");

        Set<String> tagList1 = Stream.of("quit", "peacefull", "loveit").collect(Collectors.toSet());
        Set<String> tagList2 = Stream.of("synthislove", "fastforward", "nightdrives").collect(Collectors.toSet());
        Set<String> tagList3 = Stream.of("mickjagger", "smokyvoice", "voiceover").collect(Collectors.toSet());

        tagListList.add(tagList1);
        tagListList.add(tagList2);
        tagListList.add(tagList3);


        AtomicInteger i = new AtomicInteger();
        userList.stream().forEach(userRepresentation -> {
            User user;
            Optional<User> optionalUser = userRepository.findById(userRepresentation.getId());
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                user = userRepository.save(new User(
                        userRepresentation.getId(),
                        userRepresentation.getEmail()
                ));
            }
//            userRepresentation.getClientRoles();

            // TODO: 19.08.2022 ersetzen
            /* keycloakService.getRealmRoles(userRepresentation.getId()).stream().forEach(s -> {
                logger.info(s);
            }); */


//            List<String> ur = userRepresentation.getRealmRoles();
//            String role = keycloakService.getArtistRole();

//            logger.info(ur)
            // TODO: 19.08.2022 ersetzen
            //if (keycloakService.getRealmRoles(userRepresentation.getId()).contains(keycloakService.getArtistRole())) {
                Artist artist;
                Optional<Artist> optionalArtist = artistRepository.findByUser(user);
                if (optionalArtist.isPresent()) {
                    artist = optionalArtist.get();
                } else {
                    artist = artistRepository.save(new Artist(user));
                }
                ArtistAlias artistAlias;
                Optional<List<ArtistAlias>> optionalArtistAlias = artistAliasRepository.findByArtist(artist);
                if(optionalArtistAlias.isPresent()) {
                    artistAlias = optionalArtistAlias.get().get(0);
                } else {
                    artistAlias = artistAliasRepository.save(new ArtistAlias(
                            artistNames.get(i.get()),
                            artist,
                            "1660640364855_pexels-christina-morillo-1181424.jpg"
                    ));
                }
                artist.setCurrentArtistAliasID(artistAlias.getArtistALiasID());
                artistRepository.save(artist);
                AudioUnit audioUnit;
                Sample sample;
//                Optional<AudioUnit> optionalAudioUnit = audioUnitRepository.findByArtistAlias(artistAlias);
                audioUnit = audioUnitRepository.save(
                        new AudioUnit(
//                                    artist,
                                artistAlias,
                                sampleTitles.get(i.get()),
                                "1660640242701_s8.mp3",
                                "1660640364855_pexels-christina-morillo-1181424.jpg"
                        )
                );
                sampleRepository.save(new Sample(
                        audioUnit,
                        genres.get(i.get()),
                        tempoList.get(i.get()),
                        moodListList.get(i.get()),
                        tagListList.get(i.get())
                ));
//                if (optionalAudioUnit.isPresent()) {
//                    audioUnit = optionalAudioUnit.get();
//                } else {
//
//                }

//                Sample sample = sampleRepository.save(new Sample(
//                        artist,
//                        sampleTitles.get(i.get()),
//                        genres.get(i.get()),
//                        tempoList.get(i.get()),
//                        moodListList.get(i.get()),
//                        tagListList.get(i.get()),
//                        artistAlias
//                ));

//                Sample sample = sampleRepository.save(new Sample(
//                        artist,
//                        sampleTitles.get(i.get()),
//                        genres.get(i.get()),
//                        tempoList.get(i.get()),
//                        moodListList.get(i.get()),
//                        tagListList.get(i.get()),
//                        AudioUnitType.Sample,
//                        artistAlias
//                ));

            //}
            i.getAndIncrement();
        });
    }


}

