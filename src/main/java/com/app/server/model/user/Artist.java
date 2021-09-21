package com.app.server.model.user;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String artistID;

//    @Column(name = "lep", updatable = true, nullable = false)
//    private int lep;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

//    @Column(name="real_first_name")
//    private String realFirstName;
//
//    @Column(name="real_last_name")
//    private String realLastName;

//    @Column(name="birth_date")
//    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_fk")
    private User user;

    @Column(name="current_artist_alias_id")
    private String currentArtistAliasID;

    public Artist(User user) {
//        this.realFirstName = realFirstName;
//        this.realLastName = realLastName;
//        this.birthDate = birthDate;
        this.user = user;
//        this.lep = 0;
        this.creationDate = LocalDateTime.now();
    }

    public Artist() {

    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurrentArtistAliasID() {
        return currentArtistAliasID;
    }

    public void setCurrentArtistAliasID(String currentArtistAliasID) {
        this.currentArtistAliasID = currentArtistAliasID;
    }

    //    public int increaseLep() {
//        this.lep += 1;
//        return this.lep;
//    }


}
