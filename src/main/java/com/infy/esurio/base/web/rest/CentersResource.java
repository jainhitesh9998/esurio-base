package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.CentersService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.CentersDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Centers}.
 */
@RestController
@RequestMapping("/api")
public class CentersResource {

    private final Logger log = LoggerFactory.getLogger(CentersResource.class);

    private static final String ENTITY_NAME = "centers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentersService centersService;

    public CentersResource(CentersService centersService) {
        this.centersService = centersService;
    }

    /**
     * {@code POST  /centers} : Create a new centers.
     *
     * @param centersDTO the centersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centersDTO, or with status {@code 400 (Bad Request)} if the centers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centers")
    public ResponseEntity<CentersDTO> createCenters(@Valid @RequestBody CentersDTO centersDTO) throws URISyntaxException {
        log.debug("REST request to save Centers : {}", centersDTO);
        if (centersDTO.getId() != null) {
            throw new BadRequestAlertException("A new centers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentersDTO result = centersService.save(centersDTO);
        return ResponseEntity.created(new URI("/api/centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centers} : Updates an existing centers.
     *
     * @param centersDTO the centersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centersDTO,
     * or with status {@code 400 (Bad Request)} if the centersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centers")
    public ResponseEntity<CentersDTO> updateCenters(@Valid @RequestBody CentersDTO centersDTO) throws URISyntaxException {
        log.debug("REST request to update Centers : {}", centersDTO);
        if (centersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentersDTO result = centersService.save(centersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /centers} : get all the centers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centers in body.
     */
    @GetMapping("/centers")
    public List<CentersDTO> getAllCenters() {
        log.debug("REST request to get all Centers");
        return centersService.findAll();
    }

    /**
     * {@code GET  /centers/:id} : get the "id" centers.
     *
     * @param id the id of the centersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centers/{id}")
    public ResponseEntity<CentersDTO> getCenters(@PathVariable Long id) {
        log.debug("REST request to get Centers : {}", id);
        Optional<CentersDTO> centersDTO = centersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centersDTO);
    }

    /**
     * {@code DELETE  /centers/:id} : delete the "id" centers.
     *
     * @param id the id of the centersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centers/{id}")
    public ResponseEntity<Void> deleteCenters(@PathVariable Long id) {
        log.debug("REST request to delete Centers : {}", id);
        centersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
