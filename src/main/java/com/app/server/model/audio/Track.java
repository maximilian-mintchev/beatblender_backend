package com.app.server.model.audio;


import com.app.server.enums.LicenseStatus;
import com.app.server.enums.LicenseType;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Table(name="tracks")
public class Track {

    //    public Track(Artist creator, String title, String genre, int tempo, List<String> moods, List<String> tags, ArtistAlias artistAlias) {
//        super(creator, title, genre, tempo, moods, tags, artistAlias);
//    }
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "track_id")
    private String trackID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="audio_unit_fk")
    private AudioUnit audioUnit;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "artist_fk")
//    private Artist artist;

    @Column(name = "release_artist_name", updatable = true, nullable = false)
    private String releaseArtistName;

//    @Column(name = "release_title", updatable = true, nullable = false)
//    private String releaseTitle;

    @Column(name = "licenseeName", updatable = true, nullable = false)
    private String licenseeName;

//    @Column(name="audio_file_name", updatable = true, nullable = false)
//    private String audioFileName;
//
//    @Column(name="image_file_name", updatable = true, nullable = false)
//    private String imageFileName;

    @Column(name = "first_checkbox_date", updatable = true, nullable = false)
    private LocalDateTime first_checkbox_date;

    @Column(name = "second_checkbox_date", updatable = true, nullable = false)
    private LocalDateTime second_checkbox_date;

    @Column(name="isrc", updatable = true, nullable = true)
    private String isrc;

    @Enumerated(EnumType.STRING)
    private LicenseStatus licenseStatus;

    public Track(AudioUnit audioUnit, String releaseArtistName, String releaseTitle, String licenseeName) {
        this.audioUnit = audioUnit;
        this.releaseArtistName = releaseArtistName;
//        this.releaseTitle = releaseTitle;
        this.licenseeName = licenseeName;
        this.first_checkbox_date = LocalDateTime.now();
        this.second_checkbox_date = LocalDateTime.now();
        this.licenseStatus = LicenseStatus.UNLICENSED;
    }

    //    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "audio_unit_fk")
//    private AudioUnit audioUnit;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = )
//    @OneToMany(targetEntity=MixedIn.class, mappedBy="parent",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<MixedIn> mixedIns;


    public Track() {

    }

//    public Track(AudioUnit audioUnit) {
//        this.audioUnit = audioUnit;
//    }

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public AudioUnit getAudioUnit() {
        return audioUnit;
    }

    public void setAudioUnit(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }

    public String getReleaseArtistName() {
        return releaseArtistName;
    }

    public void setReleaseArtistName(String releaseArtistName) {
        this.releaseArtistName = releaseArtistName;
    }

    public String getLicenseeName() {
        return licenseeName;
    }

    public void setLicenseeName(String licenseeName) {
        this.licenseeName = licenseeName;
    }

    public LocalDateTime getFirst_checkbox_date() {
        return first_checkbox_date;
    }

    public void setFirst_checkbox_date(LocalDateTime first_checkbox_date) {
        this.first_checkbox_date = first_checkbox_date;
    }

    public LocalDateTime getSecond_checkbox_date() {
        return second_checkbox_date;
    }

    public void setSecond_checkbox_date(LocalDateTime second_checkbox_date) {
        this.second_checkbox_date = second_checkbox_date;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public LicenseStatus getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(LicenseStatus licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    //    public AudioUnit getAudioUnit() {
//        return audioUnit;
//    }
//
//    public void setAudioUnit(AudioUnit audioUnit) {
//        this.audioUnit = audioUnit;
//    }
}
