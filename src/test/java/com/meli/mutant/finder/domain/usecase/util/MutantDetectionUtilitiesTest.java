package com.meli.mutant.finder.domain.usecase.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectionUtilitiesTest {

    private final List<String> validMutantDnaChain = Arrays.asList(
            "CCCC",
            "AAAA",
            "ACGT",
            "ACGT"
    );
    List<String> miniDiagonalValidMutantDnaChain = Arrays.asList(
            "CAAC",
            "ACCA",
            "ACCA",
            "CAAC"
    );
    List<String> diagonalValidMutantDnaChain = Arrays.asList(
            "CAGTAG",
            "TCAGGG",
            "ATCGGT",
            "ACGCAG",
            "AGGTCT",
            "GCGTCG"
    );

    List<String> diagonalValidMutantDnaChain2 = Arrays.asList(
            "AAAACG",
            "AAACGT",
            "AAAATA",
            "ACGTAA",
            "CGTAAA",
            "GTAAAA"
    );
    private final List<String> rectangularDnaChain = Arrays.asList(
            "CCCC",
            "CCCC",
            "CCCC",
            "CCC"
    );

    private final List<String> invalidCharactersDnaChain = Arrays.asList(
            "CCCZ",
            "CCCC",
            "CCCC",
            "CCCC"
    );


    @Test
    void isDnaChainValid() {
        assertTrue(MutantDetectionUtilities.isDnaChainValid(validMutantDnaChain));
    }

    @Test
    void isNotDnaChainValidWhenIsNull() {
        assertFalse(MutantDetectionUtilities.isDnaChainValid(null));
    }

    @Test
    void isNotDnaChainValidWhenIsVoid() {
        assertFalse(MutantDetectionUtilities.isDnaChainValid(new ArrayList<>()));
    }

    @Test
    void isNotDnaChainValidWhenIsRectangularMatrix() {
        assertFalse(MutantDetectionUtilities.isDnaChainValid(rectangularDnaChain));
    }

    @Test
    void isNotDnaChainValidWhenContainsInvalidCharacters() {
        assertFalse(MutantDetectionUtilities.isDnaChainValid(invalidCharactersDnaChain));
    }

    @Test
    void horizontalVerification() {
        assertEquals(Long.valueOf(2), MutantDetectionUtilities.horizontalVerification(validMutantDnaChain));
    }

    @Test
    void verticalVerification() {
        assertNotNull(MutantDetectionUtilities.transposeDnaChain(validMutantDnaChain));
    }

    @Test
    void diagonalVerification() {
        assertEquals(2, MutantDetectionUtilities.checkPrincipalDiagonals(miniDiagonalValidMutantDnaChain, 0));
    }

    @Test
    void leftToRightDiagonalsVerificationTest() {
        assertEquals(4, MutantDetectionUtilities.checkLeftToRightDiagonals(diagonalValidMutantDnaChain, 0));
    }

    @Test
    void rightToLeftDiagonalsVerificationTest() {
        assertEquals(4, MutantDetectionUtilities.checkRightToLeftDiagonals(diagonalValidMutantDnaChain2, 0));
    }

    @Test
    void isDnaChainDimensionsValid() {
        assertTrue(MutantDetectionUtilities.isDnaChainSquare(validMutantDnaChain));
    }
}