package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.EsuriitsService;
import com.infy.esurio.base.domain.Esuriits;
import com.infy.esurio.base.repository.EsuriitsRepository;
import com.infy.esurio.base.service.dto.EsuriitsDTO;
import com.infy.esurio.base.service.mapper.EsuriitsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Esuriits}.
 */
@Service
@Transactional
public class EsuriitsServiceImpl implements EsuriitsService {

    private final Logger log = LoggerFactory.getLogger(EsuriitsServiceImpl.class);

    private final EsuriitsRepository esuriitsRepository;

    private final EsuriitsMapper esuriitsMapper;

    public EsuriitsServiceImpl(EsuriitsRepository esuriitsRepository, EsuriitsMapper esuriitsMapper) {
        this.esuriitsRepository = esuriitsRepository;
        this.esuriitsMapper = esuriitsMapper;
    }

    /**
     * Save a esuriits.
     *
     * @param esuriitsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EsuriitsDTO save(EsuriitsDTO esuriitsDTO) {
        log.debug("Request to save Esuriits : {}", esuriitsDTO);
        Esuriits esuriits = esuriitsMapper.toEntity(esuriitsDTO);
        esuriits = esuriitsRepository.save(esuriits);
        return esuriitsMapper.toDto(esuriits);
    }

    /**
     * Get all the esuriits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EsuriitsDTO> findAll() {
        log.debug("Request to get all Esuriits");
        return esuriitsRepository.findAll().stream()
            .map(esuriitsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one esuriits by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EsuriitsDTO> findOne(Long id) {
        log.debug("Request to get Esuriits : {}", id);
        return esuriitsRepository.findById(id)
            .map(esuriitsMapper::toDto);
    }

    /**
     * Delete the esuriits by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Esuriits : {}", id);
        esuriitsRepository.deleteById(id);
    }
}
