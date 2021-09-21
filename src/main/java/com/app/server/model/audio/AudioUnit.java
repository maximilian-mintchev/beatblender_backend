package com.app.server.model.audio;



import com.app.server.model.user.ArtistAlias;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;


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

//    @ManyToOne(
//            fetch = FetchType.EAGER,
////            cascade = CascadeType.ALL,
//            optional = false
//    )
//    @JoinColumn(name = "creator_fk")
//    private Artist creator;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "artist_alias_fk")
    private ArtistAlias artistAlias;

    @Column(name = "title", updatable = true, nullable = false)
    private String title;

    @Column(name="audio_file_name")
    private String audioFileName;

    @Column(name="image_file_name")
    private String imageFileName;

    @Column(name = "downloads")
    private int downloads;

    @Column(name = "streams")
    private int streams;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
//    @Column(name = "title", updatable = true, nullable = false)
//    private String title;
//
//    @Column(name = "genre", updatable = true, nullable = false)
//    private String genre;
//
//    @Column(name = "tempo", nullable = false, updatable = true)
//    private int tempo;

    //   //Also joinColumns = @JoinColumn(name = "PERSON_ID" is not required as you are working with String basic type.
    //Source: https://stackoverflow.com/questions/22319265/collectiontable-and-elementcollection-is-generating-exception
    /*@ManyToMany
    @JoinTable(
            name = "has_mood",
            joinColumns = @JoinColumn(name="audio_unit"),
           inverseJoinColumns = @JoinColumn(name="mood")
    )*/
//    @ElementCollection
//    @CollectionTable(name = "audio_unit_moods")
//    private Set<String> moods;

//    @ManyToMany
//    @JoinTable(
//            name="has_tag",
//            joinColumns = @JoinColumn(name="audio_unit"),
//            inverseJoinColumns = @JoinColumn(name="tags")
//    )
//    @ElementCollection
//    @CollectionTable(name = "audio_unit_tags", joinColumns = @JoinColumn(name = "audio_unit_id"))
//    private Set<String> tags;

//    @Column(name = "is_locked", nullable = false, updatable = true)
//    private boolean isLocked;

//    @Column(name = "licenseType")
//    private LicenseType licenseType;

//    @Enumerated(EnumType.STRING)
//    private LicenseType licenseType;




//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "artist_alias_fk")
//    private ArtistAlias artistAlias;


    public AudioUnit() {
    }

    public AudioUnit(ArtistAlias artistAlias, String title , String audioFileName, String imageFileName) {
        this.title = title;
        this.artistAlias = artistAlias;
        this.audioFileName = audioFileName;
        this.imageFileName = imageFileName;
        this.uploadDate = LocalDateTime.now();
        this.downloads = 0;
        this.streams = 0;
    }

    public String getAudioUnitID() {
        return audioUnitID;
    }

    public void setAudioUnitID(String audioUnitID) {
        this.audioUnitID = audioUnitID;
    }

    public ArtistAlias getArtistAlias() {
        return artistAlias;
    }

    public void setArtistAlias(ArtistAlias artistAlias) {
        this.artistAlias = artistAlias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStreams() {
        return streams;
    }

    public void setStreams(int streams) {
        this.streams = streams;
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
}
