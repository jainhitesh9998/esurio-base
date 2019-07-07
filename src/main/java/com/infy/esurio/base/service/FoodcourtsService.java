package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.FoodcourtsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Foodcourts}.
 */
public interface FoodcourtsService {

    /**
     * Save a foodcourts.
     *
     * @param foodcourtsDTO the entity to save.
     * @return the persisted entity.
     */
    FoodcourtsDTO save(FoodcourtsDTO foodcourtsDTO);

    /**
     * Get all the foodcourts.
     *
     * @return the list of entities.
     */
    List<FoodcourtsDTO> findAll();


    /**
     * Get the "id" foodcourts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoodcourtsDTO> findOne(Long id);

    /**
     * Delete the "id" foodcourts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
