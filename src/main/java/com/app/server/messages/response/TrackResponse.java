package com.app.server.messages.response;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Track;
import com.app.server.model.license.BasicLicense;

import java.util.List;

public class TrackResponse {

    Track track;
    List<BasicLicense> basicLicenses;

    public TrackResponse() {

    }

    public TrackResponse(Track track, List<BasicLicense> basicLicenses) {
        this.track = track;
        this.basicLicenses = basicLicenses;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public List<BasicLicense> getBasicLicenses() {
        return basicLicenses;
    }

    public void setBasicLicenses(List<BasicLicense> basicLicenses) {
        this.basicLicenses = basicLicenses;
    }
}
