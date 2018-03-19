package com.gederin.java9.coin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Milling Project Coin
 * <p>
 * 1. Removal underscore as an identifier Name
 * 2. Effectively final in try-with-resources
 * 3. Private methods in the interface
 * 4. SafeVarargs to support private methods
 * 5. Diamond operators with anonymous classes
 */
public class Java9ProjectCoin {
    public static void main(String[] args) {
        //int _ = 10; //As of Java 9, '_' is keyword, and may not be used is identifier


    }

    @SafeVarargs // SafeVarargs to Support Private Methods
    private static void tryWithResources(String... strings) throws FileNotFoundException {
        //Java 7, 8

        InputStream inputStream = new FileInputStream("test.txt");

        try (InputStream stream = inputStream) {
        } catch (IOException e) {
        }

        //Java 9 - allows to use effectively final
        try (inputStream) {
        } catch (IOException e) {
        }

        /**
         * Java 9 enhanced the type inference algorithm to tell whether the inferred type is denotable when analyzing an anonymous class that supports the diamond operator.
         */
        Iterator<String> iter = new Iterator<>() {
            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public String next() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }
}


/**
 * Private Methods in the Interface
 */
interface DisplayService {
    void setUp();

    private static void printInterfaceName() {
        System.out.println("DisplayService");
    }

    static void displayInterfaceName() {
        printInterfaceName();
    }
}