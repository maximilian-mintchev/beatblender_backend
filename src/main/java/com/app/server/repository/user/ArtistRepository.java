package com.app.server.repository.user;


import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {

    Optional<Artist> findByUser(User user);



}
