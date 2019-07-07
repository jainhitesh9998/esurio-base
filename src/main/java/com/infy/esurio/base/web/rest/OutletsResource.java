package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.OutletsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.OutletsDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Outlets}.
 */
@RestController
@RequestMapping("/api")
public class OutletsResource {

    private final Logger log = LoggerFactory.getLogger(OutletsResource.class);

    private static final String ENTITY_NAME = "outlets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutletsService outletsService;

    public OutletsResource(OutletsService outletsService) {
        this.outletsService = outletsService;
    }

    /**
     * {@code POST  /outlets} : Create a new outlets.
     *
     * @param outletsDTO the outletsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outletsDTO, or with status {@code 400 (Bad Request)} if the outlets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outlets")
    public ResponseEntity<OutletsDTO> createOutlets(@Valid @RequestBody OutletsDTO outletsDTO) throws URISyntaxException {
        log.debug("REST request to save Outlets : {}", outletsDTO);
        if (outletsDTO.getId() != null) {
            throw new BadRequestAlertException("A new outlets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OutletsDTO result = outletsService.save(outletsDTO);
        return ResponseEntity.created(new URI("/api/outlets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /outlets} : Updates an existing outlets.
     *
     * @param outletsDTO the outletsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outletsDTO,
     * or with status {@code 400 (Bad Request)} if the outletsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outletsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outlets")
    public ResponseEntity<OutletsDTO> updateOutlets(@Valid @RequestBody OutletsDTO outletsDTO) throws URISyntaxException {
        log.debug("REST request to update Outlets : {}", outletsDTO);
        if (outletsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OutletsDTO result = outletsService.save(outletsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, outletsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /outlets} : get all the outlets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outlets in body.
     */
    @GetMapping("/outlets")
    public List<OutletsDTO> getAllOutlets() {
        log.debug("REST request to get all Outlets");
        return outletsService.findAll();
    }

    /**
     * {@code GET  /outlets/:id} : get the "id" outlets.
     *
     * @param id the id of the outletsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outletsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outlets/{id}")
    public ResponseEntity<OutletsDTO> getOutlets(@PathVariable Long id) {
        log.debug("REST request to get Outlets : {}", id);
        Optional<OutletsDTO> outletsDTO = outletsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outletsDTO);
    }

    /**
     * {@code DELETE  /outlets/:id} : delete the "id" outlets.
     *
     * @param id the id of the outletsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outlets/{id}")
    public ResponseEntity<Void> deleteOutlets(@PathVariable Long id) {
        log.debug("REST request to delete Outlets : {}", id);
        outletsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
