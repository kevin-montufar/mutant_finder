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

    MutantFinderUseCase mutantFinderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mutantFinderUseCase = new MutantFinderUseCase(personRepository);
    }

    @Test
    void isNotMutant() {
        assertFalse(mutantFinderUseCase.isMutant(humanDnaChain));
    }

    @Test
    void isMutantByHorizontalValidation() {
        assertTrue(mutantFinderUseCase.isMutant(horizontalValidMutantDnaChain));
    }

    @Test
    void isMutantByVerticalValidation() {
        assertTrue(mutantFinderUseCase.isMutant(verticalValidMutantDnaChain));
    }

    @Test
    void isMutantByDiagonalValidation() {
        assertTrue(mutantFinderUseCase.isMutant(diagonalValidMutantDnaChain));
    }

    @Test
    void getStatsTest() {
        Mockito.when(personRepository.countPeopleByIsMutant(true)).thenReturn(Long.valueOf(4));
        Mockito.when(personRepository.countPeopleByIsMutant(false)).thenReturn(Long.valueOf(2));
        assertEquals(2, mutantFinderUseCase.getStats().getRatio());
    }
}