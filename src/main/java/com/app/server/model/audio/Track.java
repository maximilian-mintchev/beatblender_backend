package com.app.server.model.audio;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    @JoinColumn(name = "audio_unit_fk")
    private AudioUnit audioUnit;

    public Track() {

    }

    public Track(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }

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
}
