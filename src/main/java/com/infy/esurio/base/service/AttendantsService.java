package com.infy.esurio.base.service;

import com.infy.esurio.base.service.dto.AttendantsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.esurio.base.domain.Attendants}.
 */
public interface AttendantsService {

    /**
     * Save a attendants.
     *
     * @param attendantsDTO the entity to save.
     * @return the persisted entity.
     */
    AttendantsDTO save(AttendantsDTO attendantsDTO);

    /**
     * Get all the attendants.
     *
     * @return the list of entities.
     */
    List<AttendantsDTO> findAll();


    /**
     * Get the "id" attendants.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendantsDTO> findOne(Long id);

    /**
     * Delete the "id" attendants.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
