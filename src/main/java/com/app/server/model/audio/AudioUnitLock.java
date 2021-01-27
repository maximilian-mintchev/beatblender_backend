package com.app.server.model.audio;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="audio_unit_lock")
public class AudioUnitLock {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "lock_id", unique = true)
    private String lockID;

    @Column(name="locked_until")
    private LocalDateTime lockedUntil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="audio_unit_fk")
    private AudioUnit audioUnit;

    public AudioUnitLock(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
        this.lockedUntil = LocalDateTime.now();
    }

    public AudioUnitLock() {
    }

    public String getLockID() {
        return lockID;
    }

    public void setLockID(String lockID) {
        this.lockID = lockID;
    }

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public AudioUnit getAudioUnit() {
        return audioUnit;
    }

    public void setAudioUnit(AudioUnit audioUnit) {
        this.audioUnit = audioUnit;
    }
}
