package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.VendorsService;
import com.infy.esurio.base.domain.Vendors;
import com.infy.esurio.base.repository.VendorsRepository;
import com.infy.esurio.base.service.dto.VendorsDTO;
import com.infy.esurio.base.service.mapper.VendorsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Vendors}.
 */
@Service
@Transactional
public class VendorsServiceImpl implements VendorsService {

    private final Logger log = LoggerFactory.getLogger(VendorsServiceImpl.class);

    private final VendorsRepository vendorsRepository;

    private final VendorsMapper vendorsMapper;

    public VendorsServiceImpl(VendorsRepository vendorsRepository, VendorsMapper vendorsMapper) {
        this.vendorsRepository = vendorsRepository;
        this.vendorsMapper = vendorsMapper;
    }

    /**
     * Save a vendors.
     *
     * @param vendorsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VendorsDTO save(VendorsDTO vendorsDTO) {
        log.debug("Request to save Vendors : {}", vendorsDTO);
        Vendors vendors = vendorsMapper.toEntity(vendorsDTO);
        vendors = vendorsRepository.save(vendors);
        return vendorsMapper.toDto(vendors);
    }

    /**
     * Get all the vendors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VendorsDTO> findAll() {
        log.debug("Request to get all Vendors");
        return vendorsRepository.findAll().stream()
            .map(vendorsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vendors by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VendorsDTO> findOne(Long id) {
        log.debug("Request to get Vendors : {}", id);
        return vendorsRepository.findById(id)
            .map(vendorsMapper::toDto);
    }

    /**
     * Delete the vendors by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vendors : {}", id);
        vendorsRepository.deleteById(id);
    }
}
