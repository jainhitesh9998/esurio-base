package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Outlets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Outlets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutletsRepository extends JpaRepository<Outlets, Long> {

}
