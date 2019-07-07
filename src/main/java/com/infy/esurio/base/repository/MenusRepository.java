package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Menus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Menus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenusRepository extends JpaRepository<Menus, Long> {

}
