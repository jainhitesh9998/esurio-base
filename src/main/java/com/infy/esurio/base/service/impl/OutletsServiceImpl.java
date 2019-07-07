package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.OutletsService;
import com.infy.esurio.base.domain.Outlets;
import com.infy.esurio.base.repository.OutletsRepository;
import com.infy.esurio.base.service.dto.OutletsDTO;
import com.infy.esurio.base.service.mapper.OutletsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Outlets}.
 */
@Service
@Transactional
public class OutletsServiceImpl implements OutletsService {

    private final Logger log = LoggerFactory.getLogger(OutletsServiceImpl.class);

    private final OutletsRepository outletsRepository;

    private final OutletsMapper outletsMapper;

    public OutletsServiceImpl(OutletsRepository outletsRepository, OutletsMapper outletsMapper) {
        this.outletsRepository = outletsRepository;
        this.outletsMapper = outletsMapper;
    }

    /**
     * Save a outlets.
     *
     * @param outletsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OutletsDTO save(OutletsDTO outletsDTO) {
        log.debug("Request to save Outlets : {}", outletsDTO);
        Outlets outlets = outletsMapper.toEntity(outletsDTO);
        outlets = outletsRepository.save(outlets);
        return outletsMapper.toDto(outlets);
    }

    /**
     * Get all the outlets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OutletsDTO> findAll() {
        log.debug("Request to get all Outlets");
        return outletsRepository.findAll().stream()
            .map(outletsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one outlets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OutletsDTO> findOne(Long id) {
        log.debug("Request to get Outlets : {}", id);
        return outletsRepository.findById(id)
            .map(outletsMapper::toDto);
    }

    /**
     * Delete the outlets by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Outlets : {}", id);
        outletsRepository.deleteById(id);
    }
}
