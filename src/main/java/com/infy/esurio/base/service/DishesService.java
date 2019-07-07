package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.DishesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Dishes}.
 */
public interface DishesService {

    /**
     * Save a dishes.
     *
     * @param dishesDTO the entity to save.
     * @return the persisted entity.
     */
    DishesDTO save(DishesDTO dishesDTO);

    /**
     * Get all the dishes.
     *
     * @return the list of entities.
     */
    List<DishesDTO> findAll();


    /**
     * Get the "id" dishes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DishesDTO> findOne(Long id);

    /**
     * Delete the "id" dishes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
