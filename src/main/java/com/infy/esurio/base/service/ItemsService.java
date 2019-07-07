package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.ItemsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Items}.
 */
public interface ItemsService {

    /**
     * Save a items.
     *
     * @param itemsDTO the entity to save.
     * @return the persisted entity.
     */
    ItemsDTO save(ItemsDTO itemsDTO);

    /**
     * Get all the items.
     *
     * @return the list of entities.
     */
    List<ItemsDTO> findAll();


    /**
     * Get the "id" items.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemsDTO> findOne(Long id);

    /**
     * Delete the "id" items.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
