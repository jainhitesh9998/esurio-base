package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.FoodcourtsService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.FoodcourtsDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Foodcourts}.
 */
@RestController
@RequestMapping("/api")
public class FoodcourtsResource {

    private final Logger log = LoggerFactory.getLogger(FoodcourtsResource.class);

    private static final String ENTITY_NAME = "foodcourts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodcourtsService foodcourtsService;

    public FoodcourtsResource(FoodcourtsService foodcourtsService) {
        this.foodcourtsService = foodcourtsService;
    }

    /**
     * {@code POST  /foodcourts} : Create a new foodcourts.
     *
     * @param foodcourtsDTO the foodcourtsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodcourtsDTO, or with status {@code 400 (Bad Request)} if the foodcourts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foodcourts")
    public ResponseEntity<FoodcourtsDTO> createFoodcourts(@Valid @RequestBody FoodcourtsDTO foodcourtsDTO) throws URISyntaxException {
        log.debug("REST request to save Foodcourts : {}", foodcourtsDTO);
        if (foodcourtsDTO.getId() != null) {
            throw new BadRequestAlertException("A new foodcourts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodcourtsDTO result = foodcourtsService.save(foodcourtsDTO);
        return ResponseEntity.created(new URI("/api/foodcourts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foodcourts} : Updates an existing foodcourts.
     *
     * @param foodcourtsDTO the foodcourtsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodcourtsDTO,
     * or with status {@code 400 (Bad Request)} if the foodcourtsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodcourtsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foodcourts")
    public ResponseEntity<FoodcourtsDTO> updateFoodcourts(@Valid @RequestBody FoodcourtsDTO foodcourtsDTO) throws URISyntaxException {
        log.debug("REST request to update Foodcourts : {}", foodcourtsDTO);
        if (foodcourtsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodcourtsDTO result = foodcourtsService.save(foodcourtsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foodcourtsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /foodcourts} : get all the foodcourts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodcourts in body.
     */
    @GetMapping("/foodcourts")
    public List<FoodcourtsDTO> getAllFoodcourts() {
        log.debug("REST request to get all Foodcourts");
        return foodcourtsService.findAll();
    }

    /**
     * {@code GET  /foodcourts/:id} : get the "id" foodcourts.
     *
     * @param id the id of the foodcourtsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodcourtsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foodcourts/{id}")
    public ResponseEntity<FoodcourtsDTO> getFoodcourts(@PathVariable Long id) {
        log.debug("REST request to get Foodcourts : {}", id);
        Optional<FoodcourtsDTO> foodcourtsDTO = foodcourtsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodcourtsDTO);
    }

    /**
     * {@code DELETE  /foodcourts/:id} : delete the "id" foodcourts.
     *
     * @param id the id of the foodcourtsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foodcourts/{id}")
    public ResponseEntity<Void> deleteFoodcourts(@PathVariable Long id) {
        log.debug("REST request to delete Foodcourts : {}", id);
        foodcourtsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
