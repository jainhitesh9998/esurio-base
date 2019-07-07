package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.ItemsService;
import com.infy.esurio.base.domain.Items;
import com.infy.esurio.base.repository.ItemsRepository;
import com.infy.esurio.base.service.dto.ItemsDTO;
import com.infy.esurio.base.service.mapper.ItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Items}.
 */
@Service
@Transactional
public class ItemsServiceImpl implements ItemsService {

    private final Logger log = LoggerFactory.getLogger(ItemsServiceImpl.class);

    private final ItemsRepository itemsRepository;

    private final ItemsMapper itemsMapper;

    public ItemsServiceImpl(ItemsRepository itemsRepository, ItemsMapper itemsMapper) {
        this.itemsRepository = itemsRepository;
        this.itemsMapper = itemsMapper;
    }

    /**
     * Save a items.
     *
     * @param itemsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemsDTO save(ItemsDTO itemsDTO) {
        log.debug("Request to save Items : {}", itemsDTO);
        Items items = itemsMapper.toEntity(itemsDTO);
        items = itemsRepository.save(items);
        return itemsMapper.toDto(items);
    }

    /**
     * Get all the items.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemsDTO> findAll() {
        log.debug("Request to get all Items");
        return itemsRepository.findAll().stream()
            .map(itemsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one items by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemsDTO> findOne(Long id) {
        log.debug("Request to get Items : {}", id);
        return itemsRepository.findById(id)
            .map(itemsMapper::toDto);
    }

    /**
     * Delete the items by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Items : {}", id);
        itemsRepository.deleteById(id);
    }
}
