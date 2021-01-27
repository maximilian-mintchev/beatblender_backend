package com.app.server.controller.media;


import com.app.server.exceptions.MyFileNotFoundException;
import com.app.server.services.fileStorage.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/web/public/media")
public class PublicMediaController {

    Logger logger = LoggerFactory.getLogger(PublicMediaController.class);




}
