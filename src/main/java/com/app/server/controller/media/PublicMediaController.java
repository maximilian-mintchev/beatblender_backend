package com.app.server.controller.media;


import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.user.ArtistAliasRepository;
import com.app.server.services.fileStorage.FileStorageService;
import com.app.server.services.user.UserService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/web/public/media")
public class PublicMediaController {

    Logger logger = LoggerFactory.getLogger(PublicMediaController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private AudioUnitRepository audioUnitRepository;






    //  TODO  Download File API anpassen
//    @GetMapping("/image/{userName}/{fileName}")
//            @PathVariable String userName,
//            @PathVariable String fileName
//            @PathVariable("fileName") String fileName
    @GetMapping("/image/{audioUnitID}")
    @ResponseBody
    public ResponseEntity<Resource> loadImage(
            @PathVariable("audioUnitID") String audioUnitID
    ) {
        Resource file = null;
        System.out.println("download request");
        Optional<AudioUnit> optionalAudioUnit = audioUnitRepository.findById(audioUnitID);
        if (optionalAudioUnit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AudioUnit audioUnit = optionalAudioUnit.get();
//        Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
//        Sample sample = null;
//        if(optionalSample.isPresent()) {
//            sample = optionalSample.get();
//        } else {
//
//        }
        try {
            Path targetPath = Paths.get(audioUnit.getArtistAlias().getArtist().getUser().getUuid());
            file = fileStorageService.loadFileAsResource(targetPath, audioUnit.getImageFileName());
//            audioUnit.getImageFileName(), audioUnit.getArtistAlias().getArtist().getUser().getUuid()
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SamplePoolRestAPI: Resource not Found");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }




    //            @PathVariable String userName,
//            @PathVariable String fileName
    @GetMapping("/audio/{audioUnitID}")
    @ResponseBody
    public ResponseEntity<Resource> loadAudio(
            @PathVariable("audioUnitID") String audioUnitID
    ) {
        Resource file = null;
        System.out.println("download request");
        Optional<AudioUnit> optionalAudioUnit = audioUnitRepository.findById(audioUnitID);
        if (optionalAudioUnit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AudioUnit audioUnit = optionalAudioUnit.get();
        try {
            Path targetPath = Paths.get(audioUnit.getArtistAlias().getArtist().getUser().getUuid());
            file = fileStorageService.loadFileAsResource(targetPath, audioUnit.getAudioFileName());
//            file = fileStorageService.loadFileAsResource(audioUnit.getAudioFileName(), audioUnit.getArtistAlias().getArtist().getUser().getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SamplePoolRestAPI: Resource not Found");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .header(HttpHeaders.CONTENT_TYPE, "audio/mp3")
                .body(file);
    }
}
