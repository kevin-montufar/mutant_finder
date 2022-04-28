package com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDataTest {
    private final List<String> dnaChain = Arrays.asList("CCCC", "GGGG", "ACGT", "ACGT");
    private final List<String> secondaryDnaChain = Arrays.asList("AAAA", "AAAA", "AAAA", "AAAA");
    private static PersonData personData;

    @BeforeEach
    void setUp() {
        personData = new PersonData();
        personData.setId(1L);
        personData.setMutant(true);
        personData.setDna(dnaChain);
    }

    @Test
    void getIdTest() {
        assertEquals(1L, personData.getId());
    }

    @Test
    void setIdTest() {
        personData.setId(2L);
        assertEquals(2L, personData.getId());
    }

    @Test
    void getDnaTest() {
        assertEquals(dnaChain, personData.getDna());
    }

    @Test
    void setDnaTest() {
        personData.setDna(secondaryDnaChain);
        assertEquals(secondaryDnaChain, personData.getDna());
    }

    @Test
    void isMutantTest() {
        assertTrue(personData.isMutant());
    }

    @Test
    void setMutantTest() {
        personData.setMutant(false);
        assertFalse(personData.isMutant());
    }
}