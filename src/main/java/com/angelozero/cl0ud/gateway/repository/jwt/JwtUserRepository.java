package com.angelozero.cl0ud.gateway.repository.jwt;

import com.angelozero.cl0ud.gateway.postgressql.entity.jwt.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtUserRepository extends JpaRepository<UserEntity, Long> {


    @Query("SELECT u FROM UserEntity u WHERE u.userName =:name")
    UserEntity findByUserName(@Param("name") String name);
}
