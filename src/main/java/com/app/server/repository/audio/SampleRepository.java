package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.Sample;
import com.app.server.model.user.ArtistAlias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//public interface SampleRepository extends AudioUnitRepository {
//
//
//    @Query(value="SELECT * FROM sample s WHERE s.basic_user_id = (:userID) AND s.audio_file_path = (:fileName)", nativeQuery=true)
//    Optional<List<com.app.server.model.audio.Sample>> findByUsernameAndFilename(@Param("userID") Long userID, @Param("fileName") String fileName);
//
//    @Query(value="SELECT * FROM sample s WHERE s.sample_details_id IN (:detailIds)", nativeQuery=true)     // 2. Spring JPA In cause using @Query
//    Optional<List<com.app.server.model.audio.Sample>> findBySampleDetailsId(@Param("detailIds") List<Long> ids);
//
//    @Query(value="SELECT * FROM sample s WHERE s.artist_name LIKE %:searchString% OR s.sample_title LIKE %:searchString%", nativeQuery=true)     // 2. Spring JPA In cause using @Query
//    Optional<List<com.app.server.model.audio.Sample>> findBySearchString(@Param("searchString") String searchString);
//
//    /*@Query(value="SELECT * FROM sample s WHERE s.artist_name LIKE %:searchString%", nativeQuery=true)     // 2. Spring JPA In cause using @Query
//    Optional<List<Sample>> findBySearchString(@Param("searchString") String searchString);*/
//    @Query(value="SELECT * FROM sample s WHERE s.id IN (:sampleIds)", nativeQuery = true)
//    Optional<List<com.app.server.model.audio.Sample>> findAllBySampleId(@Param("sampleIds") List<Long> ids);
//    //Optional<Sample> findBysampleDetailsId(Long idSampleDetail);
//}
//@Transactional
@Repository
public interface SampleRepository extends PagingAndSortingRepository<Sample, String> {

    @Query("SELECT s FROM Sample s, AudioUnit au, ArtistAlias aa WHERE s.audioUnit = au AND au.artistAlias = aa AND aa.artistName LIKE %:searchString% OR au.title LIKE %:searchString%")
        // 2. Spring JPA In cause using @Query
    Optional<List<Sample>> findSampleLike(@Param("searchString") String searchString);

    Optional<Sample> findByAudioUnit(AudioUnit audioUnit);

    @Query("SELECT s FROM Sample s, ArtistAlias al, AudioUnit  au WHERE au.artistAlias = al AND s.audioUnit = au AND (al.artistName LIKE %:searchString% OR au.title LIKE %:searchString%) ORDER BY s.audioUnit.title")     // 2. Spring JPA In cause using @Query
    Page<Sample> findSamplesLike(@Param("searchString") String searchString, Pageable pageable);

//    @Query("SELECT DISTINCT s FROM AudioUnit au, Sample s JOIN s.moods m WHERE s.audioUnit = au AND (au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString%) AND (s.tempo BETWEEN (:minTempo) AND (:maxTempo)) AND (m IN (:moods)) AND (s.genre IN (:genres))")
//    Page<Sample> filterTest1(@Param("searchString") String searchString, @Param("genres") List<String> genres, @Param("moods") List<String> moods ,@Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);


    @Query("SELECT s FROM Sample s, AudioUnit au, ArtistAlias aa WHERE s.audioUnit = au AND au.artistAlias = aa AND aa.artistAliasID = (:artistAliasID)")
    Page<Sample> findSamplePageByArtistAliasID(@Param("artistAliasID") String artistAliasID, Pageable pageable);
    /*
        @Query("SELECT s FROM Sample s, AudioUnit au JOIN au.moods m WHERE s.audioUnit = au AND m IN (:moods) AND au.genre = (:genre) AND au.tempo = (:tempo)")
        Optional<Set<Sample>> filterAudioUnit(@Param("moods") Set<String> moods, @Param("genre") String genre, @Param("tempo") int tempo);
    */
/*
    @Query("SELECT DISTINCT s FROM Sample s, AudioUnit au JOIN au.moods m WHERE s.audioUnit = au AND au.title LIKE (:searchString) OR au.artistAlias.artistName LIKE (:searchString) AND m IN (:moods) AND au.genre IN (:genres) AND au.tempo BETWEEN (:minTempo) AND (:maxTempo) AND au.lep BETWEEN (:minLep) AND (:maxLep)")
    Page<Sample> filterAudioUnit(@Param("searchString") String searchString, @Param("genre") Set<String> genres, @Param("moods") Set<String> moods, @Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, @Param("minLep") Integer minLep, @Param("maxLep") Integer maxLep, Pageable pageable);
*/





}
