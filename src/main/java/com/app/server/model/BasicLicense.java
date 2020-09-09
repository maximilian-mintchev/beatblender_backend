package com.app.server.model;


import com.app.server.enums.LicenseStatus;

import javax.persistence.*;

@Table(name="basic_license")
@Entity
public class BasicLicense {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="downloader_id", nullable = false)
    private BasicUser downloader;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="uploader_id", nullable = false)
    private BasicUser uploader;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sample_id", nullable = false)
    private Sample sample;

    @Column(name="basic_license_path")
    private String basicLicensePath;

    @Column(name="full_license_price")
    private int fullLicensePrice;

    @Enumerated(EnumType.STRING)
    @Column(name="license_status")
    private LicenseStatus licenseStatus;

    public BasicLicense() {}

    public BasicLicense(BasicUser downloader, BasicUser uploader, Sample sample, String basicLicensePath) {
        this.downloader = downloader;
        this.uploader = uploader;
        this.sample = sample;
        this.fullLicensePrice = sample.getSamplePrice();
        this.licenseStatus = LicenseStatus.BasicLicense;
        this.basicLicensePath = basicLicensePath;
    }



    public BasicUser getUploader() {
        return uploader;
    }

    public void setUploader(BasicUser uploader) {
        this.uploader = uploader;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public int getFullLicensePrice() {
        return fullLicensePrice;
    }

    public void setFullLicensePrice(int fullLicensePrice) {
        this.fullLicensePrice = fullLicensePrice;
    }

    public LicenseStatus getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(LicenseStatus licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public BasicUser getDownloader() {
        return downloader;
    }

    public void setDownloader(BasicUser downloader) {
        this.downloader = downloader;
    }

    public String getBasicLicensePath() {
        return basicLicensePath;
    }

    public void setBasicLicensePath(String basicLicensePath) {
        this.basicLicensePath = basicLicensePath;
    }
}
