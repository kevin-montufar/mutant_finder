package com.meli.mutant.finder.infraestructure.diven_adapters.jpa_repository.person;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "PERSON")
public class PersonData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long code;

    @Column
    private String dnaChain;

    @Column
    private boolean isMutant;
}
