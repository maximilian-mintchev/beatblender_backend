package com.app.server.repository;

import com.app.server.model.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicUserRepository extends JpaRepository<BasicUser, Long> {
    Optional<BasicUser> findByBasicUserName(String basicUserName);
}
