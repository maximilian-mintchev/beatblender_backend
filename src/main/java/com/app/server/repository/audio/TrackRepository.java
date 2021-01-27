package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

//@Repository
//public interface TrackRepository extends AudioUnitRepository {
//
//}
//@Transactional
@Repository
public interface TrackRepository extends JpaRepository<Track, String> {


    Optional<Track> findByAudioUnit(AudioUnit audioUnit);
}
