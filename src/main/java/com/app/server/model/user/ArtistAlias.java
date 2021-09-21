package com.app.server.model.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "artist_alias")
public class ArtistAlias {

    /*@Id
    @org.hibernate.annotations.Type(type="uuid-char")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @GeneratedValue(generator="UUID")
//    @GeneratedValue
//    @Column(name="artist_alias_id", nullable = false, updatable = true)
    private UUID artistAliasID;*/

    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @GeneratedValue(generator="system-uuid2")
//    @GenericGenerator(name="system-uuid2", strategy="uuid2")
//    @Column(name = "artist_alias_id")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="artist_alias_id", nullable = false, unique = true)
    private String artistAliasID;
//    private String uuid;

    @Column(name = "artist_name", nullable = false, updatable = true)
    private String artistName;

    @Column(name = "artist_image_file_name", nullable = false, updatable = true)
    private String artistImageFileName;


    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne(
            optional = false,
//            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "artist_fk")
    private Artist artist;


    public ArtistAlias(String name, Artist artist, String artistImageFileName) {
        this.artistName = name;
        this.artist = artist;
        this.creationDate = LocalDateTime.now();
        this.artistImageFileName = artistImageFileName;
    }

    public ArtistAlias(String artistName, Artist artist) {
        this.artistName = artistName;
        this.artist = artist;
    }

    public ArtistAlias() {

    }

//    public UUID getArtistAliasID() {
//        return artistAliasID;
//    }
//
//    public void setArtistAliasID(UUID artistAliasID) {
//        this.artistAliasID = artistAliasID;
//    }


    public String getArtistALiasID() {
        return artistAliasID;
    }

    public void setArtistALiasID(String artistALiasID) {
        this.artistAliasID = artistALiasID;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistImageFileName() {
        return artistImageFileName;
    }

    public void setArtistImageFileName(String artistImageFileName) {
        this.artistImageFileName = artistImageFileName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
