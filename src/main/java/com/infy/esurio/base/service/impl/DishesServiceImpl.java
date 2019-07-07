package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.DishesService;
import com.infy.esurio.base.domain.Dishes;
import com.infy.esurio.base.repository.DishesRepository;
import com.infy.esurio.base.service.dto.DishesDTO;
import com.infy.esurio.base.service.mapper.DishesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Dishes}.
 */
@Service
@Transactional
public class DishesServiceImpl implements DishesService {

    private final Logger log = LoggerFactory.getLogger(DishesServiceImpl.class);

    private final DishesRepository dishesRepository;

    private final DishesMapper dishesMapper;

    public DishesServiceImpl(DishesRepository dishesRepository, DishesMapper dishesMapper) {
        this.dishesRepository = dishesRepository;
        this.dishesMapper = dishesMapper;
    }

    /**
     * Save a dishes.
     *
     * @param dishesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DishesDTO save(DishesDTO dishesDTO) {
        log.debug("Request to save Dishes : {}", dishesDTO);
        Dishes dishes = dishesMapper.toEntity(dishesDTO);
        dishes = dishesRepository.save(dishes);
        return dishesMapper.toDto(dishes);
    }

    /**
     * Get all the dishes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DishesDTO> findAll() {
        log.debug("Request to get all Dishes");
        return dishesRepository.findAll().stream()
            .map(dishesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dishes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DishesDTO> findOne(Long id) {
        log.debug("Request to get Dishes : {}", id);
        return dishesRepository.findById(id)
            .map(dishesMapper::toDto);
    }

    /**
     * Delete the dishes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dishes : {}", id);
        dishesRepository.deleteById(id);
    }
}
