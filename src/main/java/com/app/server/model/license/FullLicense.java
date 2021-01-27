package com.app.server.model.license;


import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.audio.TimeSnippet;
import com.app.server.model.user.Artist;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="full_license")
public class FullLicense {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fullLicenseID;

    @Column(name="extension_date")
    private LocalDateTime extensionDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="used_in_tack_fk")
    private AudioUnit usedInTrack;

    @OneToMany(mappedBy = "timeSnippetID")
    private List<TimeSnippet> timeSnippetList;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "basic_license_fk")
    private BasicLicense basicLicense;

    public FullLicense() {
    }

    public FullLicense(AudioUnit usedInTrack, List<TimeSnippet> timeSnippetList) {
        this.extensionDate = LocalDateTime.now();
        this.usedInTrack = usedInTrack;
        this.timeSnippetList = timeSnippetList;
    }

    public String getFullLicenseID() {
        return fullLicenseID;
    }

    public void setFullLicenseID(String fullLicenseID) {
        this.fullLicenseID = fullLicenseID;
    }

    public LocalDateTime getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(LocalDateTime extensionDate) {
        this.extensionDate = extensionDate;
    }

    public AudioUnit getUsedInTrack() {
        return usedInTrack;
    }

    public void setUsedInTrack(AudioUnit usedInTrack) {
        this.usedInTrack = usedInTrack;
    }

    public List<TimeSnippet> getTimeSnippetList() {
        return timeSnippetList;
    }

    public void setTimeSnippetList(List<TimeSnippet> timeSnippetList) {
        this.timeSnippetList = timeSnippetList;
    }


}
