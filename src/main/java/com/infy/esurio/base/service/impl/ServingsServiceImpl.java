package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.ServingsService;
import com.infy.esurio.base.domain.Servings;
import com.infy.esurio.base.repository.ServingsRepository;
import com.infy.esurio.base.service.dto.ServingsDTO;
import com.infy.esurio.base.service.mapper.ServingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Servings}.
 */
@Service
@Transactional
public class ServingsServiceImpl implements ServingsService {

    private final Logger log = LoggerFactory.getLogger(ServingsServiceImpl.class);

    private final ServingsRepository servingsRepository;

    private final ServingsMapper servingsMapper;

    public ServingsServiceImpl(ServingsRepository servingsRepository, ServingsMapper servingsMapper) {
        this.servingsRepository = servingsRepository;
        this.servingsMapper = servingsMapper;
    }

    /**
     * Save a servings.
     *
     * @param servingsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServingsDTO save(ServingsDTO servingsDTO) {
        log.debug("Request to save Servings : {}", servingsDTO);
        Servings servings = servingsMapper.toEntity(servingsDTO);
        servings = servingsRepository.save(servings);
        return servingsMapper.toDto(servings);
    }

    /**
     * Get all the servings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServingsDTO> findAll() {
        log.debug("Request to get all Servings");
        return servingsRepository.findAll().stream()
            .map(servingsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one servings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServingsDTO> findOne(Long id) {
        log.debug("Request to get Servings : {}", id);
        return servingsRepository.findById(id)
            .map(servingsMapper::toDto);
    }

    /**
     * Delete the servings by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servings : {}", id);
        servingsRepository.deleteById(id);
    }
}
