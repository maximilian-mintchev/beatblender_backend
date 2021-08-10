package com.app.server.messages.response;

import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;

import java.util.List;
import java.util.Set;

public class MyUploadsResponse {

    Set<Set<Object>> sets;
    List<Sample> sampleList;
    List<Track> trackList;

    public MyUploadsResponse() {

    }

    public MyUploadsResponse(Set<Set<Object>> sets, List<Sample> sampleList, List<Track> trackList) {
        this.sets = sets;
        this.sampleList = sampleList;
        this.trackList = trackList;
    }

    public Set<Set<Object>> getSets() {
        return sets;
    }

    public void setSets(Set<Set<Object>> sets) {
        this.sets = sets;
    }

    public List<Sample> getSampleList() {
        return sampleList;
    }

    public void setSampleList(List<Sample> sampleList) {
        this.sampleList = sampleList;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }
}
