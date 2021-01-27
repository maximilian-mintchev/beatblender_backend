package com.app.server.model.audio;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//@Table(name = "samples")
@Entity
public class Sample {


//    public Sample(Artist creator, String title, String genre, int tempo, List<String> moods, List<String> tags, ArtistAlias artistAlias) {
//        super(creator, title, genre, tempo, moods, tags, artistAlias);
//    }


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "sample_id")
    private String sampleID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="audio_unit_fk")
    private AudioUnit audioUnit;

    public Sample() {

    }

    public Sample(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public AudioUnit getAudioUnit() {
        return audioUnit;
    }

    public void setAudioUnit(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }
}



