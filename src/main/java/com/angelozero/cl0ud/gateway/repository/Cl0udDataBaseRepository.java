package com.angelozero.cl0ud.gateway.repository;

import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cl0udDataBaseRepository extends JpaRepository<PersonEntity, Long> {

}
