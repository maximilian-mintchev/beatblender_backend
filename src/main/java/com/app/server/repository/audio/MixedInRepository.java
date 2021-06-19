package com.app.server.repository.audio;

import com.app.server.model.audio.AudioUnit;
import com.app.server.model.audio.MixedIn;
import com.app.server.model.audio.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MixedInRepository extends JpaRepository<MixedIn, String> {


    Optional<List<MixedIn>> findAllByParent(AudioUnit parent);

}
