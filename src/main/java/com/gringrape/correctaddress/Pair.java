package com.gringrape.correctaddress;

public record Pair<A, B>(A first, B second) {

    public static <T, R> Pair of(T a, R b) {
        return new Pair<>(a, b);
    }
}
