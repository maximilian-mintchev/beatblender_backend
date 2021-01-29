package com.app.server.controller.media;


import com.app.server.model.audio.Sample;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.services.fileStorage.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    //  TODO  Download File API anpassen
//    @GetMapping("/image/{userName}/{fileName}")
//            @PathVariable String userName,
//            @PathVariable String fileName
//            @PathVariable("fileName") String fileName
    @GetMapping("/image/{sampleID}")
    @ResponseBody
    public ResponseEntity<Resource> loadImage(
            @PathVariable("sampleID") String sampleID
    ) {
        Resource file = null;
        System.out.println("download request");
        Optional<Sample> optionalSample = sampleRepository.findById(sampleID);
        if (optionalSample.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Sample sample = optionalSample.get();
        try {
            file = fileStorageService.loadFileAsResource(sample.getAudioUnit().getImageFileName(), sample.getAudioUnit().getArtistAlias().getArtist().getUser().getUuid());
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
    @GetMapping("/audio/{sampleID}")
    @ResponseBody
    public ResponseEntity<Resource> loadAudio(
            @PathVariable("sampleID") String sampleID
    ) {
        Resource file = null;
        System.out.println("download request");
        Optional<Sample> optionalSample = sampleRepository.findById(sampleID);
        if (optionalSample.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Sample sample = optionalSample.get();
        try {
            file = fileStorageService.loadFileAsResource(sample.getAudioUnit().getAudioFileName(), sample.getAudioUnit().getArtistAlias().getArtist().getUser().getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SamplePoolRestAPI: Resource not Found");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
