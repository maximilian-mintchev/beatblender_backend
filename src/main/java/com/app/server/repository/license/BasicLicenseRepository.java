package com.app.server.repository.license;

import com.app.server.model.audio.Sample;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.server.model.license.BasicLicense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

//@Transactional
@Repository
public interface BasicLicenseRepository extends JpaRepository<BasicLicense, String> {
//    @Query(value = "SELECT * FROM basic_license bl WHERE bl.downloader_id = (:downloaderID) AND bl.sample_id = (:sampleID)", nativeQuery = true)
//    Optional<com.app.server.model.license.BasicLicense> findByDownloaderIDAndSampleID(@Param("downloaderID") Long downloaderID, @Param("sampleID") Long sampleID);

    Optional<List<BasicLicense>> findByDownloader(User user);
    Optional<BasicLicense> findByDownloaderAndSample(User artist, Sample sample);
}
