package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Esuriits;
import com.infy.esurio.base.repository.EsuriitsRepository;
import com.infy.esurio.base.service.EsuriitsService;
import com.infy.esurio.base.service.dto.EsuriitsDTO;
import com.infy.esurio.base.service.mapper.EsuriitsMapper;
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
 * Integration tests for the {@Link EsuriitsResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class EsuriitsResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private EsuriitsRepository esuriitsRepository;

    @Autowired
    private EsuriitsMapper esuriitsMapper;

    @Autowired
    private EsuriitsService esuriitsService;

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

    private MockMvc restEsuriitsMockMvc;

    private Esuriits esuriits;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EsuriitsResource esuriitsResource = new EsuriitsResource(esuriitsService);
        this.restEsuriitsMockMvc = MockMvcBuilders.standaloneSetup(esuriitsResource)
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
    public static Esuriits createEntity(EntityManager em) {
        Esuriits esuriits = new Esuriits()
            .identifier(DEFAULT_IDENTIFIER);
        return esuriits;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Esuriits createUpdatedEntity(EntityManager em) {
        Esuriits esuriits = new Esuriits()
            .identifier(UPDATED_IDENTIFIER);
        return esuriits;
    }

    @BeforeEach
    public void initTest() {
        esuriits = createEntity(em);
    }

    @Test
    @Transactional
    public void createEsuriits() throws Exception {
        int databaseSizeBeforeCreate = esuriitsRepository.findAll().size();

        // Create the Esuriits
        EsuriitsDTO esuriitsDTO = esuriitsMapper.toDto(esuriits);
        restEsuriitsMockMvc.perform(post("/api/esuriits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esuriitsDTO)))
            .andExpect(status().isCreated());

        // Validate the Esuriits in the database
        List<Esuriits> esuriitsList = esuriitsRepository.findAll();
        assertThat(esuriitsList).hasSize(databaseSizeBeforeCreate + 1);
        Esuriits testEsuriits = esuriitsList.get(esuriitsList.size() - 1);
        assertThat(testEsuriits.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createEsuriitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = esuriitsRepository.findAll().size();

        // Create the Esuriits with an existing ID
        esuriits.setId(1L);
        EsuriitsDTO esuriitsDTO = esuriitsMapper.toDto(esuriits);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsuriitsMockMvc.perform(post("/api/esuriits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esuriitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Esuriits in the database
        List<Esuriits> esuriitsList = esuriitsRepository.findAll();
        assertThat(esuriitsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = esuriitsRepository.findAll().size();
        // set the field null
        esuriits.setIdentifier(null);

        // Create the Esuriits, which fails.
        EsuriitsDTO esuriitsDTO = esuriitsMapper.toDto(esuriits);

        restEsuriitsMockMvc.perform(post("/api/esuriits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esuriitsDTO)))
            .andExpect(status().isBadRequest());

        List<Esuriits> esuriitsList = esuriitsRepository.findAll();
        assertThat(esuriitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEsuriits() throws Exception {
        // Initialize the database
        esuriitsRepository.saveAndFlush(esuriits);

        // Get all the esuriitsList
        restEsuriitsMockMvc.perform(get("/api/esuriits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esuriits.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getEsuriits() throws Exception {
        // Initialize the database
        esuriitsRepository.saveAndFlush(esuriits);

        // Get the esuriits
        restEsuriitsMockMvc.perform(get("/api/esuriits/{id}", esuriits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(esuriits.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEsuriits() throws Exception {
        // Get the esuriits
        restEsuriitsMockMvc.perform(get("/api/esuriits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEsuriits() throws Exception {
        // Initialize the database
        esuriitsRepository.saveAndFlush(esuriits);

        int databaseSizeBeforeUpdate = esuriitsRepository.findAll().size();

        // Update the esuriits
        Esuriits updatedEsuriits = esuriitsRepository.findById(esuriits.getId()).get();
        // Disconnect from session so that the updates on updatedEsuriits are not directly saved in db
        em.detach(updatedEsuriits);
        updatedEsuriits
            .identifier(UPDATED_IDENTIFIER);
        EsuriitsDTO esuriitsDTO = esuriitsMapper.toDto(updatedEsuriits);

        restEsuriitsMockMvc.perform(put("/api/esuriits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esuriitsDTO)))
            .andExpect(status().isOk());

        // Validate the Esuriits in the database
        List<Esuriits> esuriitsList = esuriitsRepository.findAll();
        assertThat(esuriitsList).hasSize(databaseSizeBeforeUpdate);
        Esuriits testEsuriits = esuriitsList.get(esuriitsList.size() - 1);
        assertThat(testEsuriits.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingEsuriits() throws Exception {
        int databaseSizeBeforeUpdate = esuriitsRepository.findAll().size();

        // Create the Esuriits
        EsuriitsDTO esuriitsDTO = esuriitsMapper.toDto(esuriits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsuriitsMockMvc.perform(put("/api/esuriits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esuriitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Esuriits in the database
        List<Esuriits> esuriitsList = esuriitsRepository.findAll();
        assertThat(esuriitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEsuriits() throws Exception {
        // Initialize the database
        esuriitsRepository.saveAndFlush(esuriits);

        int databaseSizeBeforeDelete = esuriitsRepository.findAll().size();

        // Delete the esuriits
        restEsuriitsMockMvc.perform(delete("/api/esuriits/{id}", esuriits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Esuriits> esuriitsList = esuriitsRepository.findAll();
        assertThat(esuriitsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Esuriits.class);
        Esuriits esuriits1 = new Esuriits();
        esuriits1.setId(1L);
        Esuriits esuriits2 = new Esuriits();
        esuriits2.setId(esuriits1.getId());
        assertThat(esuriits1).isEqualTo(esuriits2);
        esuriits2.setId(2L);
        assertThat(esuriits1).isNotEqualTo(esuriits2);
        esuriits1.setId(null);
        assertThat(esuriits1).isNotEqualTo(esuriits2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsuriitsDTO.class);
        EsuriitsDTO esuriitsDTO1 = new EsuriitsDTO();
        esuriitsDTO1.setId(1L);
        EsuriitsDTO esuriitsDTO2 = new EsuriitsDTO();
        assertThat(esuriitsDTO1).isNotEqualTo(esuriitsDTO2);
        esuriitsDTO2.setId(esuriitsDTO1.getId());
        assertThat(esuriitsDTO1).isEqualTo(esuriitsDTO2);
        esuriitsDTO2.setId(2L);
        assertThat(esuriitsDTO1).isNotEqualTo(esuriitsDTO2);
        esuriitsDTO1.setId(null);
        assertThat(esuriitsDTO1).isNotEqualTo(esuriitsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(esuriitsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(esuriitsMapper.fromId(null)).isNull();
    }
}
