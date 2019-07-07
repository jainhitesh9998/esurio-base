package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Centers;
import com.infy.esurio.base.repository.CentersRepository;
import com.infy.esurio.base.service.CentersService;
import com.infy.esurio.base.service.dto.CentersDTO;
import com.infy.esurio.base.service.mapper.CentersMapper;
import com.infy.esurio.base.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.infy.esurio.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CentersResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class CentersResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private CentersRepository centersRepository;

    @Autowired
    private CentersMapper centersMapper;

    @Autowired
    private CentersService centersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCentersMockMvc;

    private Centers centers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentersResource centersResource = new CentersResource(centersService);
        this.restCentersMockMvc = MockMvcBuilders.standaloneSetup(centersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centers createEntity(EntityManager em) {
        Centers centers = new Centers()
            .identifier(DEFAULT_IDENTIFIER);
        return centers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centers createUpdatedEntity(EntityManager em) {
        Centers centers = new Centers()
            .identifier(UPDATED_IDENTIFIER);
        return centers;
    }

    @BeforeEach
    public void initTest() {
        centers = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenters() throws Exception {
        int databaseSizeBeforeCreate = centersRepository.findAll().size();

        // Create the Centers
        CentersDTO centersDTO = centersMapper.toDto(centers);
        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isCreated());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeCreate + 1);
        Centers testCenters = centersList.get(centersList.size() - 1);
        assertThat(testCenters.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createCentersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centersRepository.findAll().size();

        // Create the Centers with an existing ID
        centers.setId(1L);
        CentersDTO centersDTO = centersMapper.toDto(centers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = centersRepository.findAll().size();
        // set the field null
        centers.setIdentifier(null);

        // Create the Centers, which fails.
        CentersDTO centersDTO = centersMapper.toDto(centers);

        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        // Get all the centersList
        restCentersMockMvc.perform(get("/api/centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centers.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        // Get the centers
        restCentersMockMvc.perform(get("/api/centers/{id}", centers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centers.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCenters() throws Exception {
        // Get the centers
        restCentersMockMvc.perform(get("/api/centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        int databaseSizeBeforeUpdate = centersRepository.findAll().size();

        // Update the centers
        Centers updatedCenters = centersRepository.findById(centers.getId()).get();
        // Disconnect from session so that the updates on updatedCenters are not directly saved in db
        em.detach(updatedCenters);
        updatedCenters
            .identifier(UPDATED_IDENTIFIER);
        CentersDTO centersDTO = centersMapper.toDto(updatedCenters);

        restCentersMockMvc.perform(put("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isOk());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeUpdate);
        Centers testCenters = centersList.get(centersList.size() - 1);
        assertThat(testCenters.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingCenters() throws Exception {
        int databaseSizeBeforeUpdate = centersRepository.findAll().size();

        // Create the Centers
        CentersDTO centersDTO = centersMapper.toDto(centers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentersMockMvc.perform(put("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        int databaseSizeBeforeDelete = centersRepository.findAll().size();

        // Delete the centers
        restCentersMockMvc.perform(delete("/api/centers/{id}", centers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Centers.class);
        Centers centers1 = new Centers();
        centers1.setId(1L);
        Centers centers2 = new Centers();
        centers2.setId(centers1.getId());
        assertThat(centers1).isEqualTo(centers2);
        centers2.setId(2L);
        assertThat(centers1).isNotEqualTo(centers2);
        centers1.setId(null);
        assertThat(centers1).isNotEqualTo(centers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentersDTO.class);
        CentersDTO centersDTO1 = new CentersDTO();
        centersDTO1.setId(1L);
        CentersDTO centersDTO2 = new CentersDTO();
        assertThat(centersDTO1).isNotEqualTo(centersDTO2);
        centersDTO2.setId(centersDTO1.getId());
        assertThat(centersDTO1).isEqualTo(centersDTO2);
        centersDTO2.setId(2L);
        assertThat(centersDTO1).isNotEqualTo(centersDTO2);
        centersDTO1.setId(null);
        assertThat(centersDTO1).isNotEqualTo(centersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centersMapper.fromId(null)).isNull();
    }
}
