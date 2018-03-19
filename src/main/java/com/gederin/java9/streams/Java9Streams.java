package com.gederin.java9.streams;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream::ofNullable
 * Stream::iterate
 * Stream::takeWhile
 * Stream::dropWhile
 */
public class Java9Streams {

    public static void main(String[] args) {
        ofNullable();
        iterate();
        takeWhile();
        dropWhile();
    }

    /**
     * Stream::ofNullable creates a stream of either zero or one element,
     * depending on whether the parameter passed to the method was null or not.
     */
    private static void ofNullable() {
        long one = Stream.ofNullable("Ruslan").count();
        long zero = Stream.ofNullable(null).count();

        System.out.println(one + ", " + zero);
    }

    /**
     * The new overloaded version of iterate takes a Predicate as the second argument
     * The values are produced by starting with the seed and then applying the unary operator as long as the values satisfy the hasNext predicate.
     */
    private static void iterate() {
        // Java 8 iterate()
        List<BigDecimal> bigDecimals =
                Stream.iterate(BigDecimal.ZERO, bd -> bd.add(BigDecimal.ONE))
                        .limit(10)
                        .collect(Collectors.toList());

        System.out.println(bigDecimals);

        //Java 9 iterate()
        bigDecimals = Stream.iterate(BigDecimal.ZERO, bd -> bd.longValue() < 10L, bd -> bd.add(BigDecimal.ONE))
                .collect(Collectors.toList());

        System.out.println(bigDecimals);

        //Could be used as traditional for loop
        Stream.iterate(1, i -> i <= 10, i -> 2 * i)
                .forEach(System.out::println);
    }

    /**
     * Called on an ordered stream it will return a new one that consists
     * of those element that passed the predicate until the first one failed.
     * <p>
     * It’s a little like filter but it cuts the stream off as soon as the first element fails the predicate.
     */
    private static void takeWhile() {
        List<String> strings = Stream.of("this is a list of strings".split(" "))
                .takeWhile(s -> !s.equals("of"))
                .collect(Collectors.toList());

        System.out.println(strings);//this is a list
    }

    /**
     * Takes a predicate and removes elements from the stream’s beginning
     * until the predicate fails for the first time – from then on,
     * the stream remains the same and no more elements are tested against the predicate
     */
    private static void dropWhile() {
        List<String> strings = Stream.of("this is a list of strings".split(" "))
                .dropWhile(s -> !s.equals("of"))
                .collect(Collectors.toList());

        System.out.println(strings);//of list
    }
}
