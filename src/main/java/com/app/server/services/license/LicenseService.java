package com.app.server.services.license;

import com.app.server.messages.response.TrackResponse;
import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.MixedIn;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;
import com.app.server.model.license.BasicLicense;
import com.app.server.model.license.FullLicense;
import com.app.server.model.user.Artist;
import com.app.server.repository.audio.AudioUnitRepository;
import com.app.server.repository.audio.MixedInRepository;
import com.app.server.repository.audio.SampleRepository;
import com.app.server.repository.audio.TrackRepository;
import com.app.server.repository.license.BasicLicenseRepository;
import com.app.server.repository.license.FullLicenseRepository;
import com.app.server.services.audio.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenseService {

    @Autowired
    private MixedInRepository mixedInRepository;

    @Autowired
    private BasicLicenseRepository basicLicenseRepository;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private AudioService audioService;

    @Autowired
    private AudioUnitRepository audioUnitRepository;

    @Autowired
    private FullLicenseRepository fullLicenseRepository;

    public List<MixedIn> findMixedInsByTrack(Track track) {
        Optional<List<MixedIn>> optMixedInList = mixedInRepository.findAllByParent(track.getAudioUnit());
        List<MixedIn> mixedInList = new ArrayList<>();
        if(optMixedInList.isPresent()) {
            mixedInList = optMixedInList.get();
//                        List<S>
//                        mixedIns.add(mixedIn);
        }
        return mixedInList;
    }

//    public List<>

    public List<BasicLicense> findBasicLicensesByTrack(Track track) {
        Optional<List<MixedIn>> optMixedInList = mixedInRepository.findAllByParent(track.getAudioUnit());
        List<MixedIn> mixedInList;
        if(optMixedInList.isPresent()) {
            mixedInList = optMixedInList.get();
            List<BasicLicense> basicLicensList = new ArrayList<>();
            return mixedInList.stream().map(mixedIn -> {
                Optional<Sample> optionalSample = sampleRepository.findByAudioUnit(mixedIn.getChild());
                if(optionalSample.isPresent()) {
                    Optional<BasicLicense> optionalBasicLicense = basicLicenseRepository.findByDownloaderAndSample(track.getAudioUnit().getCreator().getUser(),optionalSample.get());
                    if(optionalBasicLicense.isPresent()) {
                        return optionalBasicLicense.get();
                    } else {
                        throw new NullPointerException("No Basic License found.");
                    }
                }
                throw new NullPointerException();
            }).collect(Collectors.toList());
//                        List<S>
//                        mixedIns.add(mixedIn);
        } else {
            throw new NullPointerException("MixedInNotFound");
        }
    }

    public List<FullLicense> findFullLicensesByArtist(Artist artist) {
        List<AudioUnit> audioUnitList = audioService.findAudioUnitsByArtist(artist);
        List<Track> trackList = audioService.findTracksByAudioUnit(audioUnitList);
        List<FullLicense> fullLicenseList = findFullLicensesByTracks(trackList);
        return fullLicenseList;
    }








    public List<TrackResponse> createTrackResponse(List<Track> trackList) {
        List<TrackResponse> trackResponseList = new ArrayList<>();
        trackList.stream().forEach(track -> {
            List<BasicLicense> basicLicenseList = findBasicLicensesByTrack(track);
            trackResponseList.add(new TrackResponse(track, basicLicenseList));
        });
        return trackResponseList;
    }

    public List<Track> findUnextendedTracks(List<Track> trackList) {
        return trackList.stream().filter((track -> fullLicenseRepository.findByTrack(track).isEmpty())).collect(Collectors.toList());
    }

    public List<Track> findExtendedTracks(List<Track> trackList) {
        return trackList.stream().filter((track -> fullLicenseRepository.findByTrack(track).isPresent())).collect(Collectors.toList());
    }



    public List<FullLicense> findFullLicensesByTracks(List<Track> trackList) {
        List<FullLicense> fullLicenseList = new ArrayList<>();
        trackList.stream().forEach((track -> {
            Optional<FullLicense> optionalFullLicense = fullLicenseRepository.findByTrack(track);
            if(optionalFullLicense.isPresent()) {
                fullLicenseList.add(optionalFullLicense.get());
            }
        }));
        return fullLicenseList;
    }

    public FullLicense findFullLicenseByID(String fullLicenseID) {
        Optional<FullLicense> optionalFullLicense = fullLicenseRepository.findById(fullLicenseID);
        if(optionalFullLicense.isPresent()) {
            return optionalFullLicense.get();
        } else {
            throw new NullPointerException("Could not find Full License.");
        }
    }
}
