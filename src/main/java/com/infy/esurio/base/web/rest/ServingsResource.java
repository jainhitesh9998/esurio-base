package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.ServingsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.ServingsDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Servings}.
 */
@RestController
@RequestMapping("/api")
public class ServingsResource {

    private final Logger log = LoggerFactory.getLogger(ServingsResource.class);

    private static final String ENTITY_NAME = "servings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServingsService servingsService;

    public ServingsResource(ServingsService servingsService) {
        this.servingsService = servingsService;
    }

    /**
     * {@code POST  /servings} : Create a new servings.
     *
     * @param servingsDTO the servingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servingsDTO, or with status {@code 400 (Bad Request)} if the servings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servings")
    public ResponseEntity<ServingsDTO> createServings(@Valid @RequestBody ServingsDTO servingsDTO) throws URISyntaxException {
        log.debug("REST request to save Servings : {}", servingsDTO);
        if (servingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new servings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServingsDTO result = servingsService.save(servingsDTO);
        return ResponseEntity.created(new URI("/api/servings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servings} : Updates an existing servings.
     *
     * @param servingsDTO the servingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servingsDTO,
     * or with status {@code 400 (Bad Request)} if the servingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servings")
    public ResponseEntity<ServingsDTO> updateServings(@Valid @RequestBody ServingsDTO servingsDTO) throws URISyntaxException {
        log.debug("REST request to update Servings : {}", servingsDTO);
        if (servingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServingsDTO result = servingsService.save(servingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /servings} : get all the servings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servings in body.
     */
    @GetMapping("/servings")
    public List<ServingsDTO> getAllServings() {
        log.debug("REST request to get all Servings");
        return servingsService.findAll();
    }

    /**
     * {@code GET  /servings/:id} : get the "id" servings.
     *
     * @param id the id of the servingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servings/{id}")
    public ResponseEntity<ServingsDTO> getServings(@PathVariable Long id) {
        log.debug("REST request to get Servings : {}", id);
        Optional<ServingsDTO> servingsDTO = servingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servingsDTO);
    }

    /**
     * {@code DELETE  /servings/:id} : delete the "id" servings.
     *
     * @param id the id of the servingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servings/{id}")
    public ResponseEntity<Void> deleteServings(@PathVariable Long id) {
        log.debug("REST request to delete Servings : {}", id);
        servingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
