package com.app.server.repository;

import com.app.server.model.SampleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SampleDetailsRepository extends JpaRepository<SampleDetails, Long> {

    //@Query("SELECT sd FROM SampleDetails sd WHERE sd.genre IN (:genres)")     // 2. Spring JPA In cause using @Query
    @Query(value="SELECT * FROM sample_details sd WHERE sd.genre IN (:genres) AND sd.region IN (:regions) AND sd.track_type IN (:trackTypes) AND sd.song_key IN (:songKeys)", nativeQuery=true)     // 2. Spring JPA In cause using @Query
    Optional<List<SampleDetails>> findBySampleDetailsGenre(@Param("genres")List<String> genres, @Param("regions")List<String> regions, @Param("trackTypes")List<String> trackTypes, @Param("songKeys")List<String> songKeys);




}
