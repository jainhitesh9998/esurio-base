package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.service.MenusService;
import com.infy.esurio.base.web.rest.errors.BadRequestAlertException;
import com.infy.esurio.base.service.dto.MenusDTO;

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
 * REST controller for managing {@link com.infy.esurio.base.domain.Menus}.
 */
@RestController
@RequestMapping("/api")
public class MenusResource {

    private final Logger log = LoggerFactory.getLogger(MenusResource.class);

    private static final String ENTITY_NAME = "menus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenusService menusService;

    public MenusResource(MenusService menusService) {
        this.menusService = menusService;
    }

    /**
     * {@code POST  /menus} : Create a new menus.
     *
     * @param menusDTO the menusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menusDTO, or with status {@code 400 (Bad Request)} if the menus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/menus")
    public ResponseEntity<MenusDTO> createMenus(@Valid @RequestBody MenusDTO menusDTO) throws URISyntaxException {
        log.debug("REST request to save Menus : {}", menusDTO);
        if (menusDTO.getId() != null) {
            throw new BadRequestAlertException("A new menus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenusDTO result = menusService.save(menusDTO);
        return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /menus} : Updates an existing menus.
     *
     * @param menusDTO the menusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menusDTO,
     * or with status {@code 400 (Bad Request)} if the menusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/menus")
    public ResponseEntity<MenusDTO> updateMenus(@Valid @RequestBody MenusDTO menusDTO) throws URISyntaxException {
        log.debug("REST request to update Menus : {}", menusDTO);
        if (menusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MenusDTO result = menusService.save(menusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, menusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /menus} : get all the menus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menus in body.
     */
    @GetMapping("/menus")
    public List<MenusDTO> getAllMenus() {
        log.debug("REST request to get all Menus");
        return menusService.findAll();
    }

    /**
     * {@code GET  /menus/:id} : get the "id" menus.
     *
     * @param id the id of the menusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/menus/{id}")
    public ResponseEntity<MenusDTO> getMenus(@PathVariable Long id) {
        log.debug("REST request to get Menus : {}", id);
        Optional<MenusDTO> menusDTO = menusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(menusDTO);
    }

    /**
     * {@code DELETE  /menus/:id} : delete the "id" menus.
     *
     * @param id the id of the menusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/menus/{id}")
    public ResponseEntity<Void> deleteMenus(@PathVariable Long id) {
        log.debug("REST request to delete Menus : {}", id);
        menusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
