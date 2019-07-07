package com.infy.esurio.base.repository;

import com.infy.esurio.base.domain.Centers;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Centers entity.
 */
@Component
@SuppressWarnings("unused")
@Repository("CentersRepositoryExt")
public interface CentersRepositoryExt extends com.infy.esurio.base.repository.CentersRepository, JpaRepository<Centers, Long> {

    public Optional<Centers> findByIdentifier(String Identifier);
}
