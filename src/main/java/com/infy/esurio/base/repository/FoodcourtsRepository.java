package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Foodcourts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Foodcourts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodcourtsRepository extends JpaRepository<Foodcourts, Long> {

}
