package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Menus;
import com.infy.esurio.base.repository.MenusRepository;
import com.infy.esurio.base.service.MenusService;
import com.infy.esurio.base.service.dto.MenusDTO;
import com.infy.esurio.base.service.mapper.MenusMapper;
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
 * Integration tests for the {@Link MenusResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class MenusResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MenusRepository menusRepository;

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private MenusService menusService;

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

    private MockMvc restMenusMockMvc;

    private Menus menus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenusResource menusResource = new MenusResource(menusService);
        this.restMenusMockMvc = MockMvcBuilders.standaloneSetup(menusResource)
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
    public static Menus createEntity(EntityManager em) {
        Menus menus = new Menus()
            .identifier(DEFAULT_IDENTIFIER)
            .active(DEFAULT_ACTIVE);
        return menus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Menus createUpdatedEntity(EntityManager em) {
        Menus menus = new Menus()
            .identifier(UPDATED_IDENTIFIER)
            .active(UPDATED_ACTIVE);
        return menus;
    }

    @BeforeEach
    public void initTest() {
        menus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenus() throws Exception {
        int databaseSizeBeforeCreate = menusRepository.findAll().size();

        // Create the Menus
        MenusDTO menusDTO = menusMapper.toDto(menus);
        restMenusMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isCreated());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeCreate + 1);
        Menus testMenus = menusList.get(menusList.size() - 1);
        assertThat(testMenus.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testMenus.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMenusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menusRepository.findAll().size();

        // Create the Menus with an existing ID
        menus.setId(1L);
        MenusDTO menusDTO = menusMapper.toDto(menus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenusMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = menusRepository.findAll().size();
        // set the field null
        menus.setIdentifier(null);

        // Create the Menus, which fails.
        MenusDTO menusDTO = menusMapper.toDto(menus);

        restMenusMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isBadRequest());

        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);

        // Get all the menusList
        restMenusMockMvc.perform(get("/api/menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menus.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);

        // Get the menus
        restMenusMockMvc.perform(get("/api/menus/{id}", menus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menus.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMenus() throws Exception {
        // Get the menus
        restMenusMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);

        int databaseSizeBeforeUpdate = menusRepository.findAll().size();

        // Update the menus
        Menus updatedMenus = menusRepository.findById(menus.getId()).get();
        // Disconnect from session so that the updates on updatedMenus are not directly saved in db
        em.detach(updatedMenus);
        updatedMenus
            .identifier(UPDATED_IDENTIFIER)
            .active(UPDATED_ACTIVE);
        MenusDTO menusDTO = menusMapper.toDto(updatedMenus);

        restMenusMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isOk());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeUpdate);
        Menus testMenus = menusList.get(menusList.size() - 1);
        assertThat(testMenus.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testMenus.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMenus() throws Exception {
        int databaseSizeBeforeUpdate = menusRepository.findAll().size();

        // Create the Menus
        MenusDTO menusDTO = menusMapper.toDto(menus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenusMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);

        int databaseSizeBeforeDelete = menusRepository.findAll().size();

        // Delete the menus
        restMenusMockMvc.perform(delete("/api/menus/{id}", menus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menus.class);
        Menus menus1 = new Menus();
        menus1.setId(1L);
        Menus menus2 = new Menus();
        menus2.setId(menus1.getId());
        assertThat(menus1).isEqualTo(menus2);
        menus2.setId(2L);
        assertThat(menus1).isNotEqualTo(menus2);
        menus1.setId(null);
        assertThat(menus1).isNotEqualTo(menus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenusDTO.class);
        MenusDTO menusDTO1 = new MenusDTO();
        menusDTO1.setId(1L);
        MenusDTO menusDTO2 = new MenusDTO();
        assertThat(menusDTO1).isNotEqualTo(menusDTO2);
        menusDTO2.setId(menusDTO1.getId());
        assertThat(menusDTO1).isEqualTo(menusDTO2);
        menusDTO2.setId(2L);
        assertThat(menusDTO1).isNotEqualTo(menusDTO2);
        menusDTO1.setId(null);
        assertThat(menusDTO1).isNotEqualTo(menusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(menusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(menusMapper.fromId(null)).isNull();
    }
}
