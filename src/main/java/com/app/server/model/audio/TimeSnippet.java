package com.app.server.model.audio;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "time_snippets")
public class TimeSnippet {
    // PairTupel Implemation: https://stackoverflow.com/questions/521171/a-java-collection-of-value-pairs-tuples

    //    @Id
//    @GenericGenerator(
//            name="UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    @Column(name = "time_snippet_id")
//    )
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String timeSnippetID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "start_time_fk")
    private Time startTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "end_time_fk")
    private Time endTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="mixed_in_fk")
    private MixedIn mixedIn;

    public TimeSnippet() {
    }

    public TimeSnippet(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTimeSnippetID() {
        return timeSnippetID;
    }

    public void setTimeSnippetID(String timeSnippetID) {
        this.timeSnippetID = timeSnippetID;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
