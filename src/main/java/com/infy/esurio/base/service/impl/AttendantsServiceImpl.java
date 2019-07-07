package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.AttendantsService;
import com.infy.esurio.base.domain.Attendants;
import com.infy.esurio.base.repository.AttendantsRepository;
import com.infy.esurio.base.service.dto.AttendantsDTO;
import com.infy.esurio.base.service.mapper.AttendantsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Attendants}.
 */
@Service
@Transactional
public class AttendantsServiceImpl implements AttendantsService {

    private final Logger log = LoggerFactory.getLogger(AttendantsServiceImpl.class);

    private final AttendantsRepository attendantsRepository;

    private final AttendantsMapper attendantsMapper;

    public AttendantsServiceImpl(AttendantsRepository attendantsRepository, AttendantsMapper attendantsMapper) {
        this.attendantsRepository = attendantsRepository;
        this.attendantsMapper = attendantsMapper;
    }

    /**
     * Save a attendants.
     *
     * @param attendantsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttendantsDTO save(AttendantsDTO attendantsDTO) {
        log.debug("Request to save Attendants : {}", attendantsDTO);
        Attendants attendants = attendantsMapper.toEntity(attendantsDTO);
        attendants = attendantsRepository.save(attendants);
        return attendantsMapper.toDto(attendants);
    }

    /**
     * Get all the attendants.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttendantsDTO> findAll() {
        log.debug("Request to get all Attendants");
        return attendantsRepository.findAll().stream()
            .map(attendantsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one attendants by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttendantsDTO> findOne(Long id) {
        log.debug("Request to get Attendants : {}", id);
        return attendantsRepository.findById(id)
            .map(attendantsMapper::toDto);
    }

    /**
     * Delete the attendants by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attendants : {}", id);
        attendantsRepository.deleteById(id);
    }
}
