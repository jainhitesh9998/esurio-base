package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Servings;
import com.infy.esurio.base.repository.ServingsRepository;
import com.infy.esurio.base.service.ServingsService;
import com.infy.esurio.base.service.dto.ServingsDTO;
import com.infy.esurio.base.service.mapper.ServingsMapper;
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
 * Integration tests for the {@Link ServingsResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class ServingsResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREPARED = false;
    private static final Boolean UPDATED_PREPARED = true;

    private static final Boolean DEFAULT_SERVED = false;
    private static final Boolean UPDATED_SERVED = true;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private ServingsRepository servingsRepository;

    @Autowired
    private ServingsMapper servingsMapper;

    @Autowired
    private ServingsService servingsService;

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

    private MockMvc restServingsMockMvc;

    private Servings servings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServingsResource servingsResource = new ServingsResource(servingsService);
        this.restServingsMockMvc = MockMvcBuilders.standaloneSetup(servingsResource)
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
    public static Servings createEntity(EntityManager em) {
        Servings servings = new Servings()
            .identifier(DEFAULT_IDENTIFIER)
            .prepared(DEFAULT_PREPARED)
            .served(DEFAULT_SERVED)
            .quantity(DEFAULT_QUANTITY);
        return servings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servings createUpdatedEntity(EntityManager em) {
        Servings servings = new Servings()
            .identifier(UPDATED_IDENTIFIER)
            .prepared(UPDATED_PREPARED)
            .served(UPDATED_SERVED)
            .quantity(UPDATED_QUANTITY);
        return servings;
    }

    @BeforeEach
    public void initTest() {
        servings = createEntity(em);
    }

    @Test
    @Transactional
    public void createServings() throws Exception {
        int databaseSizeBeforeCreate = servingsRepository.findAll().size();

        // Create the Servings
        ServingsDTO servingsDTO = servingsMapper.toDto(servings);
        restServingsMockMvc.perform(post("/api/servings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Servings in the database
        List<Servings> servingsList = servingsRepository.findAll();
        assertThat(servingsList).hasSize(databaseSizeBeforeCreate + 1);
        Servings testServings = servingsList.get(servingsList.size() - 1);
        assertThat(testServings.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testServings.isPrepared()).isEqualTo(DEFAULT_PREPARED);
        assertThat(testServings.isServed()).isEqualTo(DEFAULT_SERVED);
        assertThat(testServings.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createServingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servingsRepository.findAll().size();

        // Create the Servings with an existing ID
        servings.setId(1L);
        ServingsDTO servingsDTO = servingsMapper.toDto(servings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServingsMockMvc.perform(post("/api/servings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servings in the database
        List<Servings> servingsList = servingsRepository.findAll();
        assertThat(servingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = servingsRepository.findAll().size();
        // set the field null
        servings.setIdentifier(null);

        // Create the Servings, which fails.
        ServingsDTO servingsDTO = servingsMapper.toDto(servings);

        restServingsMockMvc.perform(post("/api/servings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servingsDTO)))
            .andExpect(status().isBadRequest());

        List<Servings> servingsList = servingsRepository.findAll();
        assertThat(servingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServings() throws Exception {
        // Initialize the database
        servingsRepository.saveAndFlush(servings);

        // Get all the servingsList
        restServingsMockMvc.perform(get("/api/servings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servings.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].prepared").value(hasItem(DEFAULT_PREPARED.booleanValue())))
            .andExpect(jsonPath("$.[*].served").value(hasItem(DEFAULT_SERVED.booleanValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getServings() throws Exception {
        // Initialize the database
        servingsRepository.saveAndFlush(servings);

        // Get the servings
        restServingsMockMvc.perform(get("/api/servings/{id}", servings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servings.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.prepared").value(DEFAULT_PREPARED.booleanValue()))
            .andExpect(jsonPath("$.served").value(DEFAULT_SERVED.booleanValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingServings() throws Exception {
        // Get the servings
        restServingsMockMvc.perform(get("/api/servings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServings() throws Exception {
        // Initialize the database
        servingsRepository.saveAndFlush(servings);

        int databaseSizeBeforeUpdate = servingsRepository.findAll().size();

        // Update the servings
        Servings updatedServings = servingsRepository.findById(servings.getId()).get();
        // Disconnect from session so that the updates on updatedServings are not directly saved in db
        em.detach(updatedServings);
        updatedServings
            .identifier(UPDATED_IDENTIFIER)
            .prepared(UPDATED_PREPARED)
            .served(UPDATED_SERVED)
            .quantity(UPDATED_QUANTITY);
        ServingsDTO servingsDTO = servingsMapper.toDto(updatedServings);

        restServingsMockMvc.perform(put("/api/servings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servingsDTO)))
            .andExpect(status().isOk());

        // Validate the Servings in the database
        List<Servings> servingsList = servingsRepository.findAll();
        assertThat(servingsList).hasSize(databaseSizeBeforeUpdate);
        Servings testServings = servingsList.get(servingsList.size() - 1);
        assertThat(testServings.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testServings.isPrepared()).isEqualTo(UPDATED_PREPARED);
        assertThat(testServings.isServed()).isEqualTo(UPDATED_SERVED);
        assertThat(testServings.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingServings() throws Exception {
        int databaseSizeBeforeUpdate = servingsRepository.findAll().size();

        // Create the Servings
        ServingsDTO servingsDTO = servingsMapper.toDto(servings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServingsMockMvc.perform(put("/api/servings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servings in the database
        List<Servings> servingsList = servingsRepository.findAll();
        assertThat(servingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServings() throws Exception {
        // Initialize the database
        servingsRepository.saveAndFlush(servings);

        int databaseSizeBeforeDelete = servingsRepository.findAll().size();

        // Delete the servings
        restServingsMockMvc.perform(delete("/api/servings/{id}", servings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servings> servingsList = servingsRepository.findAll();
        assertThat(servingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servings.class);
        Servings servings1 = new Servings();
        servings1.setId(1L);
        Servings servings2 = new Servings();
        servings2.setId(servings1.getId());
        assertThat(servings1).isEqualTo(servings2);
        servings2.setId(2L);
        assertThat(servings1).isNotEqualTo(servings2);
        servings1.setId(null);
        assertThat(servings1).isNotEqualTo(servings2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServingsDTO.class);
        ServingsDTO servingsDTO1 = new ServingsDTO();
        servingsDTO1.setId(1L);
        ServingsDTO servingsDTO2 = new ServingsDTO();
        assertThat(servingsDTO1).isNotEqualTo(servingsDTO2);
        servingsDTO2.setId(servingsDTO1.getId());
        assertThat(servingsDTO1).isEqualTo(servingsDTO2);
        servingsDTO2.setId(2L);
        assertThat(servingsDTO1).isNotEqualTo(servingsDTO2);
        servingsDTO1.setId(null);
        assertThat(servingsDTO1).isNotEqualTo(servingsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(servingsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(servingsMapper.fromId(null)).isNull();
    }
}
