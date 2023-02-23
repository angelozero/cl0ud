package com.angelozero.cl0ud.gateway.repository.dao;

import com.angelozero.cl0ud.gateway.postgressql.entity.dao.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
