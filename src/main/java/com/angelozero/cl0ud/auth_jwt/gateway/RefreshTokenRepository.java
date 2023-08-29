package com.angelozero.cl0ud.auth_jwt.gateway;

import com.angelozero.cl0ud.auth_jwt.gateway.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    @Query("SELECT t FROM RefreshTokenEntity t WHERE t.token =:token")
    RefreshTokenEntity findByToken(String token);
}
