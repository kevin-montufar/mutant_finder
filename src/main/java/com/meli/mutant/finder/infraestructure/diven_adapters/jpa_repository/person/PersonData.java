package com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.person;

import com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.helpers.ListToStringConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "PERSON")
public class PersonData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column
    @Convert(converter = ListToStringConverter.class)
    private List<String> dna;

    @Column
    private boolean isMutant;
}
