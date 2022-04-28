package com.meli.mutant.finder.domain.usecase;

import com.meli.mutant.finder.domain.model.person.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorUseCaseTest {
    private final List<String> testDnaChain = Arrays.asList(
            "AAAACG",
            "AAACGT",
            "AAAATA",
            "ACGTAA",
            "CGTAAA",
            "GTAAAA"
    );

    private final List<String> horizontalValidMutantDnaChain = Arrays.asList(
            "CCCC",
            "AAAA",
            "ACGT",
            "ACGT"
    );

    private final List<String> verticalValidMutantDnaChain = Arrays.asList(
            "CAGT",
            "CAGT",
            "CAGT",
            "CAGT"
    );

    private final List<String> diagonalValidMutantDnaChain = Arrays.asList(
            "CAGT",
            "TCTG",
            "GTCA",
            "TGTC"
    );

    private final List<String> humanDnaChain = Arrays.asList(
            "CAGT",
            "TGAC",
            "CAGT",
            "TGAC"
    );

    @Mock
    PersonRepository personRepository;

    MutantFinderUseCase mutantDetectorUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mutantDetectorUseCase = new MutantFinderUseCase(personRepository);
    }

    @Test
    void isNotMutant() {
        assertFalse(mutantDetectorUseCase.isMutant(humanDnaChain));
    }

    @Test
    void isMutantByHorizontalValidation() {
        assertTrue(mutantDetectorUseCase.isMutant(horizontalValidMutantDnaChain));
    }

    @Test
    void isMutantByVerticalValidation() {
        assertTrue(mutantDetectorUseCase.isMutant(verticalValidMutantDnaChain));
    }

    @Test
    void isMutantByDiagonalValidation() {
        assertTrue(mutantDetectorUseCase.isMutant(diagonalValidMutantDnaChain));
    }

    @Test
    void getStatsTest() {
        Mockito.when(personRepository.countPeopleByIsMutant(true)).thenReturn(Long.valueOf(4));
        Mockito.when(personRepository.countPeopleByIsMutant(false)).thenReturn(Long.valueOf(2));
        assertEquals(2, mutantDetectorUseCase.getStats().getRatio());
    }
}