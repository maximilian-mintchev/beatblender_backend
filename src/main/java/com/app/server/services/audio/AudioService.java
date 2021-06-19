package com.app.server.services.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;
import com.app.server.model.searchfilterform.SearchFilter;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AudioService {

    @Autowired
    AudioUnitRepository audioUnitRepository;

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    TrackRepository trackRepository;



    public AudioService() {
    }


    public Page<AudioUnit> getAllAudioUnits(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudioUnit> page = audioUnitRepository.findAll(pageable);
        return page;
    }

    public Page<Track> getAllTracks(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Track> trackPage = trackRepository.findAll(pageable);
        return trackPage;
    }

    public Page<AudioUnit> findAudioUnitLike(String searchString, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudioUnit> audioUnitPage = audioUnitRepository.findAudioUnitLike(searchString, pageable);
        return audioUnitPage;
    }

    public List<Track> findTracksByAudioUnit(List<AudioUnit> audioUnitList) {
        List<Track> trackList = new ArrayList<>();
        audioUnitList.stream().forEach((audioUnit -> {
            Optional<Track> optionalTrack = trackRepository.findByAudioUnit(audioUnit);
            if(optionalTrack.isPresent()) {
                trackList.add(optionalTrack.get());
            }
        }));
        return trackList;
    }

    public Map<String, Object> findTracksLike(String searchString, Integer pageNo, Integer pageSize, String sortBy) {
        Page<AudioUnit> audioUnitPage = findAudioUnitLike(searchString, pageNo, pageSize, sortBy);
        Map<String, Object> trackPage = new HashMap<>();
        if(audioUnitPage.hasContent()) {
            List<Track> tracks = findTracksByAudioUnit(audioUnitPage.getContent());
            trackPage.put("tracks", tracks);
            trackPage.put("currentPage", audioUnitPage.getNumber());
            trackPage.put("totalItems", audioUnitPage.getTotalElements());
            trackPage.put("totalPages", audioUnitPage.getTotalPages());
            return trackPage;
        } else {
            return null;
        }
    }



    public Page<AudioUnit> findSamplesByString(Integer pageNo, Integer pageSize, String sortBy, String searchString) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudioUnit> page = audioUnitRepository.findAudioUnitLike(searchString, pageable);
        return page;
    }

    public Page<AudioUnit> filterSamples(SearchFilter searchFilter) {
        Page<AudioUnit> audioUnitPage;
        if(searchFilter.getSearchString().equals("")) {
            audioUnitPage = audioUnitRepository.filterAudioUnits(searchFilter.getGenres() ,searchFilter.getMoods(),searchFilter.getMinTempo(), searchFilter.getMaxTempo(), searchFilter.getPageable());
        } else {
            audioUnitPage = audioUnitRepository
                    .filterTest1(searchFilter.getSearchString(), searchFilter.getGenres() ,searchFilter.getMoods(),searchFilter.getMinTempo(), searchFilter.getMaxTempo(), searchFilter.getPageable());
        }
//                .filterAudioUnit(searchFilter.getSearchString(), searchFilter.getGenres(), searchFilter.getMoods(),searchFilter.getMinTempo(),searchFilter.getMaxTempo(), searchFilter.getMinLep(), searchFilter.getMaxLep(), searchFilter.getPageable());
        return audioUnitPage;
    }





}
