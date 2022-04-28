package com.meli.mutant.finder.domain.usecase;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.model.person.PersonRepository;
import com.meli.mutant.finder.domain.usecase.model.Stat;
import com.meli.mutant.finder.domain.usecase.util.MutantDetectionUtilities;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MutantFinderUseCase {

    private final PersonRepository personRepository;

    public boolean isMutant(List<String> dnaChain) {
        int mutantDnaFound = 0;
        if (!MutantDetectionUtilities.isDnaChainValid(dnaChain)) {
            // Throw exception
        }
        mutantDnaFound += MutantDetectionUtilities.horizontalVerification(dnaChain);
        if (mutantDnaFound > 1) {
            savePerson(dnaChain, true);
            return true;
        }
        mutantDnaFound += MutantDetectionUtilities.verticalVerification(dnaChain, mutantDnaFound);
        if (mutantDnaFound > 1) {
            savePerson(dnaChain, true);
            return true;
        }
        mutantDnaFound += MutantDetectionUtilities.diagonalVerification(dnaChain, mutantDnaFound);
        if (mutantDnaFound > 1) {
            savePerson(dnaChain, true);
            return true;
        }
        savePerson(dnaChain, false);
        return false;
    }

    private Person savePerson(List<String> dnaChain, boolean isMutant) {
        return personRepository.save(
                Person.builder().dna(dnaChain).mutant(isMutant).build()
        );
    }

    public Stat getStats() {
        int mutants = Math.toIntExact(personRepository.countPeopleByIsMutant(true));
        int humans = Math.toIntExact(personRepository.countPeopleByIsMutant(false));
        return Stat.builder()
                .mutants(mutants)
                .humans(humans)
                .ratio(humans > 0 ? (double) mutants/humans : mutants)
                .build();
    }
}
