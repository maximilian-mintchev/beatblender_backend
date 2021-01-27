package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.user.ArtistAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    @Query("SELECT au FROM AudioUnit au, ArtistAlias al WHERE au.artistAlias = al AND al.artistName LIKE %:searchString% OR au.title LIKE %:searchString%")     // 2. Spring JPA In cause using @Query
    Optional<List<AudioUnit>> findAudioUnitLike(@Param("searchString") String searchString);


    Optional<AudioUnit> findByArtistAlias(ArtistAlias artistAlias);
}
