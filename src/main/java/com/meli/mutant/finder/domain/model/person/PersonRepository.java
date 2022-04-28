package com.meli.mutant.finder.domain.model.person;

public interface PersonRepository {
    Person save(Person person);
    Long countPeopleByIsMutant(boolean isMutant);
}
