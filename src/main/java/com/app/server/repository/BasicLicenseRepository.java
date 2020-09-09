package com.app.server.repository;

import com.app.server.model.BasicLicense;
import com.app.server.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicLicenseRepository extends JpaRepository<BasicLicense, Long> {

    @Query(value = "SELECT * FROM basic_license bl WHERE bl.downloader_id = (:downloaderID) AND bl.sample_id = (:sampleID)", nativeQuery = true)
    Optional<BasicLicense> findByDownloaderIDAndSampleID(@Param("downloaderID") Long downloaderID, @Param("sampleID") Long sampleID);

}
