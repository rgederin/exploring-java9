package com.gederin.java9.optional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Optional::or
 * Optional::stream
 * Optional::ifPresentOrElse
 */
public class Java9Optionals {
    private static Map<Integer, Customer> customerDb = new HashMap<>();

    public static void main(String[] args) {
        customerDb.put(2, new Customer(2, "Brand"));
        or(1);
        stream();
        ifPresentOrElse(1);
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise performs the given empty-based action.
     */
    private static void ifPresentOrElse(int id) {
        findByIdLocal(id).ifPresentOrElse(System.out::println,
                () -> System.out.println("Customer with id=" + id + " not found"));
    }

    /**
     * If a value is present, this method returns an Optional describing it.
     * Otherwise it invokes the Supplier and returns the Optional it returns.
     */
    private static void or(int id) {
        Optional<Customer> customer = findByIdLocal(id)
                .or(() -> findByIdRemote(id))
                .or(() -> Optional.of(Customer.DEFAULT));

        System.out.println(customer.get());
    }

    /**
     * If a value is present, this method returns a sequential, single element stream containing only that value.
     * Otherwise it returns an empty stream.
     */
    private static void stream() {
        Integer ids[] = {1, 4, 5};

        List<Customer> allCustomersByGivenIds = Arrays.stream(ids)
                .map(Java9Optionals::findById)
                .flatMap(Optional::stream) //using new Optional::stream
                .collect(Collectors.toList());

        System.out.println(allCustomersByGivenIds);
    }

    private static Optional<Customer> findById(int id) {
        return Optional.ofNullable(customerDb.get(id));
    }

    private static Optional<Customer> findByIdLocal(int id) {
        System.out.println("checking local db for needful customer...");
        return Optional.ofNullable(customerDb.get(id));
    }

    private static Optional<Customer> findByIdRemote(int id) {
        System.out.println("checking remote db for needful customer...");
        return Optional.ofNullable(customerDb.get(id));
    }
}


class Customer {
    public static final Customer DEFAULT = new Customer(1, "Bob");
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }
}