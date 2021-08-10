package com.app.server.controller.audio;

import com.app.server.enums.TrackFilterCategory;
import com.app.server.messages.response.SampleResponse;
import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;
import com.app.server.model.searchfilterform.SearchFilter;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.services.audio.AudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    AudioService audioService;

    Logger logger = LoggerFactory.getLogger(PublicAudioController.class);

    @GetMapping("/samples-home")
    public ResponseEntity<Map<String, Object>> samplesHome(
            @RequestParam(value="pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value="sortBy", defaultValue = "title") String sortBy
    ) {

        try {
//            List<AudioUnit> audioUnitList = new ArrayList<>();
//            List<Sample> sampleList = new ArrayList<>();
////            List<SampleResponse> sampleResponseList = new ArrayList<>();
////        sampleRepository.findAll().forEach(sampleList::add);
//            Page<AudioUnit> page = audioService.getAllAudioUnits(pageNo, pageSize, sortBy);
//
//
//            if (page.hasContent()) {
//                audioUnitList = page.getContent();
////            page.getContent().stream().forEach((audioUnit -> {
////                Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
////                if(optionalSample.isPresent()) {
////                    Sample sample = optionalSample.get();
////                    sampleResponseList.add(new SampleResponse(sample));
//////                sampleList.add(optionalSample.get());
////                }
////            }));
//            }
//            for (int i = 0; i < audioUnitList.size(); i++) {
//                AudioUnit audioUnit = audioUnitList.get(i);
//                Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
//                if(optionalSample.isPresent()) {
//                    Sample sample = optionalSample.get();
//                    sampleList.add(sample);
////                sampleList.add(optionalSample.get());
//                }
//            }

            Page<Sample> samplePage = audioService.getAllSamples(pageNo, pageSize, sortBy);
            Map<String, Object> responseMap = new HashMap<>();
            if(samplePage.hasContent()) {
                responseMap.put("samples", samplePage.getContent());
                responseMap.put("currentPage", samplePage.getNumber());
                responseMap.put("totalItems", samplePage.getTotalElements());
                responseMap.put("totalPages", samplePage.getTotalPages());
            } else {
                throw new NullPointerException();
            }
            /*audioUnitList.stream().forEach(audioUnit -> {
                Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
                if(optionalSample.isPresent()) {
                    sampleResponseList.add(new SampleResponse(optionalSample.get()));
//                sampleList.add(optionalSample.get());
                }
            });*/

            /*sampleList.stream().forEach((sample -> {
                sampleResponseList.add(new SampleResponse(sample));
            }));*/

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
            logger.info(responseMap.toString());
            return new ResponseEntity<>(responseMap, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tracks-home")
    public ResponseEntity<Map<String, Object>> tracksHome(
            @RequestParam(value="page", defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", defaultValue = "10") Integer pageSize
//            @RequestParam(value="sortBy", defaultValue = "title") String sortBy
    ) {

        try {
            List<Track> trackList = new ArrayList<>();
//            List<Sample> sampleList = new ArrayList<>();
            Page<Track> page = audioService.getAllTracks(pageNo, pageSize);
            if (page.hasContent()) {
                trackList = page.getContent();
            }


//            for (int i = 0; i < audioUnitList.size(); i++) {
//                AudioUnit audioUnit = audioUnitList.get(i);
//                Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
//                if(optionalSample.isPresent()) {
//                    Sample sample = optionalSample.get();
//                    sampleList.add(sample);
////                sampleList.add(optionalSample.get());
//                }
//            }
            /*audioUnitList.stream().forEach(audioUnit -> {
                Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
                if(optionalSample.isPresent()) {
                    sampleResponseList.add(new SampleResponse(optionalSample.get()));
//                sampleList.add(optionalSample.get());
                }
            });*/

            /*sampleList.stream().forEach((sample -> {
                sampleResponseList.add(new SampleResponse(sample));
            }));*/
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("tracks", trackList);
            responseMap.put("currentPage", page.getNumber());
            responseMap.put("totalItems", page.getTotalElements());
            responseMap.put("totalPages", page.getTotalPages());
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
            logger.info(responseMap.toString());
            return new ResponseEntity<>(responseMap, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/find-by-string")
    public ResponseEntity<Map<String, Object>> searchMultipleAudio(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("search") String searchString
    ) {
//        List<Sample> queriedSamples = new ArrayList<>();
        if(searchString.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(pageSize == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<AudioUnit> page = audioService.findSamplesByString(pageNo, pageSize, sortBy,searchString);


//        Optional<List<AudioUnit>> optionalAudioUnits = this.audioUnitRepository.findAudioUnitLike(searchString);
//        if(optionalAudioUnits.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        audioUnitList = optionalAudioUnits.get();
        if (!page.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AudioUnit> audioUnitList;
        audioUnitList = page.getContent();
        List<Sample> sampleList = new ArrayList<>();
//        for(int i = 0; i < audioUnitList.size(); i++) {
//            Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnitList.get(i));
//            if(optionalSample.isPresent()) {
//                Sample sample = optionalSample.get();
//                sampleResponseList.add(new SampleResponse(sample));
//            } else {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
        for (int i = 0; i < audioUnitList.size(); i++) {
            AudioUnit audioUnit = audioUnitList.get(i);
            Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
            if(optionalSample.isPresent()) {
                Sample sample = optionalSample.get();
                sampleList.add(sample);
            } else {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("samples", sampleList);
        responseMap.put("currentPage", page.getNumber());
        responseMap.put("totalItems", page.getTotalElements());
        responseMap.put("totalPages", page.getTotalPages());

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

//    @PostMapping("/search-multiple-audio")
//    public ResponseEntity<List<Sample>> searchMultipleAudio(
//            @RequestParam("sampleIDs") List<String> sampleIDList) {
//        List<Sample> queriedSamples = new ArrayList<>();
//        sampleIDList.stream().forEach((id) -> {
//            Optional<Sample> optionalSample = sampleRepository.findById(id);
//            if (optionalSample.isPresent()) {
//                queriedSamples.add(optionalSample.get());
//            }
//        });
//        if (queriedSamples.size() > 0) {
//            return ResponseEntity.ok(queriedSamples);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PostMapping("/search-single-audio")
//    public ResponseEntity<Sample> searchMusic(@RequestParam("sampleId") String sampleId) {
//        Optional<Sample> filteredSample = this.sampleRepository.findById(sampleId);
//        Sample querySample;
//        if (filteredSample.isPresent()) {
//            querySample = filteredSample.get();
//            return ResponseEntity.ok(querySample);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @PostMapping("/search-music-by-input")
//    public ResponseEntity<List<AudioUnit>> searchMusicByInput(@RequestParam("searchString") String searchString) {
//        Optional<List<AudioUnit>> optionalAudioUnits = this.audioUnitRepository.findAudioUnitLike(searchString);
//        List<AudioUnit> queriedSamples;
//        if (optionalAudioUnits.isPresent()) {
//            queriedSamples = optionalAudioUnits.get();
//            return ResponseEntity.ok(queriedSamples);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//
////
////        if(filteredSamples.isPresent()) {
////            logger.info("Found Similar Samples");
////            querySamples = filteredSamples.get();
////            SampleSearchQuery[] sampleSearchQueries = new SampleSearchQuery[querySamples.size()];
////            int i = 0;
////            for (Sample sample : querySamples) {
////                sampleSearchQueries[i] = new SampleSearchQuery(
////                        sample.getId(),
////                        sample.getArtistName(),
////                        sample.getSampleTitle(),
////                        sample.getImageDownLoadUri()
////                );
////                i++;
////            }
////            return ResponseEntity.ok(new SampleSearchQueryResponse(sampleSearchQueries));
////        } else {
////            logger.info("No Similar Samples Found");
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
//
//
//    }

    @PostMapping("/filter-samples")
    private ResponseEntity<Map<String, Object>> filterSamples(
            @RequestParam("searchString") String searchString,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("genres") List<String> genres,
            @RequestParam("moods") List<String> moods,
            @RequestParam("categories") List<String> categories,
            @RequestParam("minTempo") Integer minTempo,
            @RequestParam("maxTempo") Integer maxTempo,
            @RequestParam("minLep") Integer minLep,
            @RequestParam("maxLep") Integer maxLep
    ) {
        if (moods == null || genres == null || categories == null) {
            throw new NullPointerException("Filter Params are null");
        }
        if (genres.equals("") || moods.equals("") || categories.equals("")) {
            throw new IllegalArgumentException("Params cannot be empty");
        }
        if(searchString == null || searchString.equals("")) {
            searchString = "";
        }
        List<Sample> samples;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudioUnit> audioUnitPage = audioService.filterSamples(new SearchFilter(
                pageable,
                searchString,
                genres,
                moods,
                categories,
                minTempo,
                maxTempo,
                minLep,
                maxLep
        ));
//        Optional<Set<Sample>> optionalSamples = sampleRepository.filterAudioUnit(searchString, genres, moods, minTempo, maxTempo, minLep, maxLep);
        if(!audioUnitPage.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AudioUnit> audioUnitList = new ArrayList<>();
        audioUnitList = audioUnitPage.getContent();
        List<Sample> sampleList = new ArrayList<>();
        for (int i = 0; i < audioUnitList.size(); i++) {
            AudioUnit audioUnit = audioUnitList.get(i);
            Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
            if(optionalSample.isPresent()) {
                Sample sample = optionalSample.get();
                sampleList.add(sample);
//                sampleList.add(optionalSample.get());
            }
        }


//        List<SampleResponse> sampleResponses = new ArrayList<>();
//        samples = samplePage.getContent();
//        samples.stream().forEach((sample -> {
//            sampleResponses.add(new SampleResponse(sample));
//        }));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("samples", sampleList);
        responseMap.put("currentPage", audioUnitPage.getNumber());
        responseMap.put("totalItems",audioUnitPage.getTotalElements());
        responseMap.put("totalPages", audioUnitPage.getTotalPages());

        return new ResponseEntity<>(responseMap, HttpStatus.OK);

//        return ResponseEntity.ok(sampleResponses);


//        if (optionalSamples.isPresent()) {
//            Set<SampleResponse> sampleResponses = new HashSet<>();
//            samples = new HashSet<>(optionalSamples.get());
//            samples.stream().forEach((sample -> {
//                sampleResponses.add(new SampleResponse(sample));
//            }));
//            return ResponseEntity.ok(sampleResponses);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    @GetMapping("/get-sample/{audioUnitID}")
    @ResponseBody
    private ResponseEntity<Sample> getAudioUnit(
//            @RequestParam("search") String searchString,
            @PathVariable("audioUnitID") String audioUnitID
//            @RequestParam("pageNo") Integer pageNo,
//            @RequestParam("pageSize") Integer pageSize
//            @RequestParam("genres") List<String> genres,
//            @RequestParam("moods") List<String> moods,
//            @RequestParam("categories") List<String> categories,
//            @RequestParam("minTempo") Integer minTempo,
//            @RequestParam("maxTempo") Integer maxTempo,
//            @RequestParam("minLep") Integer minLep,
//            @RequestParam("maxLep") Integer maxLep
    ) {
    Optional<Sample> optionalSample = this.audioService.findOneSampleByAudioUnitID(audioUnitID);
    Sample sample;
    if(optionalSample.isPresent()) {
        sample = optionalSample.get();
    } else {
        throw new NullPointerException("Sample not Found");
    }
    return ResponseEntity.ok(sample);
    }

    @GetMapping("/filter-tracks")
    @ResponseBody
    private ResponseEntity<Map<String, Object>> filterTracks(
            @RequestParam("search") String searchString,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize
//            @RequestParam("genres") List<String> genres,
//            @RequestParam("moods") List<String> moods,
//            @RequestParam("categories") List<String> categories,
//            @RequestParam("minTempo") Integer minTempo,
//            @RequestParam("maxTempo") Integer maxTempo,
//            @RequestParam("minLep") Integer minLep,
//            @RequestParam("maxLep") Integer maxLep
    ) {
        /*if (moods == null || genres == null || categories == null) {
            throw new NullPointerException("Filter Params are null");
        }
        if (genres.equals("") || moods.equals("") || categories.equals("")) {
            throw new IllegalArgumentException("Params cannot be empty");
        }*/
//        String searchString;
//        if(optSearchString.isPresent()) {
//            searchString = optSearchString.get();
//        } else {
//            throw new NullPointerException("searchString is null");
//        }

        Map<String, Object> trackResponse = audioService.findTracksLike(searchString, pageNo, pageSize, sortBy);
        return ResponseEntity.ok(trackResponse);
//        List<Sample> samples;
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<AudioUnit> audioUnitPage = audioService.filterSamples(new SearchFilter(
//                pageable,
//                searchString,
//                genres,
//                moods,
//                categories,
//                minTempo,
//                maxTempo,
//                minLep,
//                maxLep
//        ));
////        Optional<Set<Sample>> optionalSamples = sampleRepository.filterAudioUnit(searchString, genres, moods, minTempo, maxTempo, minLep, maxLep);
//        if(!audioUnitPage.hasContent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        List<AudioUnit> audioUnitList = new ArrayList<>();
//        audioUnitList = audioUnitPage.getContent();
//        List<Sample> sampleList = new ArrayList<>();
//        for (int i = 0; i < audioUnitList.size(); i++) {
//            AudioUnit audioUnit = audioUnitList.get(i);
//            Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
//            if(optionalSample.isPresent()) {
//                Sample sample = optionalSample.get();
//                sampleList.add(sample);
////                sampleList.add(optionalSample.get());
//            }
//        }
//
//
////        List<SampleResponse> sampleResponses = new ArrayList<>();
////        samples = samplePage.getContent();
////        samples.stream().forEach((sample -> {
////            sampleResponses.add(new SampleResponse(sample));
////        }));
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("samples", sampleList);
//        responseMap.put("currentPage", audioUnitPage.getNumber());
//        responseMap.put("totalItems",audioUnitPage.getTotalElements());
//        responseMap.put("totalPages", audioUnitPage.getTotalPages());
//
//        return new ResponseEntity<>(responseMap, HttpStatus.OK);

//        return ResponseEntity.ok(sampleResponses);


//        if (optionalSamples.isPresent()) {
//            Set<SampleResponse> sampleResponses = new HashSet<>();
//            samples = new HashSet<>(optionalSamples.get());
//            samples.stream().forEach((sample -> {
//                sampleResponses.add(new SampleResponse(sample));
//            }));
//            return ResponseEntity.ok(sampleResponses);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

}
