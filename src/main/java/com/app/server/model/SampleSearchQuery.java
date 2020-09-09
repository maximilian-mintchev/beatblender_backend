package com.app.server.model;

public class SampleSearchQuery {

    Long id;

    String artistName;

    String sampleTitle;

    String imageDownloadUri;

    public SampleSearchQuery() {
    }

    /*public SampleSearchQuery(String artistName, String sampleTitle, String imageDownloadUri) {
        this.artistName = artistName;
        this.sampleTitle = sampleTitle;
        this.imageDownloadUri = imageDownloadUri;
    }*/

    public SampleSearchQuery(Long id, String artistName, String sampleTitle, String imageDownloadUri) {
        this.id = id;
        this.artistName = artistName;
        this.sampleTitle = sampleTitle;
        this.imageDownloadUri = imageDownloadUri;
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

    public String getSampleTitle() {
        return sampleTitle;
    }

    public void setSampleTitle(String sampleTitle) {
        this.sampleTitle = sampleTitle;
    }

    public String getImageDownloadUri() {
        return imageDownloadUri;
    }

    public void setImageDownloadUri(String imageDownloadUri) {
        this.imageDownloadUri = imageDownloadUri;
    }
}

