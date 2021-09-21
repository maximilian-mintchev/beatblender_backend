package com.app.server.repository.user;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ArtistAliasRepository extends JpaRepository<ArtistAlias, String> {

    Optional<List<ArtistAlias>> findByArtist(Artist artist);

//    @Query("SELECT DISTINCT aa FROM ArtistAlias aa, AudioUnit au JOIN aa.artistAliasID m WHERE au.artistAlias = aa AND au.title LIKE %:searchString% OR au.artistAlias.artistName LIKE %:searchString% AND m IN (:moods) AND au.genre IN (:genres) AND au.tempo BETWEEN (:minTempo) AND (:maxTempo)")
//    Page<ArtistAlias> findMostPopularArtists(@Param("searchString") String searchString, @Param("genres") Set<String> genres, @Param("moods") Set<String> moods, @Param("minTempo") Integer minTempo, @Param("maxTempo") Integer maxTempo, Pageable pageable);


}
