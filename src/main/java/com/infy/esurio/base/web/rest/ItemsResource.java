package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.ItemsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.ItemsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.esurio.base.domain.Items}.
 */
@RestController
@RequestMapping("/api")
public class ItemsResource {

    private final Logger log = LoggerFactory.getLogger(ItemsResource.class);

    private static final String ENTITY_NAME = "items";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemsService itemsService;

    public ItemsResource(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    /**
     * {@code POST  /items} : Create a new items.
     *
     * @param itemsDTO the itemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemsDTO, or with status {@code 400 (Bad Request)} if the items has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/items")
    public ResponseEntity<ItemsDTO> createItems(@Valid @RequestBody ItemsDTO itemsDTO) throws URISyntaxException {
        log.debug("REST request to save Items : {}", itemsDTO);
        if (itemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new items cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemsDTO result = itemsService.save(itemsDTO);
        return ResponseEntity.created(new URI("/api/items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /items} : Updates an existing items.
     *
     * @param itemsDTO the itemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemsDTO,
     * or with status {@code 400 (Bad Request)} if the itemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/items")
    public ResponseEntity<ItemsDTO> updateItems(@Valid @RequestBody ItemsDTO itemsDTO) throws URISyntaxException {
        log.debug("REST request to update Items : {}", itemsDTO);
        if (itemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemsDTO result = itemsService.save(itemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /items} : get all the items.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of items in body.
     */
    @GetMapping("/items")
    public List<ItemsDTO> getAllItems() {
        log.debug("REST request to get all Items");
        return itemsService.findAll();
    }

    /**
     * {@code GET  /items/:id} : get the "id" items.
     *
     * @param id the id of the itemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/items/{id}")
    public ResponseEntity<ItemsDTO> getItems(@PathVariable Long id) {
        log.debug("REST request to get Items : {}", id);
        Optional<ItemsDTO> itemsDTO = itemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemsDTO);
    }

    /**
     * {@code DELETE  /items/:id} : delete the "id" items.
     *
     * @param id the id of the itemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItems(@PathVariable Long id) {
        log.debug("REST request to delete Items : {}", id);
        itemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
