package com.app.server.controller.media;

import com.app.server.exceptions.MyFileNotFoundException;
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
@RequestMapping("/api/web/protected/media")
public class ProtectedMediaController {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SampleRepository sampleRepository;



    //    @GetMapping("/downloadFile/{sampleID}")
//    @ResponseBody
//    public ResponseEntity<Resource> getFile(@PathVariable String sampleID) {
//        Resource file = null;
//        System.out.println("download request");
//        try {
//            file = fileStorageService.loadFileAsResource(fileName, userName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("SamplePoolRestAPI: Resource not Found");
//        }
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
//    }

    //    TODO: download basic license api anpassen
    @GetMapping("downloadFile/downloadBasicLicense/{downloaderID}/{sampleID}")
    @ResponseBody
    public ResponseEntity<Resource> getBasicLicense(@PathVariable("downloaderID") String downloaderID, @PathVariable("sampleID") String sampleID) {
        Resource file;
        try {
            file = fileStorageService.loadBasicLicenseAsResource(downloaderID, sampleID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyFileNotFoundException("Wasn't able to retrieve basic license", e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


}
