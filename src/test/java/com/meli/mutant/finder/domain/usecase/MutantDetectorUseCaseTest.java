package com.meli.mutant.finder.domain.usecase;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.model.person.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class MutantDetectorUseCaseTest {
    List<String> testDnaChain = Arrays.asList(
            "AAAACG",
            "AAACGT",
            "AAAATA",
            "ACGTAA",
            "CGTAAA",
            "GTAAAA"
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
    void isMutant() {
        mutantDetectorUseCase.isMutant(Person.builder().dnaChain(testDnaChain).build());
    }

}