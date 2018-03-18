## Streams improvements

Stream interface has 4 new methods:

 * Stream::takeWhile
 * Stream::dropWhile
 * Stream::iterate
 * Stream::ofNullable


**Stream::takeWhile**

```
static<T> Stream<T> takeWhile(Predicate<? super T> predicate);
```

Called on an ordered stream it will return a new one that consists of those element that passed the predicate until the first one failed. It’s a little like filter but it cuts the stream off as soon as the first element fails the predicate. In its parlance, it takes elements from the stream while the predicate holds and stops as soon as it no longer does.

```
List<String> strings = Stream.of("this is a list of strings".split(" "))
                .takeWhile(s -> !s.equals("of"))
                .collect(Collectors.toList());

System.out.println(strings);//this is a list
```

The real advantage to takeWhile is that it is a short-circuiting operation. If you have a huge collection of sorted elements, you can stop evaluating once you hit the condi‐ tion you care about.

**Stream::dropWhile**

```
static<T> Stream<T> dropWhile(Predicate<? super T> predicate);
```

It does just the opposite of takeWhile: Called on an ordered stream it will return a new one that consists of the first element that failed the predicate and all following ones. Or, closer to its name, it drops elements while the predicate holds and returns the rest.

```
List<String> strings = Stream.of("this is a list of strings".split(" "))
                .dropWhile(s -> !s.equals("of"))
                .collect(Collectors.toList());

System.out.println(strings);//of list
```

**Stream::iterate**

```
static<T> Stream<T> iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
```

The new overloaded version of iterate takes a Predicate as the second argument. The values are produced by starting with the seed and then applying the unary operator as long as the values satisfy the hasNext predicate.

```
bigDecimals = Stream.iterate(BigDecimal.ZERO, bd -> bd.longValue() < 10L, bd -> bd.add(BigDecimal.ONE))
                .collect(Collectors.toList());

System.out.println(bigDecimals);

//Could be used as traditional for loop
Stream.iterate(1, i -> i <= 10, i -> 2 * i)
           .forEach(System.out::println);
```

**Stream::ofNullable**

```
static<T> Stream<T> ofNullable(T t)
```

Stream::ofNullable creates a stream of either zero or one element, depending on whether the parameter passed to the method was null or not.

```
 long one = Stream.ofNullable("Ruslan").count();
 long zero = Stream.ofNullable(null).count();
```

This method simplify stream creation from objects which may be null

```

// findCustomer can return null
Customer customer = findCustomer(customerId);

Stream.ofNullable(customer)
	.flatMap(Customer::streamOrders)
	. // do something with stream of orders

```

## Optional improvements

Optional class was extended by three methods

 * Optional::or
 * Optional::stream
 * Optional::ifPresentOrElse

**Optional::or**

```
Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)
```

If a value is present, this method returns an Optional describing it. Otherwise it invokes the Supplier and returns the Optional it returns.

```
Optional<Customer> customer = findByIdLocal(id)
                .or(() -> findByIdRemote(id))
                .or(() -> Optional.of(Customer.DEFAULT));
```

**Optional::stream**

```
Stream<T> stream()
```

If a value is present, this method returns a sequential, single element stream contain‐ ing only that value. Otherwise it returns an empty stream.
This method means that a stream of optional customers can be turned into a stream of customers directly.

```
List<Customer> allCustomersByGivenIds = Arrays.stream(ids)
                .map(Java9Optionals::findById)
                .flatMap(Optional::stream) //using new Optional::stream
                .collect(Collectors.toList());
```

**Optional::ifPresentOrElse**

```
void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
```

The ifPresent method on Optional executes a Consumer if the Optional is not empty.

The new ifPresentOrElse method takes a second, Runnable, argument that is executed if the Optional is empty. To use it, simply provide a lambda that takes no arguments and returns void.

```
findByIdLocal(id).ifPresentOrElse(System.out::println,
                () -> System.out.println("Customer with id=" + id + " not found"));
```
