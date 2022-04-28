package com.meli.mutant.finder.domain.model.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MutantFinderException extends RuntimeException  {
    private String code;
    private String message;
}
