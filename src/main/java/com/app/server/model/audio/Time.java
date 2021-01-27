package com.app.server.model.audio;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="time")
public class Time {

//    @Id
//    @GenericGenerator(
//            name="UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name="time")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String time;

    @Column(name="hour",nullable = false,updatable = true)
    private int hour;

    @Column(name="minute", nullable = false, updatable = true)
    private int minute;

    @Column(name="second", nullable = false, updatable = true)
    private int second;

    public Time() {
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
