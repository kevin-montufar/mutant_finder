package com.meli.mutant.finder.configuration.use_case_config;

import com.meli.mutant.finder.domain.model.person.PersonRepository;
import com.meli.mutant.finder.domain.usecase.MutantFinderUseCase;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public MutantFinderUseCase createMutantDetectorUseCase(PersonRepository personRepository) {
        return new MutantFinderUseCase(personRepository);
    }

    @Bean
    public ObjectMapperImp createObjectMapper() {
        return new ObjectMapperImp();
    }
}
