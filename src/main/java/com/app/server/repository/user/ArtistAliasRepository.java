package com.app.server.repository.user;

import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtistAliasRepository extends JpaRepository<ArtistAlias, String> {

    Optional<List<ArtistAlias>> findByArtist(Artist artist);

}
