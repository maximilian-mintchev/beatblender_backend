package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Track;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
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

    @Query("SELECT t FROM Track t, AudioUnit  au WHERE t.audioUnit = au AND au.artistAlias = (:artistAlias)")     // 2. Spring JPA In cause using @Query
    Optional<List<Track>> findByArtistAlias(ArtistAlias artistAlias);



//    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN au.moods m WHERE s.audioUnit = au AND (au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString%) AND (au.tempo BETWEEN (:minTempo) AND (:maxTempo)) AND (m IN (:moods)) AND (au.genre IN (:genres))")
//    Page<AudioUnit> filterTracksByTitle(@Param("searchString") String searchString, @Param("genres") List<String> genres, @Param("categories") ,@Param("moods") List<String> moods , @Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);


}
