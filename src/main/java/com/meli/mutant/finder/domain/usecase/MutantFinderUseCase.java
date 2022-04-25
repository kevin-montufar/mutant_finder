package com.meli.mutant.finder.domain.usecase;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.model.person.PersonRepository;
import com.meli.mutant.finder.domain.usecase.util.MutantDetectionUtilities;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MutantFinderUseCase {
    private final PersonRepository personRepository;

    public boolean isMutant(Person person) {
        int mutantDnaFound = 0;
        if (!MutantDetectionUtilities.isDnaChainValid(person.getDnaChain())) {
            // Throw exception
        }
        mutantDnaFound += MutantDetectionUtilities.horizontalVerification(person.getDnaChain());
        if (mutantDnaFound > 1) {
            person.setMutant(true);
            personRepository.save(person);
            return true;
        }
        mutantDnaFound += MutantDetectionUtilities.verticalVerification(person.getDnaChain(), mutantDnaFound);
        if (mutantDnaFound > 1) {
            person.setMutant(true);
            personRepository.save(person);
            return true;
        }
        mutantDnaFound += (person.getDnaChain().size() > 4) ?
                MutantDetectionUtilities.checkLeftToRightDiagonals(person.getDnaChain(), mutantDnaFound) :
                MutantDetectionUtilities.checkPrincipalDiagonals(person.getDnaChain(), mutantDnaFound);
        if (mutantDnaFound > 1) {
            person.setMutant(true);
            personRepository.save(person);
            return true;
        }
        mutantDnaFound += MutantDetectionUtilities.checkLeftToRightDiagonals(person.getDnaChain(), mutantDnaFound);
        if (mutantDnaFound > 1) {
            person.setMutant(true);
            personRepository.save(person);
            return true;
        }
        personRepository.save(person);
        return false;
    }

}
