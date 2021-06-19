package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//@Repository
//public interface TrackRepository extends AudioUnitRepository {
//
//}
//@Transactional
@Repository
public interface TrackRepository extends JpaRepository<Track, String> {


    Optional<Track> findByAudioUnit(AudioUnit audioUnit);



//    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN au.moods m WHERE s.audioUnit = au AND (au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString%) AND (au.tempo BETWEEN (:minTempo) AND (:maxTempo)) AND (m IN (:moods)) AND (au.genre IN (:genres))")
//    Page<AudioUnit> filterTracksByTitle(@Param("searchString") String searchString, @Param("genres") List<String> genres, @Param("categories") ,@Param("moods") List<String> moods , @Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);


}
