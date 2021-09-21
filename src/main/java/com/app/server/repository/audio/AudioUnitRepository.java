package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
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
import java.util.Set;

//@Repository
//public interface AudioUnitRepository extends JpaRepository<AudioUnit, String> {
//
//
//
//    @Query(value="SELECT * FROM audio_unit au, artist_alias al WHERE au.artist_alias_id = al.id AND al.name LIKE %:searchString% OR au.title LIKE %:searchString%", nativeQuery=true)     // 2. Spring JPA In cause using @Query
//    Optional<List<AudioUnit>> findAudioUnitLike(@Param("searchString") String searchString);
//
//
//
//}
// Source: https://blog.netgloo.com/2014/12/18/handling-entities-inheritance-with-spring-data-jpa/
//@Transactional
@Repository
public interface AudioUnitRepository extends JpaRepository<AudioUnit, String> {
//    #{#entityName}

//    @Query("SELECT au FROM AudioUnit au, ArtistAlias al WHERE au.artistAlias = al AND al.artistName LIKE %:searchString% OR au.title LIKE %:searchString%")     // 2. Spring JPA In cause using @Query
//    Optional<List<AudioUnit>> findAudioUnitLike(@Param("searchString") String searchString);
    Optional<AudioUnit> findByAudioUnitID(String audioUnitID);

    Optional<List<AudioUnit>> findByArtistAlias(ArtistAlias artistAlias);

//    Optional<AudioUnit> findByArtistAlias(ArtistAlias artistAlias);

//    @Query("SELECT DISTINCT au FROM AudioUnit au, ArtistAlias al WHERE au.artistAlias = al AND al.artistName LIKE %:searchString% OR au.title LIKE %:searchString%")     // 2. Spring JPA In cause using @Query
//    Page<AudioUnit> findAudioUnitLike(@Param("searchString") String searchString, Pageable pageable);

//    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN au.moods m WHERE s.audioUnit = au AND au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString% AND m IN (:moods) AND au.genre IN (:genres) AND au.tempo BETWEEN (:minTempo) AND (:maxTempo)")
//    Page<AudioUnit> filterAudioUnit(@Param("searchString") String searchString, @Param("genres") Set<String> genres, @Param("moods") Set<String> moods, @Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);
//
//    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN au.moods m WHERE s.audioUnit = au AND au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString%")
//    Page<AudioUnit> filterTest(@Param("searchString") String searchString, Pageable pageable);
//

//    AND m IN (:moods) AND au.genre IN (:genres) AND au.tempo BETWEEN (:minTempo) AND (:maxTempo) AND au.lep BETWEEN (:minLep) AND (:maxLep)

    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN s.moods m WHERE s.audioUnit = au AND (au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString%) AND (s.tempo BETWEEN (:minTempo) AND (:maxTempo)) AND (m IN (:moods)) AND (s.genre IN (:genres))")
    Page<AudioUnit> filterTest1(@Param("searchString") String searchString, @Param("genres") List<String> genres, @Param("moods") List<String> moods ,@Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);

//    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN au.moods m WHERE s.audioUnit = au AND (au.tempo BETWEEN (:minTempo) AND (:maxTempo)) AND (m IN (:moods)) AND (au.genre IN (:genres))")
//    Page<AudioUnit> filterAudioUnits(@Param("genres") List<String> genres, @Param("moods") List<String> moods ,@Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);




/*
    @Query("SELECT au FROM Sample s, AudioUnit au WHERE s.audioUnit = au")
    Page<AudioUnit> findAllSamples(Pageable pageable);
*/

}
