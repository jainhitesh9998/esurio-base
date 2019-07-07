package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.MenusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Menus}.
 */
public interface MenusService {

    /**
     * Save a menus.
     *
     * @param menusDTO the entity to save.
     * @return the persisted entity.
     */
    MenusDTO save(MenusDTO menusDTO);

    /**
     * Get all the menus.
     *
     * @return the list of entities.
     */
    List<MenusDTO> findAll();


    /**
     * Get the "id" menus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenusDTO> findOne(Long id);

    /**
     * Delete the "id" menus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
