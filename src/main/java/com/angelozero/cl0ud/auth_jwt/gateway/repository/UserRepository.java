package com.angelozero.cl0ud.auth_jwt.gateway.repository;

import com.angelozero.cl0ud.auth_jwt.gateway.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.email =:email")
    UserEntity findUserByEmail(@Param("email") String email);

}
