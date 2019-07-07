package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Vendors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vendors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorsRepository extends JpaRepository<Vendors, Long> {

}
