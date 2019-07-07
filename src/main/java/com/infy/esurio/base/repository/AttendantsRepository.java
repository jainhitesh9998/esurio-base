package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Attendants;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Attendants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendantsRepository extends JpaRepository<Attendants, Long> {

}
