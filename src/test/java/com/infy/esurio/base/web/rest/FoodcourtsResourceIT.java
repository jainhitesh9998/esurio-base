package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Foodcourts;
import com.infy.esurio.base.repository.FoodcourtsRepository;
import com.infy.esurio.base.service.FoodcourtsService;
import com.infy.esurio.base.service.dto.FoodcourtsDTO;
import com.infy.esurio.base.service.mapper.FoodcourtsMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.infy.esurio.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FoodcourtsResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class FoodcourtsResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private FoodcourtsRepository foodcourtsRepository;

    @Autowired
    private FoodcourtsMapper foodcourtsMapper;

    @Autowired
    private FoodcourtsService foodcourtsService;

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

    private MockMvc restFoodcourtsMockMvc;

    private Foodcourts foodcourts;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodcourtsResource foodcourtsResource = new FoodcourtsResource(foodcourtsService);
        this.restFoodcourtsMockMvc = MockMvcBuilders.standaloneSetup(foodcourtsResource)
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
    public static Foodcourts createEntity(EntityManager em) {
        Foodcourts foodcourts = new Foodcourts()
            .identifier(DEFAULT_IDENTIFIER)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return foodcourts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foodcourts createUpdatedEntity(EntityManager em) {
        Foodcourts foodcourts = new Foodcourts()
            .identifier(UPDATED_IDENTIFIER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return foodcourts;
    }

    @BeforeEach
    public void initTest() {
        foodcourts = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodcourts() throws Exception {
        int databaseSizeBeforeCreate = foodcourtsRepository.findAll().size();

        // Create the Foodcourts
        FoodcourtsDTO foodcourtsDTO = foodcourtsMapper.toDto(foodcourts);
        restFoodcourtsMockMvc.perform(post("/api/foodcourts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodcourtsDTO)))
            .andExpect(status().isCreated());

        // Validate the Foodcourts in the database
        List<Foodcourts> foodcourtsList = foodcourtsRepository.findAll();
        assertThat(foodcourtsList).hasSize(databaseSizeBeforeCreate + 1);
        Foodcourts testFoodcourts = foodcourtsList.get(foodcourtsList.size() - 1);
        assertThat(testFoodcourts.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testFoodcourts.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testFoodcourts.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFoodcourtsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodcourtsRepository.findAll().size();

        // Create the Foodcourts with an existing ID
        foodcourts.setId(1L);
        FoodcourtsDTO foodcourtsDTO = foodcourtsMapper.toDto(foodcourts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodcourtsMockMvc.perform(post("/api/foodcourts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodcourtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Foodcourts in the database
        List<Foodcourts> foodcourtsList = foodcourtsRepository.findAll();
        assertThat(foodcourtsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodcourtsRepository.findAll().size();
        // set the field null
        foodcourts.setIdentifier(null);

        // Create the Foodcourts, which fails.
        FoodcourtsDTO foodcourtsDTO = foodcourtsMapper.toDto(foodcourts);

        restFoodcourtsMockMvc.perform(post("/api/foodcourts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodcourtsDTO)))
            .andExpect(status().isBadRequest());

        List<Foodcourts> foodcourtsList = foodcourtsRepository.findAll();
        assertThat(foodcourtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodcourts() throws Exception {
        // Initialize the database
        foodcourtsRepository.saveAndFlush(foodcourts);

        // Get all the foodcourtsList
        restFoodcourtsMockMvc.perform(get("/api/foodcourts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodcourts.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getFoodcourts() throws Exception {
        // Initialize the database
        foodcourtsRepository.saveAndFlush(foodcourts);

        // Get the foodcourts
        restFoodcourtsMockMvc.perform(get("/api/foodcourts/{id}", foodcourts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodcourts.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingFoodcourts() throws Exception {
        // Get the foodcourts
        restFoodcourtsMockMvc.perform(get("/api/foodcourts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodcourts() throws Exception {
        // Initialize the database
        foodcourtsRepository.saveAndFlush(foodcourts);

        int databaseSizeBeforeUpdate = foodcourtsRepository.findAll().size();

        // Update the foodcourts
        Foodcourts updatedFoodcourts = foodcourtsRepository.findById(foodcourts.getId()).get();
        // Disconnect from session so that the updates on updatedFoodcourts are not directly saved in db
        em.detach(updatedFoodcourts);
        updatedFoodcourts
            .identifier(UPDATED_IDENTIFIER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        FoodcourtsDTO foodcourtsDTO = foodcourtsMapper.toDto(updatedFoodcourts);

        restFoodcourtsMockMvc.perform(put("/api/foodcourts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodcourtsDTO)))
            .andExpect(status().isOk());

        // Validate the Foodcourts in the database
        List<Foodcourts> foodcourtsList = foodcourtsRepository.findAll();
        assertThat(foodcourtsList).hasSize(databaseSizeBeforeUpdate);
        Foodcourts testFoodcourts = foodcourtsList.get(foodcourtsList.size() - 1);
        assertThat(testFoodcourts.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testFoodcourts.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFoodcourts.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodcourts() throws Exception {
        int databaseSizeBeforeUpdate = foodcourtsRepository.findAll().size();

        // Create the Foodcourts
        FoodcourtsDTO foodcourtsDTO = foodcourtsMapper.toDto(foodcourts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodcourtsMockMvc.perform(put("/api/foodcourts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodcourtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Foodcourts in the database
        List<Foodcourts> foodcourtsList = foodcourtsRepository.findAll();
        assertThat(foodcourtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodcourts() throws Exception {
        // Initialize the database
        foodcourtsRepository.saveAndFlush(foodcourts);

        int databaseSizeBeforeDelete = foodcourtsRepository.findAll().size();

        // Delete the foodcourts
        restFoodcourtsMockMvc.perform(delete("/api/foodcourts/{id}", foodcourts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Foodcourts> foodcourtsList = foodcourtsRepository.findAll();
        assertThat(foodcourtsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foodcourts.class);
        Foodcourts foodcourts1 = new Foodcourts();
        foodcourts1.setId(1L);
        Foodcourts foodcourts2 = new Foodcourts();
        foodcourts2.setId(foodcourts1.getId());
        assertThat(foodcourts1).isEqualTo(foodcourts2);
        foodcourts2.setId(2L);
        assertThat(foodcourts1).isNotEqualTo(foodcourts2);
        foodcourts1.setId(null);
        assertThat(foodcourts1).isNotEqualTo(foodcourts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodcourtsDTO.class);
        FoodcourtsDTO foodcourtsDTO1 = new FoodcourtsDTO();
        foodcourtsDTO1.setId(1L);
        FoodcourtsDTO foodcourtsDTO2 = new FoodcourtsDTO();
        assertThat(foodcourtsDTO1).isNotEqualTo(foodcourtsDTO2);
        foodcourtsDTO2.setId(foodcourtsDTO1.getId());
        assertThat(foodcourtsDTO1).isEqualTo(foodcourtsDTO2);
        foodcourtsDTO2.setId(2L);
        assertThat(foodcourtsDTO1).isNotEqualTo(foodcourtsDTO2);
        foodcourtsDTO1.setId(null);
        assertThat(foodcourtsDTO1).isNotEqualTo(foodcourtsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foodcourtsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foodcourtsMapper.fromId(null)).isNull();
    }
}
