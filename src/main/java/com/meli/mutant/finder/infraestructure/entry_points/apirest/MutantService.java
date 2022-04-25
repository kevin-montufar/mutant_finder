package com.meli.mutant.finder.infraestructure.entry_points.apirest;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.usecase.MutantFinderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutant", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MutantService {

    private final MutantFinderUseCase mutantDetectorUseCase;

    @PostMapping("/save")
    public ResponseEntity<String> saveEmployee(@RequestBody Person person) {
        boolean employeeSaved = mutantDetectorUseCase.isMutant(person);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
