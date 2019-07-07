package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.OutletsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Outlets}.
 */
public interface OutletsService {

    /**
     * Save a outlets.
     *
     * @param outletsDTO the entity to save.
     * @return the persisted entity.
     */
    OutletsDTO save(OutletsDTO outletsDTO);

    /**
     * Get all the outlets.
     *
     * @return the list of entities.
     */
    List<OutletsDTO> findAll();


    /**
     * Get the "id" outlets.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OutletsDTO> findOne(Long id);

    /**
     * Delete the "id" outlets.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
