package com.app.server.repository.license;

import com.app.server.model.audio.Track;
import com.app.server.model.license.FullLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

//@Transactional
@Repository
public interface FullLicenseRepository extends JpaRepository<FullLicense, String> {

    Optional<FullLicense> findByTrack(Track track);

}
