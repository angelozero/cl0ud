package com.angelozero.cl0ud.gateway.repository;

import com.angelozero.cl0ud.gateway.postgressql.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT p FROM PersonEntity p WHERE lower(p.name) LIKE lower(concat('%', :name, '%'))")
    Page<PersonEntity> findPersonsByName(@Param("name") String name, Pageable pageable);

}
