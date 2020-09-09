package com.app.server.model;

import javax.persistence.*;

@Entity
@Table(name="sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    private String artistName;

    @Column(nullable = false)
    private String sampleTitle;

    @Column(nullable = false)
    private String audioFilePath;

    @Column(nullable = false)
    private String sampleImagePath;

    @Column(nullable = false)
    private String audioDownLoadUri;

    public String getAudioDownLoadUri() {
        return audioDownLoadUri;
    }

    public void setAudioDownLoadUri(String audioDownLoadUri) {
        this.audioDownLoadUri = audioDownLoadUri;
    }

    public String getImageDownLoadUri() {
        return imageDownLoadUri;
    }

    public void setImageDownLoadUri(String imageDownLoadUri) {
        this.imageDownLoadUri = imageDownLoadUri;
    }

	/*public Sample(String artistName, String sampleTitle, String audioFilePath, String sampleImagePath, String audioDownLoadUri, String imageDownLoadUri, BasicUser basicUser) {

		this.artistName = artistName;
		this.sampleTitle = sampleTitle;
		this.audioFilePath = audioFilePath;
		this.sampleImagePath = sampleImagePath;
		this.audioDownLoadUri = audioDownLoadUri;
		this.imageDownLoadUri = imageDownLoadUri;
		this.basicUser = basicUser;
	}*/

    public Sample(String artistName, String sampleTitle, String audioFilePath, String sampleImagePath, String audioDownLoadUri, String imageDownLoadUri, SampleDetails sampleDetails, BasicUser basicUser) {
        this.artistName = artistName;
        this.sampleTitle = sampleTitle;
        this.audioFilePath = audioFilePath;
        this.sampleImagePath = sampleImagePath;
        this.audioDownLoadUri = audioDownLoadUri;
        this.imageDownLoadUri = imageDownLoadUri;
        this.sampleDetails = sampleDetails;
        this.basicUser = basicUser;
        this.samplePrice = 1;
    }



    @Column(nullable = false)
    private String imageDownLoadUri;


    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private SampleDetails sampleDetails;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private BasicUser basicUser;

    @Column(name="sample_price", nullable = true)
    private Integer samplePrice;

    public Sample() {

    }



	/*public Sample(String artistName, String sampleTitle, String audioFilePath, String sampleImagePath, BasicUser basicUser) {
		this.artistName = artistName;
		this.sampleTitle = sampleTitle;
		this.audioFilePath = audioFilePath;
		this.sampleImagePath = sampleImagePath;
		this.basicUser = basicUser;
	}*/


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

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public String getSampleImagePath() {
        return sampleImagePath;
    }

    public void setSampleImagePath(String sampleImagePath) {
        this.sampleImagePath = sampleImagePath;
    }


    public SampleDetails getSampleDetails() {
        return sampleDetails;
    }

    public void setSampleDetails(SampleDetails sampleDetails) {
        this.sampleDetails = sampleDetails;
    }

    public BasicUser getBasicUser() {
        return basicUser;
    }

    public void setBasicUser(BasicUser basicUser) {
        this.basicUser = basicUser;
    }

    public int getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(int samplePrice) {
        this.samplePrice = samplePrice;
    }


}



