package com.app.server.messages.response;

import com.app.server.enums.LicenseType;
import com.app.server.model.audio.Sample;

import java.util.List;
import java.util.Set;

public class SampleResponse {

    private String sampleID;
    private String title;
    private String genre;
    private int tempo;
    private Set<String> moods;
    private Set<String> tags;
    private LicenseType licenseType;
    private String artistName;


    public SampleResponse(Sample sample) {
        this.sampleID = sample.getSampleID();
        this.title = sample.getAudioUnit().getTitle();
        this.genre = sample.getGenre();
        this.tempo = sample.getTempo();
        this.moods = sample.getMoods();
        this.tags = sample.getTags();
        this.artistName = sample.getAudioUnit().getArtistAlias().getArtistName();
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public Set<String> getMoods() {
        return moods;
    }

    public void setMoods(Set<String> moods) {
        this.moods = moods;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getTags() {
        return tags;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
