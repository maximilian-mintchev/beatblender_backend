package com.app.server.controller.audio;

import com.app.server.messages.response.SampleResponse;
import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/web/public/audio")
public class PublicAudioController {

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    AudioUnitRepository audioUnitRepository;

    Logger logger = LoggerFactory.getLogger(PublicAudioController.class);

    @GetMapping("/samples-home")
    public ResponseEntity<List<SampleResponse>> samplesHome(
    ) {

        List<Sample> sampleList = new ArrayList<>();
        List<SampleResponse> sampleResponseList = new ArrayList<>();
        sampleRepository.findAll().forEach(sampleList::add);
        sampleList.stream().forEach((sample -> {
            sampleResponseList.add(new SampleResponse(sample));
        }));
//        audioList.stream().forEach((audioUnit -> {
//            if (audioUnit instanceof Track) {
//                logger.info("AudioUnit is a Track");
//            } else if (audioUnit instanceof Sample) {
//                logger.info("AudioUnit is a Sample");
//            } else {
//                throw new IllegalStateException("AudioUnit has to be a Track or a Sample");
////                return ResponseEntity.status(301);
//            }
//        }));
        return ResponseEntity.ok(sampleResponseList);
    }

    @PostMapping("/search-multiple-audio")
    public ResponseEntity<List<Sample>> searchMultipleAudio(
            @RequestParam("sampleIDs") List<String> sampleIDList) {
        List<Sample> queriedSamples = new ArrayList<>();
        sampleIDList.stream().forEach((id) -> {
            Optional<Sample> optionalSample = sampleRepository.findById(id);
            if (optionalSample.isPresent()) {
                queriedSamples.add(optionalSample.get());
            }
        });
        if (queriedSamples.size() > 0) {
            return ResponseEntity.ok(queriedSamples);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/search-single-audio")
    public ResponseEntity<Sample> searchMusic(@RequestParam("sampleId") String sampleId) {
        Optional<Sample> filteredSample = this.sampleRepository.findById(sampleId);
        Sample querySample;
        if (filteredSample.isPresent()) {
            querySample = filteredSample.get();
            return ResponseEntity.ok(querySample);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search-music-by-input")
    public ResponseEntity<List<AudioUnit>> searchMusicByInput(@RequestParam("searchString") String searchString) {
        Optional<List<AudioUnit>> optionalAudioUnits = this.audioUnitRepository.findAudioUnitLike(searchString);
        List<AudioUnit> queriedSamples;
        if (optionalAudioUnits.isPresent()) {
            queriedSamples =  optionalAudioUnits.get();
            return ResponseEntity.ok(queriedSamples);
        } else {
            return ResponseEntity.notFound().build();
        }

//
//        if(filteredSamples.isPresent()) {
//            logger.info("Found Similar Samples");
//            querySamples = filteredSamples.get();
//            SampleSearchQuery[] sampleSearchQueries = new SampleSearchQuery[querySamples.size()];
//            int i = 0;
//            for (Sample sample : querySamples) {
//                sampleSearchQueries[i] = new SampleSearchQuery(
//                        sample.getId(),
//                        sample.getArtistName(),
//                        sample.getSampleTitle(),
//                        sample.getImageDownLoadUri()
//                );
//                i++;
//            }
//            return ResponseEntity.ok(new SampleSearchQueryResponse(sampleSearchQueries));
//        } else {
//            logger.info("No Similar Samples Found");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }


    }

    @PostMapping("/filter-samples")
    private ResponseEntity<Set<SampleResponse>> filterSamples(
            @RequestParam("moods") Set<String> moods,
            @RequestParam("tempo") int tempo,
            @RequestParam("genre") String genre
    ) {
        if(moods == null || genre == null) {
            throw new NullPointerException("Filter Params are null");
        }
        if(genre.equals("") && moods.equals("")) {
            throw new IllegalArgumentException("Params cannot be empty");
        }
        Set<Sample> samples;
        Optional<Set<Sample>> optionalSamples = sampleRepository.filterAudioUnit(moods,genre,tempo);
        if(optionalSamples.isPresent()) {
            Set<SampleResponse> sampleResponses = new HashSet<>();
            samples = new HashSet<>(optionalSamples.get());
            samples.stream().forEach((sample -> {
                sampleResponses.add(new SampleResponse(sample));
            }));
            return ResponseEntity.ok(sampleResponses);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
