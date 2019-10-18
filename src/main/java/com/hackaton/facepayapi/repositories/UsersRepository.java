package com.hackaton.facepayapi.repositories;

import com.hackaton.facepayapi.daos.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

    Optional<UsersEntity> findByUserName(String userName);

    Optional<UsersEntity> findByUserNameAndPassword(String userName, String password);

    Optional<UsersEntity> findByFaceId(String faceId);



}

