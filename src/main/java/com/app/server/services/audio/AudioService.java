package com.app.server.services.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;
import com.app.server.model.searchfilterform.SearchFilter;
import com.app.server.model.user.Artist;
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

    public Optional<AudioUnit> findOneAudioUnit(String audioUnitID) {
        Optional<AudioUnit> optionalAudioUnit = audioUnitRepository.findById(audioUnitID);
        return optionalAudioUnit;
    }

    public Optional<Sample> findOneSamplebyAudioUnit(AudioUnit audioUnit) {
        Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
        return optionalSample;
    }

    public Optional<Sample> findOneSampleByAudioUnitID(String audioUnitID) {
        Optional<AudioUnit> optionalAudioUnit = findOneAudioUnit(audioUnitID);
        if(optionalAudioUnit.isPresent()) {
            AudioUnit audioUnit = optionalAudioUnit.get();
            return findOneSamplebyAudioUnit(audioUnit);
        } else {
            throw new NullPointerException("AudioUnit not present");
        }
    }


    public Page<AudioUnit> getAllAudioUnits(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudioUnit> page = audioUnitRepository.findAll(pageable);
        return page;
    }

    public Page<Sample> getAllSamples(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Sample> samplePage = sampleRepository.findAll(pageable);
        return samplePage;
    }

    public Page<Track> getAllTracks(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Track> trackPage = trackRepository.findAll(pageable);
        return trackPage;
    }

    public List<Track> findTracksByArtist(Artist artist) {
        List<AudioUnit> audioUnitLists = findAudioUnitsByArtist(artist);
        return findTracksByAudioUnit(audioUnitLists);
    }

    public List<Sample> findSamplesByArtist(Artist artist) {
        List<AudioUnit> audioUnitLists = findAudioUnitsByArtist(artist);
        return findSamplesByAudioUnit(audioUnitLists);
    }

    public List<AudioUnit> findAudioUnitsByArtist(Artist artist) {
        Optional<List<AudioUnit>> optionalAudioUnits = audioUnitRepository.findByCreator(artist);
        List<AudioUnit> audioUnitList  = new ArrayList<>();
        if(optionalAudioUnits.isPresent()) {
            audioUnitList = optionalAudioUnits.get();
        }
        return audioUnitList;
    }

    public Optional<Track> findTrackByAudioUnit(AudioUnit audioUnit) {
//        List<Track> trackList = new ArrayList<>();
        Track track;
        Optional<Track> optionalTrack = trackRepository.findByAudioUnit(audioUnit);
//        if(optionalTrack.isPresent()) {
//            track = optionalTrack.get();
//        } else {
//            throw new NullPointerException();
//        }
        return optionalTrack;
    }

    public Optional<Sample> findSampleByAudioUnit(AudioUnit audioUnit) {
//        List<Sample> trackList = new ArrayList<>();
        Sample sample;
        Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
//        if(optionalSample.isPresent()) {
//            sample = optionalSample.get();
//        } else {
//            throw new NullPointerException();
//        }
        return optionalSample;
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

    public List<Sample> findSamplesByAudioUnit(List<AudioUnit> audioUnitList) {
        List<Sample> sampleList = new ArrayList<>();
        audioUnitList.stream().forEach((audioUnit -> {
            Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(audioUnit);
            if(optionalSample.isPresent()) {
                sampleList.add(optionalSample.get());
            }
        }));
        return sampleList;
    }

    public Page<AudioUnit> findAudioUnitLike(String searchString, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudioUnit> audioUnitPage = audioUnitRepository.findAudioUnitLike(searchString, pageable);
        return audioUnitPage;
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
            /**/audioUnitPage = audioUnitRepository.filterAudioUnits(searchFilter.getGenres() ,searchFilter.getMoods(),searchFilter.getMinTempo(), searchFilter.getMaxTempo(), searchFilter.getPageable());
            audioUnitPage = audioUnitRepository
                    .filterTest1(searchFilter.getSearchString(), searchFilter.getGenres() ,searchFilter.getMoods(),searchFilter.getMinTempo(), searchFilter.getMaxTempo(), searchFilter.getPageable());
        } else {
            audioUnitPage = audioUnitRepository
                    .filterTest1(searchFilter.getSearchString(), searchFilter.getGenres() ,searchFilter.getMoods(),searchFilter.getMinTempo(), searchFilter.getMaxTempo(), searchFilter.getPageable());
        }
//                .filterAudioUnit(searchFilter.getSearchString(), searchFilter.getGenres(), searchFilter.getMoods(),searchFilter.getMinTempo(),searchFilter.getMaxTempo(), searchFilter.getMinLep(), searchFilter.getMaxLep(), searchFilter.getPageable());
        return audioUnitPage;
    }







}
