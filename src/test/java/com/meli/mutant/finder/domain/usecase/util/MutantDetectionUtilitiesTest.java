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
    private final List<String> miniDiagonalValidMutantDnaChain = Arrays.asList(
            "CAAT",
            "ACTA",
            "ATCA",
            "TAAC"
    );

    private final List<String> diagonalValidMutantDnaChain = Arrays.asList(
            "CAGTAG",
            "TCAGGG",
            "ATCGGT",
            "ACGCAG",
            "AGGTCT",
            "GCGTCG"
    );

    private final List<String> diagonalValidMutantDnaChain2 = Arrays.asList(
            "ACGTA",
            "CGTAC",
            "GTAAC",
            "TACCG",
            "ACGTA"
    );
    private final List<String> rectangularDnaChain = Arrays.asList(
            "CCCC",
            "CCCC",
            "CCCC",
            "CCC"
    );

    private final List<String> lessThanFourDnaChain = Arrays.asList(
            "CCCC",
            "CCCC",
            "CCCC"
    );

    private final List<String> invalidCharactersDnaChain = Arrays.asList(
            "CCCZ",
            "CCCC",
            "CCCC",
            "CCCC"
    );


    private final List<String> verticalValidMutantDnaChain = Arrays.asList(
            "GAGT",
            "GAAG",
            "GAGT",
            "GAGT"
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
    void isNotDnaChainValidWhenIsLessThanFourElements() {
        assertFalse(MutantDetectionUtilities.isDnaChainValid(lessThanFourDnaChain));
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
        assertEquals(Long.valueOf(2), MutantDetectionUtilities.verticalVerification(verticalValidMutantDnaChain, 0));
    }

    @Test
    void diagonalVerification() {
        assertEquals(2, MutantDetectionUtilities.diagonalVerification(miniDiagonalValidMutantDnaChain, 0));
    }

    @Test
    void leftToRightDiagonalsVerificationTest() {
        assertEquals(2, MutantDetectionUtilities.diagonalVerification(diagonalValidMutantDnaChain, 0));
    }

    @Test
    void rightToLeftDiagonalsVerificationTest() {
        assertEquals(3, MutantDetectionUtilities.diagonalVerification(diagonalValidMutantDnaChain2, 0));
    }

    @Test
    void isDnaChainDimensionsValid() {
        assertTrue(MutantDetectionUtilities.isDnaChainValid(validMutantDnaChain));
    }
}