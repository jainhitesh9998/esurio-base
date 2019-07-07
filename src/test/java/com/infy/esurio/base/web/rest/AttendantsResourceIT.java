package com.infy.esurio.base.web.rest;

import com.infy.esurio.base.EsurioApp;
import com.infy.esurio.base.domain.Attendants;
import com.infy.esurio.base.repository.AttendantsRepository;
import com.infy.esurio.base.service.AttendantsService;
import com.infy.esurio.base.service.dto.AttendantsDTO;
import com.infy.esurio.base.service.mapper.AttendantsMapper;
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
 * Integration tests for the {@Link AttendantsResource} REST controller.
 */
@SpringBootTest(classes = EsurioApp.class)
public class AttendantsResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private AttendantsRepository attendantsRepository;

    @Autowired
    private AttendantsMapper attendantsMapper;

    @Autowired
    private AttendantsService attendantsService;

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

    private MockMvc restAttendantsMockMvc;

    private Attendants attendants;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendantsResource attendantsResource = new AttendantsResource(attendantsService);
        this.restAttendantsMockMvc = MockMvcBuilders.standaloneSetup(attendantsResource)
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
    public static Attendants createEntity(EntityManager em) {
        Attendants attendants = new Attendants()
            .identifier(DEFAULT_IDENTIFIER);
        return attendants;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendants createUpdatedEntity(EntityManager em) {
        Attendants attendants = new Attendants()
            .identifier(UPDATED_IDENTIFIER);
        return attendants;
    }

    @BeforeEach
    public void initTest() {
        attendants = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendants() throws Exception {
        int databaseSizeBeforeCreate = attendantsRepository.findAll().size();

        // Create the Attendants
        AttendantsDTO attendantsDTO = attendantsMapper.toDto(attendants);
        restAttendantsMockMvc.perform(post("/api/attendants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendantsDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendants in the database
        List<Attendants> attendantsList = attendantsRepository.findAll();
        assertThat(attendantsList).hasSize(databaseSizeBeforeCreate + 1);
        Attendants testAttendants = attendantsList.get(attendantsList.size() - 1);
        assertThat(testAttendants.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createAttendantsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendantsRepository.findAll().size();

        // Create the Attendants with an existing ID
        attendants.setId(1L);
        AttendantsDTO attendantsDTO = attendantsMapper.toDto(attendants);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendantsMockMvc.perform(post("/api/attendants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendantsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendants in the database
        List<Attendants> attendantsList = attendantsRepository.findAll();
        assertThat(attendantsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendantsRepository.findAll().size();
        // set the field null
        attendants.setIdentifier(null);

        // Create the Attendants, which fails.
        AttendantsDTO attendantsDTO = attendantsMapper.toDto(attendants);

        restAttendantsMockMvc.perform(post("/api/attendants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendantsDTO)))
            .andExpect(status().isBadRequest());

        List<Attendants> attendantsList = attendantsRepository.findAll();
        assertThat(attendantsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttendants() throws Exception {
        // Initialize the database
        attendantsRepository.saveAndFlush(attendants);

        // Get all the attendantsList
        restAttendantsMockMvc.perform(get("/api/attendants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendants.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @Test
    @Transactional
    public void getAttendants() throws Exception {
        // Initialize the database
        attendantsRepository.saveAndFlush(attendants);

        // Get the attendants
        restAttendantsMockMvc.perform(get("/api/attendants/{id}", attendants.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendants.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttendants() throws Exception {
        // Get the attendants
        restAttendantsMockMvc.perform(get("/api/attendants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendants() throws Exception {
        // Initialize the database
        attendantsRepository.saveAndFlush(attendants);

        int databaseSizeBeforeUpdate = attendantsRepository.findAll().size();

        // Update the attendants
        Attendants updatedAttendants = attendantsRepository.findById(attendants.getId()).get();
        // Disconnect from session so that the updates on updatedAttendants are not directly saved in db
        em.detach(updatedAttendants);
        updatedAttendants
            .identifier(UPDATED_IDENTIFIER);
        AttendantsDTO attendantsDTO = attendantsMapper.toDto(updatedAttendants);

        restAttendantsMockMvc.perform(put("/api/attendants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendantsDTO)))
            .andExpect(status().isOk());

        // Validate the Attendants in the database
        List<Attendants> attendantsList = attendantsRepository.findAll();
        assertThat(attendantsList).hasSize(databaseSizeBeforeUpdate);
        Attendants testAttendants = attendantsList.get(attendantsList.size() - 1);
        assertThat(testAttendants.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendants() throws Exception {
        int databaseSizeBeforeUpdate = attendantsRepository.findAll().size();

        // Create the Attendants
        AttendantsDTO attendantsDTO = attendantsMapper.toDto(attendants);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendantsMockMvc.perform(put("/api/attendants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendantsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendants in the database
        List<Attendants> attendantsList = attendantsRepository.findAll();
        assertThat(attendantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendants() throws Exception {
        // Initialize the database
        attendantsRepository.saveAndFlush(attendants);

        int databaseSizeBeforeDelete = attendantsRepository.findAll().size();

        // Delete the attendants
        restAttendantsMockMvc.perform(delete("/api/attendants/{id}", attendants.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendants> attendantsList = attendantsRepository.findAll();
        assertThat(attendantsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendants.class);
        Attendants attendants1 = new Attendants();
        attendants1.setId(1L);
        Attendants attendants2 = new Attendants();
        attendants2.setId(attendants1.getId());
        assertThat(attendants1).isEqualTo(attendants2);
        attendants2.setId(2L);
        assertThat(attendants1).isNotEqualTo(attendants2);
        attendants1.setId(null);
        assertThat(attendants1).isNotEqualTo(attendants2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendantsDTO.class);
        AttendantsDTO attendantsDTO1 = new AttendantsDTO();
        attendantsDTO1.setId(1L);
        AttendantsDTO attendantsDTO2 = new AttendantsDTO();
        assertThat(attendantsDTO1).isNotEqualTo(attendantsDTO2);
        attendantsDTO2.setId(attendantsDTO1.getId());
        assertThat(attendantsDTO1).isEqualTo(attendantsDTO2);
        attendantsDTO2.setId(2L);
        assertThat(attendantsDTO1).isNotEqualTo(attendantsDTO2);
        attendantsDTO1.setId(null);
        assertThat(attendantsDTO1).isNotEqualTo(attendantsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attendantsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attendantsMapper.fromId(null)).isNull();
    }
}
