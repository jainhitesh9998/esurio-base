package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.AttendantsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.AttendantsDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Attendants}.
 */
@RestController
@RequestMapping("/api")
public class AttendantsResource {

    private final Logger log = LoggerFactory.getLogger(AttendantsResource.class);

    private static final String ENTITY_NAME = "attendants";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendantsService attendantsService;

    public AttendantsResource(AttendantsService attendantsService) {
        this.attendantsService = attendantsService;
    }

    /**
     * {@code POST  /attendants} : Create a new attendants.
     *
     * @param attendantsDTO the attendantsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendantsDTO, or with status {@code 400 (Bad Request)} if the attendants has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendants")
    public ResponseEntity<AttendantsDTO> createAttendants(@Valid @RequestBody AttendantsDTO attendantsDTO) throws URISyntaxException {
        log.debug("REST request to save Attendants : {}", attendantsDTO);
        if (attendantsDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendants cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendantsDTO result = attendantsService.save(attendantsDTO);
        return ResponseEntity.created(new URI("/api/attendants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendants} : Updates an existing attendants.
     *
     * @param attendantsDTO the attendantsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendantsDTO,
     * or with status {@code 400 (Bad Request)} if the attendantsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendantsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendants")
    public ResponseEntity<AttendantsDTO> updateAttendants(@Valid @RequestBody AttendantsDTO attendantsDTO) throws URISyntaxException {
        log.debug("REST request to update Attendants : {}", attendantsDTO);
        if (attendantsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendantsDTO result = attendantsService.save(attendantsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendantsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendants} : get all the attendants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendants in body.
     */
    @GetMapping("/attendants")
    public List<AttendantsDTO> getAllAttendants() {
        log.debug("REST request to get all Attendants");
        return attendantsService.findAll();
    }

    /**
     * {@code GET  /attendants/:id} : get the "id" attendants.
     *
     * @param id the id of the attendantsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendantsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendants/{id}")
    public ResponseEntity<AttendantsDTO> getAttendants(@PathVariable Long id) {
        log.debug("REST request to get Attendants : {}", id);
        Optional<AttendantsDTO> attendantsDTO = attendantsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendantsDTO);
    }

    /**
     * {@code DELETE  /attendants/:id} : delete the "id" attendants.
     *
     * @param id the id of the attendantsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendants/{id}")
    public ResponseEntity<Void> deleteAttendants(@PathVariable Long id) {
        log.debug("REST request to delete Attendants : {}", id);
        attendantsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
