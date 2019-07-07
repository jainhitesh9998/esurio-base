package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.EsuriitsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Esuriits}.
 */
public interface EsuriitsService {

    /**
     * Save a esuriits.
     *
     * @param esuriitsDTO the entity to save.
     * @return the persisted entity.
     */
    EsuriitsDTO save(EsuriitsDTO esuriitsDTO);

    /**
     * Get all the esuriits.
     *
     * @return the list of entities.
     */
    List<EsuriitsDTO> findAll();


    /**
     * Get the "id" esuriits.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EsuriitsDTO> findOne(Long id);

    /**
     * Delete the "id" esuriits.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
