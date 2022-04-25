package com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.person;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.model.person.PersonRepository;
import com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.helpers.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDataRepositoryAdapter extends AdapterOperations<Person, PersonData, Long, PersonDataRepository>
        implements PersonRepository {


    public PersonDataRepositoryAdapter(PersonDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Person.PersonBuilder.class).build());
    }

    @Override
    public Person save(Person person) {

        return person;
    }
}
