package com.app.server.model.license;


import com.app.server.enums.LicenseType;
import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "basic_license")
public class BasicLicense {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String basicLicenseID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "downloader_fk", nullable = false)
    private User downloader;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audio_unit_fk", nullable = false)
    private Sample sample;

//    @Column(name = "full_license_price")
//    private LicenseType licenseType;

    @Column(name = "creation_date")
    private LocalDateTime creationdDate;


    public BasicLicense() {
    }

    public BasicLicense(User downloader, Sample sample) {
        this.downloader = downloader;
        this.sample = sample;
//        this.licenseType = sample.getAudioUnit().getLicenseType();
        creationdDate = LocalDateTime.now();
    }



    public String getBasicLicenseID() {
        return basicLicenseID;
    }

    public void setBasicLicenseID(String basicLicenseID) {
        this.basicLicenseID = basicLicenseID;
    }

    public User getDownloader() {
        return downloader;
    }

    public void setDownloader(User downloader) {
        this.downloader = downloader;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }



    public LocalDateTime getCreationdDate() {
        return creationdDate;
    }

    public void setCreationdDate(LocalDateTime creationdDate) {
        this.creationdDate = creationdDate;
    }
}
