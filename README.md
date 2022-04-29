# Mutant Finder Project

## Project scope

Find mutants in a NxN matrix which contains DNA information. 

### Business rules

1. Person is mutant when has more than one sequence of DNA with 4 characters equals.
2. Sequence could be horizontal, vertical and diagonal.
3. A DNA chain is valid if matrix is square, and it contains only A,C,G or T. 

## Technological stack

- Java 11
- Spring boot 2.6.6 RELEASE
- H2 Database
- Junit 5 
- Jacoco

## Run Locally

To run locally you must be sure to have installed next pre-requisites:
- Java Development Kit 11
- Gradle 6.8.X

Then you must run with "gradle bootRun" command.

## API Rest Testing

### Endpoints:
- Method POST "/mutant"
  - body: {“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}
  - response status: HTTP status 200-OK if is mutant, and 400-FORBIDDEN if is human.
- Method GET "/stats"
  - response status: HTTP status 200-OK
  - response body: {“count_mutant_dna”:40, “count_human_dna”:100, “ratio”:0.4}

