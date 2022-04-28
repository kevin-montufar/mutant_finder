package com.meli.mutant.finder.infraestructure.entry_points.apirest;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.usecase.MutantFinderUseCase;
import com.meli.mutant.finder.domain.usecase.model.Stat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MutantService {

    private final MutantFinderUseCase mutantFinderUseCase;

    @GetMapping("/stats")
    public ResponseEntity<Stat> getStats() {
        return new ResponseEntity<>(mutantFinderUseCase.getStats(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/mutant")
    public ResponseEntity<String> isMutant(@RequestBody Person person) {
        return new ResponseEntity<>(
                new HttpHeaders(),
                mutantFinderUseCase.isMutant(person.getDna()) ? HttpStatus.OK : HttpStatus.FORBIDDEN
        );
    }
}
