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

    @Column(name = "lep", updatable = true, nullable = false)
    private int lep;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name="real_first_name")
    private String realFirstName;

    @Column(name="real_last_name")
    private String realLastName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_fk")
    private User user;

    public Artist(String realFirstName, String realLastName, LocalDate birthDate, User user) {
        this.realFirstName = realFirstName;
        this.realLastName = realLastName;
        this.birthDate = birthDate;
        this.user = user;
        this.lep = 0;
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

    public int getLep() {
        return lep;
    }

    public void setLep(int lep) {
        this.lep = lep;
    }

    public String getRealFirstName() {
        return realFirstName;
    }

    public void setRealFirstName(String realFirstName) {
        this.realFirstName = realFirstName;
    }

    public String getRealLastName() {
        return realLastName;
    }

    public void setRealLastName(String realLastName) {
        this.realLastName = realLastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int increaseLep() {
        this.lep += 1;
        return this.lep;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistID='" + artistID + '\'' +
                ", lep=" + lep +
                ", creationDate=" + creationDate +
                ", realFirstName='" + realFirstName + '\'' +
                ", realLastName='" + realLastName + '\'' +
                ", birtDate=" + birthDate +
                ", user=" + user +
                '}';
    }
}
