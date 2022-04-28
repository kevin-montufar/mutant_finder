package com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface PersonDataRepository extends CrudRepository<PersonData, Long>, QueryByExampleExecutor<PersonData> {

    Long countPersonDataByIsMutantIs(boolean isMutant);
}
