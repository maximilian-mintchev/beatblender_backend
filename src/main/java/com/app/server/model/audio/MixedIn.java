package com.app.server.model.audio;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mixed_in")
public class MixedIn {

    //    @Id
//    @GenericGenerator(
//            name="UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name="mixed_in_id")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String mixedInID;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "audio_unit_fk")
    private AudioUnit child;

//    @JoinColumn(name = "track_fk")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="track_fk")
    private AudioUnit parent;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    //    @ElementCollection
//    @CollectionTable(name = "time_snips", joinColumns = @JoinColumn(name = "mixedInID"))
//
//    @OneToMany(
//            mappedBy = "timeSnippetID",
////            orphanRemoval = true,
//            fetch = FetchType.LAZY
////            cascade = CascadeType.ALL
//    )
//    private List<TimeSnippet> timeSnippets;

    public MixedIn() {
    }

    public MixedIn(AudioUnit parent, AudioUnit child) {
        this.parent = parent;
        this.child = child;
        this.creationDate = LocalDateTime.now();
    }

    //    public MixedIn(AudioUnit audioUnit, List<TimeSnippet> timeSnippets) {
//        this.audioUnit = audioUnit;
//        this.timeSnippets = timeSnippets;
//    }


    public String getMixedInID() {
        return mixedInID;
    }

    public void setMixedInID(String mixedInID) {
        this.mixedInID = mixedInID;
    }

    public AudioUnit getChild() {
        return child;
    }

    public void setChild(AudioUnit child) {
        this.child = child;
    }

    public AudioUnit getParent() {
        return parent;
    }

    public void setParent(AudioUnit parent) {
        this.parent = parent;
    }


    //    public List<TimeSnippet> getTimeSnippets() {
//        return timeSnippets;
//    }
//

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
//    public void setTimeSnippets(List<TimeSnippet> timeSnippets) {
//        this.timeSnippets = timeSnippets;
//    }
}
