package com.app.server.model.audio;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "audio_unit_fk")
    private AudioUnit audioUnit;

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

    public MixedIn(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }

    //    public MixedIn(AudioUnit audioUnit, List<TimeSnippet> timeSnippets) {
//        this.audioUnit = audioUnit;
//        this.timeSnippets = timeSnippets;
//    }

    public AudioUnit getAudioUnit() {
        return audioUnit;
    }

    public void setAudioUnit(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }

//    public List<TimeSnippet> getTimeSnippets() {
//        return timeSnippets;
//    }
//
//    public void setTimeSnippets(List<TimeSnippet> timeSnippets) {
//        this.timeSnippets = timeSnippets;
//    }
}
