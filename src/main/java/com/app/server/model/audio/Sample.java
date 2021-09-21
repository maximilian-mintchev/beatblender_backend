package com.app.server.model.audio;

import com.app.server.enums.LicenseType;
import com.app.server.model.user.ArtistAlias;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

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

//    @Column(name = "title", updatable = true, nullable = false)
//    private String title;

    @Column(name = "genre", updatable = true, nullable = false)
    private String genre;

    @Column(name = "tempo", nullable = false, updatable = true)
    private int tempo;

    @ElementCollection
    @CollectionTable(name = "audio_unit_moods")
    private Set<String> moods;


    @ElementCollection
    @CollectionTable(name = "audio_unit_tags", joinColumns = @JoinColumn(name = "audio_unit_id"))
    private Set<String> tags;



//    @Enumerated(EnumType.STRING)
//    private LicenseType licenseType;
//
//    @Column(name = "downloads")
//    private int downloads;

    public Sample() {

    }

    public Sample(AudioUnit audioUnit, String genre, int tempo, Set<String> moods, Set<String> tags) {
        this.audioUnit = audioUnit;
//        this.title = title;
        this.genre = genre;
        this.tempo = tempo;
        this.moods = moods;
        this.tags = tags;
//        this.artistAlias = artistAlias;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}



