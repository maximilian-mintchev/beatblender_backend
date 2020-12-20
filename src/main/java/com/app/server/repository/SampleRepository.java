package com.app.server.repository;

import com.app.server.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long>
{

    @Query(value="SELECT * FROM sample s WHERE s.basic_user_id = (:userID) AND s.audio_file_path = (:fileName)", nativeQuery=true)
    Optional<List<Sample>> findByUsernameAndFilename(@Param("userID") Long userID, @Param("fileName") String fileName);

    @Query(value="SELECT * FROM sample s WHERE s.sample_details_id IN (:detailIds)", nativeQuery=true)     // 2. Spring JPA In cause using @Query
    Optional<List<Sample>> findBySampleDetailsId(@Param("detailIds") List<Long> ids);

    @Query(value="SELECT * FROM sample s WHERE s.artist_name LIKE %:searchString% OR s.sample_title LIKE %:searchString%", nativeQuery=true)     // 2. Spring JPA In cause using @Query
    Optional<List<Sample>> findBySearchString(@Param("searchString") String searchString);

    /*@Query(value="SELECT * FROM sample s WHERE s.artist_name LIKE %:searchString%", nativeQuery=true)     // 2. Spring JPA In cause using @Query
    Optional<List<Sample>> findBySearchString(@Param("searchString") String searchString);*/
    @Query(value="SELECT * FROM sample s WHERE s.id IN (:sampleIds)", nativeQuery = true)
    Optional<List<Sample>> findAllBySampleId(@Param("sampleIds") List<Long> ids);
    //Optional<Sample> findBysampleDetailsId(Long idSampleDetail);
}
