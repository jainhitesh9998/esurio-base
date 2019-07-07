package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.TagsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Tags}.
 */
public interface TagsService {

    /**
     * Save a tags.
     *
     * @param tagsDTO the entity to save.
     * @return the persisted entity.
     */
    TagsDTO save(TagsDTO tagsDTO);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<TagsDTO> findAll();


    /**
     * Get the "id" tags.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagsDTO> findOne(Long id);

    /**
     * Delete the "id" tags.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
