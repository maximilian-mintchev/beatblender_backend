package com.app.server.model;

import javax.persistence.*;

@Entity
@Table(name="audiofile")
public class AudioFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String artistName;

    @Column(nullable = false)
    private String audioTitle;

    public AudioFile() {

    }

    public AudioFile(String artistName, String audioTitle) {
        this.artistName = artistName;
        this.audioTitle = audioTitle;
    }

    public AudioFile(String artistName, String sampleTitle, String audioFilePath, String sampleImagePath) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }




}

