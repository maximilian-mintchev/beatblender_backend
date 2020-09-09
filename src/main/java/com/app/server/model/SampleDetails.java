package com.app.server.model;

import javax.persistence.*;

@Entity
@Table(name="sample_details")
public class SampleDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private String genre;

    @Column(nullable = true)
    private String trackType;

    @Column(nullable = true)
    private String songKey;

    @Column(nullable = true)
    private String region;

    @Column(nullable = true)
    private String audioDescription;

    public SampleDetails() {
    }

    public SampleDetails(String genre, String trackType, String songKey, String region, String audioDescription) {
        this.genre = genre;
        this.trackType = trackType;
        this.songKey = songKey;
        this.region = region;
        this.audioDescription = audioDescription;
    }

    public Long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getSongKey() {
        return songKey;
    }

    public void setSongKey(String songKey) {
        this.songKey = songKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(String audioDescription) {
        this.audioDescription = audioDescription;
    }
}

