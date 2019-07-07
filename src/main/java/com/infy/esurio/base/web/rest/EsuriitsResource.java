package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.EsuriitsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.EsuriitsDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Esuriits}.
 */
@RestController
@RequestMapping("/api")
public class EsuriitsResource {

    private final Logger log = LoggerFactory.getLogger(EsuriitsResource.class);

    private static final String ENTITY_NAME = "esuriits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EsuriitsService esuriitsService;

    public EsuriitsResource(EsuriitsService esuriitsService) {
        this.esuriitsService = esuriitsService;
    }

    /**
     * {@code POST  /esuriits} : Create a new esuriits.
     *
     * @param esuriitsDTO the esuriitsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new esuriitsDTO, or with status {@code 400 (Bad Request)} if the esuriits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/esuriits")
    public ResponseEntity<EsuriitsDTO> createEsuriits(@Valid @RequestBody EsuriitsDTO esuriitsDTO) throws URISyntaxException {
        log.debug("REST request to save Esuriits : {}", esuriitsDTO);
        if (esuriitsDTO.getId() != null) {
            throw new BadRequestAlertException("A new esuriits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EsuriitsDTO result = esuriitsService.save(esuriitsDTO);
        return ResponseEntity.created(new URI("/api/esuriits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /esuriits} : Updates an existing esuriits.
     *
     * @param esuriitsDTO the esuriitsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esuriitsDTO,
     * or with status {@code 400 (Bad Request)} if the esuriitsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the esuriitsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/esuriits")
    public ResponseEntity<EsuriitsDTO> updateEsuriits(@Valid @RequestBody EsuriitsDTO esuriitsDTO) throws URISyntaxException {
        log.debug("REST request to update Esuriits : {}", esuriitsDTO);
        if (esuriitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EsuriitsDTO result = esuriitsService.save(esuriitsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, esuriitsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /esuriits} : get all the esuriits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of esuriits in body.
     */
    @GetMapping("/esuriits")
    public List<EsuriitsDTO> getAllEsuriits() {
        log.debug("REST request to get all Esuriits");
        return esuriitsService.findAll();
    }

    /**
     * {@code GET  /esuriits/:id} : get the "id" esuriits.
     *
     * @param id the id of the esuriitsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the esuriitsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/esuriits/{id}")
    public ResponseEntity<EsuriitsDTO> getEsuriits(@PathVariable Long id) {
        log.debug("REST request to get Esuriits : {}", id);
        Optional<EsuriitsDTO> esuriitsDTO = esuriitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esuriitsDTO);
    }

    /**
     * {@code DELETE  /esuriits/:id} : delete the "id" esuriits.
     *
     * @param id the id of the esuriitsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/esuriits/{id}")
    public ResponseEntity<Void> deleteEsuriits(@PathVariable Long id) {
        log.debug("REST request to delete Esuriits : {}", id);
        esuriitsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
