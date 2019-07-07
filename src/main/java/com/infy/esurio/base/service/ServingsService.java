package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.ServingsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Servings}.
 */
public interface ServingsService {

    /**
     * Save a servings.
     *
     * @param servingsDTO the entity to save.
     * @return the persisted entity.
     */
    ServingsDTO save(ServingsDTO servingsDTO);

    /**
     * Get all the servings.
     *
     * @return the list of entities.
     */
    List<ServingsDTO> findAll();


    /**
     * Get the "id" servings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServingsDTO> findOne(Long id);

    /**
     * Delete the "id" servings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
