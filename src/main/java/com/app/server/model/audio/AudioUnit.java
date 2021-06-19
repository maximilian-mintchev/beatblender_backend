package com.app.server.model.audio;


import com.app.server.enums.AudioUnitType;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "audio_unit")
public class AudioUnit {

    //How to generate UUID: https://thorben-janssen.com/generate-uuids-primary-keys-hibernate/
    //Another way is exposed here: https://xebia.com/blog/jpa-implementation-patterns-using-uuids-as-primary-keys/

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "audio_unit_id")
    private String audioUnitID;

    @ManyToOne(
            fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(name = "creator_fk")
    private Artist creator;

    @Column(name = "title", updatable = true, nullable = false)
    private String title;

    @Column(name = "genre", updatable = true, nullable = false)
    private String genre;

    @Column(name = "tempo", nullable = false, updatable = true)
    private int tempo;

    //   //Also joinColumns = @JoinColumn(name = "PERSON_ID" is not required as you are working with String basic type.
    //Source: https://stackoverflow.com/questions/22319265/collectiontable-and-elementcollection-is-generating-exception
    /*@ManyToMany
    @JoinTable(
            name = "has_mood",
            joinColumns = @JoinColumn(name="audio_unit"),
           inverseJoinColumns = @JoinColumn(name="mood")
    )*/
    @ElementCollection
    @CollectionTable(name = "audio_unit_moods")
    private Set<String> moods;

//    @ManyToMany
//    @JoinTable(
//            name="has_tag",
//            joinColumns = @JoinColumn(name="audio_unit"),
//            inverseJoinColumns = @JoinColumn(name="tags")
//    )
    @ElementCollection
    @CollectionTable(name = "audio_unit_tags", joinColumns = @JoinColumn(name = "audio_unit_id"))
    private Set<String> tags;

//    @Column(name = "is_locked", nullable = false, updatable = true)
//    private boolean isLocked;
    @Column(name="audio_file_name")
    private String audioFileName;

    @Column(name="image_file_name")
    private String imageFileName;

    @Column(name = "LEP")
    private int lep;

    @Column(name = "downloads")
    private int downloads;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "artist_alias_fk")
    private ArtistAlias artistAlias;


    public AudioUnit() {
    }

    public AudioUnit(Artist creator, String title, String genre, int tempo, Set<String> moods, Set<String> tags, String audioFileName, String imageFileName, int lep, ArtistAlias artistAlias) {
        this.creator = creator;
        this.title = title;
        this.genre = genre;
        this.tempo = tempo;
        this.moods = moods;
        this.tags = tags;
        this.audioFileName = audioFileName;
        this.imageFileName = imageFileName;
        this.lep = lep;
        this.downloads = 0;
        this.uploadDate = LocalDateTime.now();
        this.artistAlias = artistAlias;
    }

    public String getAudioUnitID() {
        return audioUnitID;
    }

    public void setAudioUnitID(String audioUnitID) {
        this.audioUnitID = audioUnitID;
    }

    public Artist getCreator() {
        return creator;
    }

    public void setCreator(Artist creator) {
        this.creator = creator;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public int getLep() {
        return lep;
    }

    public void setLep(int lep) {
        this.lep = lep;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ArtistAlias getArtistAlias() {
        return artistAlias;
    }

    public void setArtistAlias(ArtistAlias artistAlias) {
        this.artistAlias = artistAlias;
    }

}
