package com.infy.esurio.base.service.impl;

import com.infy.esurio.base.service.TagsService;
import com.infy.esurio.base.domain.Tags;
import com.infy.esurio.base.repository.TagsRepository;
import com.infy.esurio.base.service.dto.TagsDTO;
import com.infy.esurio.base.service.mapper.TagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Tags}.
 */
@Service
@Transactional
public class TagsServiceImpl implements TagsService {

    private final Logger log = LoggerFactory.getLogger(TagsServiceImpl.class);

    private final TagsRepository tagsRepository;

    private final TagsMapper tagsMapper;

    public TagsServiceImpl(TagsRepository tagsRepository, TagsMapper tagsMapper) {
        this.tagsRepository = tagsRepository;
        this.tagsMapper = tagsMapper;
    }

    /**
     * Save a tags.
     *
     * @param tagsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TagsDTO save(TagsDTO tagsDTO) {
        log.debug("Request to save Tags : {}", tagsDTO);
        Tags tags = tagsMapper.toEntity(tagsDTO);
        tags = tagsRepository.save(tags);
        return tagsMapper.toDto(tags);
    }

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TagsDTO> findAll() {
        log.debug("Request to get all Tags");
        return tagsRepository.findAll().stream()
            .map(tagsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tags by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TagsDTO> findOne(Long id) {
        log.debug("Request to get Tags : {}", id);
        return tagsRepository.findById(id)
            .map(tagsMapper::toDto);
    }

    /**
     * Delete the tags by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tags : {}", id);
        tagsRepository.deleteById(id);
    }
}
