package com.angelozero.cl0ud.jwt.gateway;

import com.angelozero.cl0ud.jwt.gateway.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT email FROM UserEntity u WHERE u.email =:email")
    Optional<UserEntity> findUserByEmail(@Param("email") String email);

}
