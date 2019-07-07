package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Dishes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dishes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {

}
