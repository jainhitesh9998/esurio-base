package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Outlets;
import com.infy.esurio.base.repository.OutletsRepository;
import com.infy.esurio.base.service.OutletsService;
import com.infy.esurio.base.service.dto.OutletsDTO;
import com.infy.esurio.base.service.mapper.OutletsMapper;
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
 * Integration tests for the {@Link OutletsResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class OutletsResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private OutletsRepository outletsRepository;

    @Autowired
    private OutletsMapper outletsMapper;

    @Autowired
    private OutletsService outletsService;

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

    private MockMvc restOutletsMockMvc;

    private Outlets outlets;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OutletsResource outletsResource = new OutletsResource(outletsService);
        this.restOutletsMockMvc = MockMvcBuilders.standaloneSetup(outletsResource)
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
    public static Outlets createEntity(EntityManager em) {
        Outlets outlets = new Outlets()
            .identifier(DEFAULT_IDENTIFIER);
        return outlets;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outlets createUpdatedEntity(EntityManager em) {
        Outlets outlets = new Outlets()
            .identifier(UPDATED_IDENTIFIER);
        return outlets;
    }

    @BeforeEach
    public void initTest() {
        outlets = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutlets() throws Exception {
        int databaseSizeBeforeCreate = outletsRepository.findAll().size();

        // Create the Outlets
        OutletsDTO outletsDTO = outletsMapper.toDto(outlets);
        restOutletsMockMvc.perform(post("/api/outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outletsDTO)))
            .andExpect(status().isCreated());

        // Validate the Outlets in the database
        List<Outlets> outletsList = outletsRepository.findAll();
        assertThat(outletsList).hasSize(databaseSizeBeforeCreate + 1);
        Outlets testOutlets = outletsList.get(outletsList.size() - 1);
        assertThat(testOutlets.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createOutletsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outletsRepository.findAll().size();

        // Create the Outlets with an existing ID
        outlets.setId(1L);
        OutletsDTO outletsDTO = outletsMapper.toDto(outlets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutletsMockMvc.perform(post("/api/outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outletsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Outlets in the database
        List<Outlets> outletsList = outletsRepository.findAll();
        assertThat(outletsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = outletsRepository.findAll().size();
        // set the field null
        outlets.setIdentifier(null);

        // Create the Outlets, which fails.
        OutletsDTO outletsDTO = outletsMapper.toDto(outlets);

        restOutletsMockMvc.perform(post("/api/outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outletsDTO)))
            .andExpect(status().isBadRequest());

        List<Outlets> outletsList = outletsRepository.findAll();
        assertThat(outletsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutlets() throws Exception {
        // Initialize the database
        outletsRepository.saveAndFlush(outlets);

        // Get all the outletsList
        restOutletsMockMvc.perform(get("/api/outlets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outlets.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getOutlets() throws Exception {
        // Initialize the database
        outletsRepository.saveAndFlush(outlets);

        // Get the outlets
        restOutletsMockMvc.perform(get("/api/outlets/{id}", outlets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outlets.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOutlets() throws Exception {
        // Get the outlets
        restOutletsMockMvc.perform(get("/api/outlets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutlets() throws Exception {
        // Initialize the database
        outletsRepository.saveAndFlush(outlets);

        int databaseSizeBeforeUpdate = outletsRepository.findAll().size();

        // Update the outlets
        Outlets updatedOutlets = outletsRepository.findById(outlets.getId()).get();
        // Disconnect from session so that the updates on updatedOutlets are not directly saved in db
        em.detach(updatedOutlets);
        updatedOutlets
            .identifier(UPDATED_IDENTIFIER);
        OutletsDTO outletsDTO = outletsMapper.toDto(updatedOutlets);

        restOutletsMockMvc.perform(put("/api/outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outletsDTO)))
            .andExpect(status().isOk());

        // Validate the Outlets in the database
        List<Outlets> outletsList = outletsRepository.findAll();
        assertThat(outletsList).hasSize(databaseSizeBeforeUpdate);
        Outlets testOutlets = outletsList.get(outletsList.size() - 1);
        assertThat(testOutlets.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingOutlets() throws Exception {
        int databaseSizeBeforeUpdate = outletsRepository.findAll().size();

        // Create the Outlets
        OutletsDTO outletsDTO = outletsMapper.toDto(outlets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutletsMockMvc.perform(put("/api/outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outletsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Outlets in the database
        List<Outlets> outletsList = outletsRepository.findAll();
        assertThat(outletsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutlets() throws Exception {
        // Initialize the database
        outletsRepository.saveAndFlush(outlets);

        int databaseSizeBeforeDelete = outletsRepository.findAll().size();

        // Delete the outlets
        restOutletsMockMvc.perform(delete("/api/outlets/{id}", outlets.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outlets> outletsList = outletsRepository.findAll();
        assertThat(outletsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outlets.class);
        Outlets outlets1 = new Outlets();
        outlets1.setId(1L);
        Outlets outlets2 = new Outlets();
        outlets2.setId(outlets1.getId());
        assertThat(outlets1).isEqualTo(outlets2);
        outlets2.setId(2L);
        assertThat(outlets1).isNotEqualTo(outlets2);
        outlets1.setId(null);
        assertThat(outlets1).isNotEqualTo(outlets2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OutletsDTO.class);
        OutletsDTO outletsDTO1 = new OutletsDTO();
        outletsDTO1.setId(1L);
        OutletsDTO outletsDTO2 = new OutletsDTO();
        assertThat(outletsDTO1).isNotEqualTo(outletsDTO2);
        outletsDTO2.setId(outletsDTO1.getId());
        assertThat(outletsDTO1).isEqualTo(outletsDTO2);
        outletsDTO2.setId(2L);
        assertThat(outletsDTO1).isNotEqualTo(outletsDTO2);
        outletsDTO1.setId(null);
        assertThat(outletsDTO1).isNotEqualTo(outletsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(outletsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(outletsMapper.fromId(null)).isNull();
    }
}
