package com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.person;

import com.meli.mutant.finder.domain.model.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonDataRepositoryAdapterTest {

    PersonDataRepositoryAdapter personDataRepositoryAdapter;

    @Mock
    PersonDataRepository personDataRepository;

    @Mock
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mapper.map(any(), any())).thenReturn(new PersonData());
        when(mapper.mapBuilder(any(), any())).thenReturn(Person.builder());
        personDataRepositoryAdapter = new PersonDataRepositoryAdapter(personDataRepository, mapper);
    }

    @Test
    void save() {
        when(personDataRepository.save(any())).thenReturn(new PersonData());
        assertNotNull(personDataRepositoryAdapter.save(Person.builder().build()));
    }

    @Test
    void countPeopleByIsMutant() {
        when(personDataRepository.countPersonDataByIsMutantIs(true)).thenReturn(2L);
        assertEquals(2L, personDataRepository.countPersonDataByIsMutantIs(true));
    }
}