package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.FoodcourtsService;
import com.infy.esurio.base.domain.Foodcourts;
import com.infy.esurio.base.repository.FoodcourtsRepository;
import com.infy.esurio.base.service.dto.FoodcourtsDTO;
import com.infy.esurio.base.service.mapper.FoodcourtsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Foodcourts}.
 */
@Service
@Transactional
public class FoodcourtsServiceImpl implements FoodcourtsService {

    private final Logger log = LoggerFactory.getLogger(FoodcourtsServiceImpl.class);

    private final FoodcourtsRepository foodcourtsRepository;

    private final FoodcourtsMapper foodcourtsMapper;

    public FoodcourtsServiceImpl(FoodcourtsRepository foodcourtsRepository, FoodcourtsMapper foodcourtsMapper) {
        this.foodcourtsRepository = foodcourtsRepository;
        this.foodcourtsMapper = foodcourtsMapper;
    }

    /**
     * Save a foodcourts.
     *
     * @param foodcourtsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FoodcourtsDTO save(FoodcourtsDTO foodcourtsDTO) {
        log.debug("Request to save Foodcourts : {}", foodcourtsDTO);
        Foodcourts foodcourts = foodcourtsMapper.toEntity(foodcourtsDTO);
        foodcourts = foodcourtsRepository.save(foodcourts);
        return foodcourtsMapper.toDto(foodcourts);
    }

    /**
     * Get all the foodcourts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FoodcourtsDTO> findAll() {
        log.debug("Request to get all Foodcourts");
        return foodcourtsRepository.findAll().stream()
            .map(foodcourtsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foodcourts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FoodcourtsDTO> findOne(Long id) {
        log.debug("Request to get Foodcourts : {}", id);
        return foodcourtsRepository.findById(id)
            .map(foodcourtsMapper::toDto);
    }

    /**
     * Delete the foodcourts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Foodcourts : {}", id);
        foodcourtsRepository.deleteById(id);
    }
}
