package com.meli.mutant.finder.domain.usecase.util;

import com.codepoetics.protonpack.StreamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MutantDetectionUtilities {

    private static final String REGEX_DNA_VALIDATION = ".*(AAAA|aaaa|CCCC|cccc|GGGG|gggg|TTTT|tttt).*";

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
        if (areDnaChainCharactersAccepted(dnaChain)) {
            return false;
        }
        return true;
    }

    public static boolean isDnaChainSquare(List<String> dnaChain) {
        return dnaChain.stream().noneMatch(dna -> dna.length() != dnaChain.size());
    }

    public static boolean areDnaChainCharactersAccepted(List<String> dnaChain) {
        return dnaChain.parallelStream().anyMatch(dna -> !dna.matches("^[AaCcGgTt]+$"));
    }


    public static int horizontalVerification(List<String> dnaChain) {
        return (int) dnaChain.parallelStream()
                .filter(dna -> dna.matches(REGEX_DNA_VALIDATION))
                .count();
    }

    public static int verticalVerification(List<String> dnaChain, int mutantDnaCounter) {
        mutantDnaCounter += horizontalVerification(transposeDnaChain(dnaChain));
        return mutantDnaCounter;
    }

    public static List<String> transposeDnaChain(List<String> dnaChain) {
        List<String> transposedDnaChain = new ArrayList<>();
        for (int i = 0; i < dnaChain.size(); i++) {
            StringBuilder newDna = new StringBuilder();
            for (int j = 0; j < dnaChain.get(i).length(); j++) {
                newDna.append(dnaChain.get(j).charAt(i));
            }
            transposedDnaChain.add(newDna.toString());
        }
        return transposedDnaChain;
    }

    public static int checkPrincipalDiagonals(List<String> dnaChain, int mutantDnaCounter) {
        List<String> diagonalDnaChain = new ArrayList<>();
        AtomicReference<String> firstDiagonal = new AtomicReference<>("");
        AtomicReference<String> inverseDiagonal = new AtomicReference<>("");
        StreamUtils.zipWithIndex(dnaChain.stream()).forEach(
                dna -> {
                    int row = (int) dna.getIndex();
                    firstDiagonal.updateAndGet(v -> v + dna.getValue().charAt(row));
                    inverseDiagonal.updateAndGet(v -> v + dna.getValue().charAt((dna.getValue().length() - 1) - row));
                }
        );
        diagonalDnaChain.add(firstDiagonal.get());
        diagonalDnaChain.add(inverseDiagonal.get());
        mutantDnaCounter += horizontalVerification(diagonalDnaChain);
        return mutantDnaCounter;
    }

    public static int checkLeftToRightDiagonals(List<String> dnaChain, int mutantDnaCounter) {
        List<String> dnaChainValidation = new ArrayList<>();
        for (int row = 0; row < dnaChain.size() / 2 && mutantDnaCounter < 2; row++) {
            String diagonalDnaChainUp = "";
            String diagonalDnaChainBottom = "";
            for (int col = 0; col < dnaChain.size() - row; col++) {
                if (row > 0)
                    diagonalDnaChainBottom += dnaChain.get(row + col).charAt(col);
                diagonalDnaChainUp += dnaChain.get(col).charAt(row + col);
            }
            if (!diagonalDnaChainUp.equals(""))
                dnaChainValidation.add(diagonalDnaChainUp);
            if (!diagonalDnaChainBottom.equals(""))
                dnaChainValidation.add(diagonalDnaChainBottom);
            mutantDnaCounter += horizontalVerification(dnaChainValidation);
        }
        return mutantDnaCounter;
    }

    public static int checkRightToLeftDiagonals(List<String> dnaChain, int mutantDnaCounter) {
        List<String> dnaChainValidation = new ArrayList<>();
        for (int row = 0; row < dnaChain.size() / 2 && mutantDnaCounter < 2; row++) {
            String diagonalDnaChainUp = "";
            String diagonalDnaChainBottom = "";
            for (int col = dnaChain.size() - 1; col >= row; col--) {
                if (row > 0)
                    diagonalDnaChainBottom += dnaChain.get(col - row).charAt((dnaChain.size() - 1) - col);
                diagonalDnaChainUp += dnaChain.get(col).charAt((row + dnaChain.size() - 1) - col);
            }
            if (!diagonalDnaChainUp.equals(""))
                dnaChainValidation.add(diagonalDnaChainUp);
            if (!diagonalDnaChainBottom.equals(""))
                dnaChainValidation.add(diagonalDnaChainBottom);
            mutantDnaCounter += horizontalVerification(dnaChainValidation);
        }
        return mutantDnaCounter;
    }

}
