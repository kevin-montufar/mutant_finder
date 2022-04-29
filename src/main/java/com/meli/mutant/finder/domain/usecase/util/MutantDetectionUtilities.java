package com.meli.mutant.finder.domain.usecase.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class MutantDetectionUtilities {

    private static final String REGEX_DNA_VALIDATION = ".*(AAAA|aaaa|CCCC|cccc|GGGG|gggg|TTTT|tttt).*";

    private MutantDetectionUtilities() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Validates if a DNA chain is different from null, it is an NxN matrix, the characters contained are only ACG or T
     * and the size is greater than 4
     *
     * @param dnaChain List of strings with DNA information
     * @return true if is valid and false if not
     */
    public static boolean isDnaChainValid(List<String> dnaChain) {
        if (dnaChain == null) {
            return false;
        }
        if (dnaChain.isEmpty()) {
            return false;
        }
        if (dnaChain.size() < 4) {
            return false;
        }
        if (!isDnaChainSquare(dnaChain)) {
            return false;
        }
        return !areDnaChainCharactersAccepted(dnaChain);
    }

    /**
     * Validates if a DNA chain is square
     *
     * @param dnaChain List of strings with DNA information
     * @return true if is valid and false if not
     */
    private static boolean isDnaChainSquare(List<String> dnaChain) {
        return dnaChain.stream().noneMatch(dna -> dna.length() != dnaChain.size());
    }

    /**
     * Validates if the characters in the DNA chain are only A,C,G or T
     *
     * @param dnaChain List of strings with DNA information
     * @return true if is valid and false if not
     */
    private static boolean areDnaChainCharactersAccepted(List<String> dnaChain) {
        return dnaChain.parallelStream().anyMatch(dna -> !dna.matches("^[AaCcGgTt]+$"));
    }

    /**
     * Perform horizontal verification looking for DNA chains with 4 equal valid characters
     *
     * @param dnaChain List of strings with DNA information
     * @return number of matches
     */
    public static int horizontalVerification(List<String> dnaChain) {
        return (int) dnaChain.parallelStream()
                .filter(MutantDetectionUtilities::isDnaMatchesMutant)
                .count();
    }

    /**
     * Matches AAAA, CCCC, GGGG or TTTT
     *
     * @param dna string with DNA information
     * @return true if match and false if not
     */
    private static boolean isDnaMatchesMutant(String dna) {
        return dna.matches(REGEX_DNA_VALIDATION);
    }

    /**
     * Perform vertical verification looking for DNA chains with 4 equal valid characters
     *
     * @param dnaChain         List of strings with DNA information
     * @param mutantDnaCounter Number of matches found before
     * @return Number of matches found
     */
    public static int verticalVerification(List<String> dnaChain, int mutantDnaCounter) {
        AtomicInteger counter = new AtomicInteger(mutantDnaCounter);
        IntStream.range(0, dnaChain.size())
                .forEach(
                        row -> {
                            StringBuilder newDna = new StringBuilder();
                            IntStream.range(0, dnaChain.get(row).length())
                                    .forEach(col -> newDna.append(dnaChain.get(col).charAt(row)));
                            counter.addAndGet(isDnaMatchesMutant(newDna.toString()) ? 1 : 0);
                        }
                );
        return counter.get();
    }

    /**
     * Perform diagonal verification looking for DNA chains with 4 equal valid characters
     *
     * @param dnaChain         List of strings with DNA information
     * @param mutantDnaCounter Number of matches found before
     * @return Number of matches found
     */
    public static int diagonalVerification(List<String> dnaChain, int mutantDnaCounter) {
        if (dnaChain.size() == 4)
            return checkTwoPrincipalDiagonals(dnaChain);
        mutantDnaCounter += checkLeftToRightDiagonals(dnaChain, mutantDnaCounter);
        if (mutantDnaCounter > 1)
            return mutantDnaCounter;
        return checkRightToLeftDiagonals(dnaChain, mutantDnaCounter);
    }

    /**
     * Perform diagonal verification only in two principal diagonals looking for DNA chains with 4 equal valid characters
     *
     * @param dnaChain List of strings with DNA information
     * @return Number of matches found
     */
    private static int checkTwoPrincipalDiagonals(List<String> dnaChain) {
        AtomicReference<String> firstDiagonal = new AtomicReference<>("");
        AtomicReference<String> inverseDiagonal = new AtomicReference<>("");
        IntStream.range(0, dnaChain.size())
                .forEach(
                        row -> {
                            firstDiagonal.updateAndGet(v -> v + dnaChain.get(row).charAt(row));
                            inverseDiagonal.updateAndGet(v -> v + dnaChain.get(row).charAt((dnaChain.get(row).length() - 1) - row));
                        }
                );

        return (isDnaMatchesMutant(firstDiagonal.toString()) ? 1 : 0) + (isDnaMatchesMutant(inverseDiagonal.toString()) ? 1 : 0);
    }

    /**
     * Perform diagonal verification from left to right looking for DNA chains with 4 equal valid characters
     *
     * @param dnaChain         List of strings with DNA information
     * @param mutantDnaCounter Number of matches found before
     * @return Number of matches found
     */
    private static int checkLeftToRightDiagonals(List<String> dnaChain, int mutantDnaCounter) {
        AtomicInteger counter = new AtomicInteger(mutantDnaCounter);
        IntStream.range(0, dnaChain.size() / 2).forEach(
                row -> {
                    StringBuilder diagonalDnaChainUp = new StringBuilder();
                    StringBuilder diagonalDnaChainBottom = new StringBuilder();
                    IntStream.range(0, dnaChain.size() - row)
                            .forEach(col -> {
                                if (row > 0)
                                    diagonalDnaChainBottom.append(dnaChain.get(row + col).charAt(col));
                                diagonalDnaChainUp.append(dnaChain.get(col).charAt(row + col));
                            });
                    int mutantFound = (isDnaMatchesMutant(diagonalDnaChainUp.toString()) ? 1 : 0) +
                            (isDnaMatchesMutant(diagonalDnaChainBottom.toString()) ? 1 : 0);
                    counter.addAndGet(mutantFound);
                }
        );
        return counter.get();
    }

    /**
     * Perform diagonal verification from right to left looking for DNA chains with 4 equal valid characters
     *
     * @param dnaChain         List of strings with DNA information
     * @param mutantDnaCounter Number of matches found before
     * @return Number of matches found
     */
    private static int checkRightToLeftDiagonals(List<String> dnaChain, int mutantDnaCounter) {
        List<String> dnaChainValidation = new ArrayList<>();
        for (int row = 0; row < dnaChain.size() / 2 && mutantDnaCounter < 2; row++) {
            StringBuilder diagonalDnaChainUp = new StringBuilder();
            StringBuilder diagonalDnaChainBottom = new StringBuilder();
            for (int col = dnaChain.size() - 1; col >= row; col--) {
                if (row > 0)
                    diagonalDnaChainBottom.append(dnaChain.get(col - row).charAt((dnaChain.size() - 1) - col));
                diagonalDnaChainUp.append(dnaChain.get(col).charAt((row + dnaChain.size() - 1) - col));
            }
            if (!diagonalDnaChainUp.toString().equals(""))
                dnaChainValidation.add(diagonalDnaChainUp.toString());
            if (!diagonalDnaChainBottom.toString().equals(""))
                dnaChainValidation.add(diagonalDnaChainBottom.toString());
            mutantDnaCounter += horizontalVerification(dnaChainValidation);
        }
        return mutantDnaCounter;
    }

}
