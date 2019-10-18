package com.hackaton.facepayapi.repositories;

import com.hackaton.facepayapi.daos.SessionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SessionsRepository extends JpaRepository<SessionsEntity, String> {

    Optional<SessionsEntity> findFirstBySessionIdAndLoggedOrderByDtLoggedInDesc(long sessionId, Boolean logged);
}

