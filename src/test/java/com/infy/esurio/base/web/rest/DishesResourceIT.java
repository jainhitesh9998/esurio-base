package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Dishes;
import com.infy.esurio.base.repository.DishesRepository;
import com.infy.esurio.base.service.DishesService;
import com.infy.esurio.base.service.dto.DishesDTO;
import com.infy.esurio.base.service.mapper.DishesMapper;
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
 * Integration tests for the {@Link DishesResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class DishesResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TAKEAWAY = false;
    private static final Boolean UPDATED_TAKEAWAY = true;

    private static final Integer DEFAULT_SERVINGS = 1;
    private static final Integer UPDATED_SERVINGS = 2;

    @Autowired
    private DishesRepository dishesRepository;

    @Autowired
    private DishesMapper dishesMapper;

    @Autowired
    private DishesService dishesService;

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

    private MockMvc restDishesMockMvc;

    private Dishes dishes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DishesResource dishesResource = new DishesResource(dishesService);
        this.restDishesMockMvc = MockMvcBuilders.standaloneSetup(dishesResource)
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
    public static Dishes createEntity(EntityManager em) {
        Dishes dishes = new Dishes()
            .identifier(DEFAULT_IDENTIFIER)
            .takeaway(DEFAULT_TAKEAWAY)
            .servings(DEFAULT_SERVINGS);
        return dishes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dishes createUpdatedEntity(EntityManager em) {
        Dishes dishes = new Dishes()
            .identifier(UPDATED_IDENTIFIER)
            .takeaway(UPDATED_TAKEAWAY)
            .servings(UPDATED_SERVINGS);
        return dishes;
    }

    @BeforeEach
    public void initTest() {
        dishes = createEntity(em);
    }

    @Test
    @Transactional
    public void createDishes() throws Exception {
        int databaseSizeBeforeCreate = dishesRepository.findAll().size();

        // Create the Dishes
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);
        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isCreated());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeCreate + 1);
        Dishes testDishes = dishesList.get(dishesList.size() - 1);
        assertThat(testDishes.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testDishes.isTakeaway()).isEqualTo(DEFAULT_TAKEAWAY);
        assertThat(testDishes.getServings()).isEqualTo(DEFAULT_SERVINGS);
    }

    @Test
    @Transactional
    public void createDishesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dishesRepository.findAll().size();

        // Create the Dishes with an existing ID
        dishes.setId(1L);
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = dishesRepository.findAll().size();
        // set the field null
        dishes.setIdentifier(null);

        // Create the Dishes, which fails.
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        // Get all the dishesList
        restDishesMockMvc.perform(get("/api/dishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dishes.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].takeaway").value(hasItem(DEFAULT_TAKEAWAY.booleanValue())))
            .andExpect(jsonPath("$.[*].servings").value(hasItem(DEFAULT_SERVINGS)));
    }
    
    @Test
    @Transactional
    public void getDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        // Get the dishes
        restDishesMockMvc.perform(get("/api/dishes/{id}", dishes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dishes.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.takeaway").value(DEFAULT_TAKEAWAY.booleanValue()))
            .andExpect(jsonPath("$.servings").value(DEFAULT_SERVINGS));
    }

    @Test
    @Transactional
    public void getNonExistingDishes() throws Exception {
        // Get the dishes
        restDishesMockMvc.perform(get("/api/dishes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        int databaseSizeBeforeUpdate = dishesRepository.findAll().size();

        // Update the dishes
        Dishes updatedDishes = dishesRepository.findById(dishes.getId()).get();
        // Disconnect from session so that the updates on updatedDishes are not directly saved in db
        em.detach(updatedDishes);
        updatedDishes
            .identifier(UPDATED_IDENTIFIER)
            .takeaway(UPDATED_TAKEAWAY)
            .servings(UPDATED_SERVINGS);
        DishesDTO dishesDTO = dishesMapper.toDto(updatedDishes);

        restDishesMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isOk());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeUpdate);
        Dishes testDishes = dishesList.get(dishesList.size() - 1);
        assertThat(testDishes.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testDishes.isTakeaway()).isEqualTo(UPDATED_TAKEAWAY);
        assertThat(testDishes.getServings()).isEqualTo(UPDATED_SERVINGS);
    }

    @Test
    @Transactional
    public void updateNonExistingDishes() throws Exception {
        int databaseSizeBeforeUpdate = dishesRepository.findAll().size();

        // Create the Dishes
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishesMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        int databaseSizeBeforeDelete = dishesRepository.findAll().size();

        // Delete the dishes
        restDishesMockMvc.perform(delete("/api/dishes/{id}", dishes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dishes.class);
        Dishes dishes1 = new Dishes();
        dishes1.setId(1L);
        Dishes dishes2 = new Dishes();
        dishes2.setId(dishes1.getId());
        assertThat(dishes1).isEqualTo(dishes2);
        dishes2.setId(2L);
        assertThat(dishes1).isNotEqualTo(dishes2);
        dishes1.setId(null);
        assertThat(dishes1).isNotEqualTo(dishes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishesDTO.class);
        DishesDTO dishesDTO1 = new DishesDTO();
        dishesDTO1.setId(1L);
        DishesDTO dishesDTO2 = new DishesDTO();
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
        dishesDTO2.setId(dishesDTO1.getId());
        assertThat(dishesDTO1).isEqualTo(dishesDTO2);
        dishesDTO2.setId(2L);
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
        dishesDTO1.setId(null);
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dishesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dishesMapper.fromId(null)).isNull();
    }
}
