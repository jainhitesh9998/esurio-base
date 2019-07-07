package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Esuriits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Esuriits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsuriitsRepository extends JpaRepository<Esuriits, Long> {

}
