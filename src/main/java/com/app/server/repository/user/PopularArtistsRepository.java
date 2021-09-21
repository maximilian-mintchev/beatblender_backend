package com.app.server.repository.user;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PopularArtistsRepository extends JpaRepository<Artist, String> {

//    @Query("SELECT DISTINCT au FROM AudioUnit au, Sample s JOIN au.moods m WHERE s.audioUnit = au AND au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString% AND m IN (:moods) AND au.genre IN (:genres) AND au.tempo BETWEEN (:minTempo) AND (:maxTempo)")
//    Page<AudioUnit> filterAudioUnit(@Param("searchString") String searchString, @Param("genres") Set<String> genres, @Param("moods") Set<String> moods, @Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);
//


}
