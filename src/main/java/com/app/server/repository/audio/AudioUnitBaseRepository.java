package com.app.server.repository.audio;


import com.app.server.model.audio.AudioUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AudioUnitBaseRepository<T extends AudioUnit> extends CrudRepository<T, String> {
//    , artist_alias al WHERE au.audio_unit_id = al.artist_alias_id AND al.name LIKE %:searchString% OR au.title LIKE %:searchString%
//AND al.artistName LIKE %:searchString% OR au.title LIKE %:searchString%
    @Query("SELECT au FROM #{#entityName} au, ArtistAlias al WHERE au.artistAlias = al AND al.artistName LIKE %:searchString% OR au.title LIKE %:searchString%")     // 2. Spring JPA In cause using @Query
    Optional<List<T>> findAudioUnitLike(@Param("searchString") String searchString);


//    AudioUnit findOne(String id);
//    @Query("SELECT u FROM #{#entityName} as u")
//    List<T> findAll();

    /*List<AudioUnit> findAll(Sort sort);

    Page<AudioUnit> findAll(Pageable pageable);*/

}
