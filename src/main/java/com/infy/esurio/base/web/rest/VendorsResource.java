package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.VendorsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.VendorsDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Vendors}.
 */
@RestController
@RequestMapping("/api")
public class VendorsResource {

    private final Logger log = LoggerFactory.getLogger(VendorsResource.class);

    private static final String ENTITY_NAME = "vendors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendorsService vendorsService;

    public VendorsResource(VendorsService vendorsService) {
        this.vendorsService = vendorsService;
    }

    /**
     * {@code POST  /vendors} : Create a new vendors.
     *
     * @param vendorsDTO the vendorsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendorsDTO, or with status {@code 400 (Bad Request)} if the vendors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendors")
    public ResponseEntity<VendorsDTO> createVendors(@Valid @RequestBody VendorsDTO vendorsDTO) throws URISyntaxException {
        log.debug("REST request to save Vendors : {}", vendorsDTO);
        if (vendorsDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendorsDTO result = vendorsService.save(vendorsDTO);
        return ResponseEntity.created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendors} : Updates an existing vendors.
     *
     * @param vendorsDTO the vendorsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendorsDTO,
     * or with status {@code 400 (Bad Request)} if the vendorsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendorsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendors")
    public ResponseEntity<VendorsDTO> updateVendors(@Valid @RequestBody VendorsDTO vendorsDTO) throws URISyntaxException {
        log.debug("REST request to update Vendors : {}", vendorsDTO);
        if (vendorsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendorsDTO result = vendorsService.save(vendorsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vendorsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vendors} : get all the vendors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendors in body.
     */
    @GetMapping("/vendors")
    public List<VendorsDTO> getAllVendors() {
        log.debug("REST request to get all Vendors");
        return vendorsService.findAll();
    }

    /**
     * {@code GET  /vendors/:id} : get the "id" vendors.
     *
     * @param id the id of the vendorsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendorsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendors/{id}")
    public ResponseEntity<VendorsDTO> getVendors(@PathVariable Long id) {
        log.debug("REST request to get Vendors : {}", id);
        Optional<VendorsDTO> vendorsDTO = vendorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendorsDTO);
    }

    /**
     * {@code DELETE  /vendors/:id} : delete the "id" vendors.
     *
     * @param id the id of the vendorsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendors/{id}")
    public ResponseEntity<Void> deleteVendors(@PathVariable Long id) {
        log.debug("REST request to delete Vendors : {}", id);
        vendorsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
