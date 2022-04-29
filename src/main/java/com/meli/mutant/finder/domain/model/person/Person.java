package com.meli.mutant.finder.domain.model.person;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
public class Person {
    private List<String> dna;
    private boolean mutant;
}
