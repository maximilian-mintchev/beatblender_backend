package com.app.server.messages.response;

import com.app.server.model.audio.Sample;
import com.app.server.model.audio.Track;

import java.util.List;

public class Release {

    private  Track track;
    private List<Sample> samples;

    public Release(Track track, List<Sample> samples) {
        this.track = track;
        this.samples = samples;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
}
