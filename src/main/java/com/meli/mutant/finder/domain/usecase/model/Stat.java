package com.meli.mutant.finder.domain.usecase.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Stat {

    @JsonProperty("count_mutant_dna")
    private int mutants;
    @JsonProperty("count_human_dna")
    private int humans;
    private double ratio;

}
