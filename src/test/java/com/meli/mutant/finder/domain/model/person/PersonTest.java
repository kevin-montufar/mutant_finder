package com.meli.mutant.finder.domain.model.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private static Person person;

    @BeforeEach
    void setUp() {
        person = Person.builder().dna(Arrays.asList("CCCC", "AAAA", "ACGT", "ACGT")).mutant(true).build();
    }

    @Test
    void getDnaTest() {
        assertEquals("AAAA", person.getDna().get(1));
    }

    @Test
    void setDnaTest() {
        person.setDna(Arrays.asList("CCCC", "GGGG", "ACGT", "ACGT"));
        assertEquals("GGGG", person.getDna().get(1));
    }

    @Test
    void isMutantTest() {
        assertTrue(person.isMutant());
    }

    @Test
    void setMutantTest() {
        person.setMutant(false);
        assertFalse(person.isMutant());
    }
}