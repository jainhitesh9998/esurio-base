package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Servings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Servings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServingsRepository extends JpaRepository<Servings, Long> {

}
