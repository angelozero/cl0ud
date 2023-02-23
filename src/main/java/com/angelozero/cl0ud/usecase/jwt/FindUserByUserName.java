package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.exception.jwt.InvalidJwtAuthException;
import com.angelozero.cl0ud.gateway.JwtSecurityGateway;
import com.angelozero.cl0ud.gateway.postgressql.entity.jwt.UserEntity;
import com.angelozero.cl0ud.usecase.jwt.mapper.UserMapper;
import com.angelozero.cl0ud.usecase.jwt.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FindUserByUserName {

    private final JwtSecurityGateway jwtSecurityGateway;
    private final UserMapper mapper;

    public User execute(String userName) throws UsernameNotFoundException {
        log.info("Encontrando um USUARIO ---> " + userName);
        UserEntity userFinded = jwtSecurityGateway.findByUserName(userName);
        log.info("USUARIO encontrado ---> " + userFinded.getFullName());
        return Optional.of(mapper.toModel(userFinded))
                .orElseThrow(() -> new InvalidJwtAuthException("Falha ao encontrar usuario"));
    }
}
