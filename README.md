## JShell

JShell is a REPL (Read-Eval-Print-Loop) tool that allows snippets of code to be run without having to place them in classes. It is similar to what exists in other JVM-based languages such as Groovy or Scala. It allows facilitate quick prototyping of new code without having to compile and run and without having to open an IDE.

In addition to the command line tool, JShell comes with an API to allow other tools to integrate JShell's functionality.

Some rules such as ending statements with semi-colons and checked exceptions are relaxed. You can even declare variables of some type that you define after declaring the variable. The class path and module path can also be changed during the session, or when starting JShell the first time


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

## Milling Project Coin

Milling Project Coin has five small amendments to the Java programming language:

 * Removal underscore as an identifier name
 * Effectively final in try-with-resources
 * Private methods in the interface
 * SafeVarargs to support private methods
 * Diamond operators with anonymous classes

**Removal underscore as an identifier name**

In Java 8, an underscore ( _ ) is allowed as an identifier name, but the compiler will show a warning that "It will not be supported after Java SE 8." So Java 9 completed the removal of underscore from the set of legal identifier names.

The underscore will be used as a keyword for an unnamed lambda parameter in future Java releases (JEP 302).That's the reason the compiler stated _ is a keyword while compiling the Java file.

**Effectively final in try-with-resources**

Java 7 introduced the try-with-resources statement, where resources will be closed automatically after the execution. It requires an additional variable for the resources to be assigned. But Java 9 manages the same with the final or effectively final variables. The effectively final variable is the variable or the parameter whose values will never be changed once it is initialized.

```
InputStream inputStream = new FileInputStream("test.txt");
try (inputStream) {} catch (IOException e) {}
```

**Private methods in the interface**

In Java 8, we can use Interface with static and default methods. Meanwhile, Java 9 will introduce Interface implementations with private methods.

The private method can be accessed only within that Interface.

**SafeVarargs to support private methods**

Java 7 introduced the @SafeVarargs annotation to final, static methods, and constructors. Java 9 extends this functionality to use for private methods, too.

**Diamond operators with anonymous classes**

Java 7 introduced the diamond operator ( <> ) in generic class instantiation contexts.

But in Java 7, we cannot use the diamond operator in anonymous classes. Java 9 enhanced the type inference algorithm to tell whether the inferred type is denotable when analyzing an anonymous class that supports the diamond operator.

## Collections improvement

Java 9 has created factory methods for creating immutable Lists, Sets, Maps, and Map.Entry Objects. These utility methods are used to create empty or non-empty collection objects.

**Characteristics of these utility methods**

* These methods are immutable. We cannot add or delete or update the elements in the collection. If we try to add or delete or update the elements, it throws an unsupportedOperationException.
* It doesn't allow null values. If we try to add null values to any collection, then it throws a null pointer exception.
* They are serializable if all the elements are serializable.

**Examples**

List: (with values and an empty List)

```
List<String> list= List.of("apple","bat");
List<String> list= List.of();
```

Set: (with values and an empty Set)

```
Set<String> set= Set.of("apple","bat");
Set<String> set= Set.of();
```

Map: (with values and an empty Map)

```
Map<Integer,String> emptyMap = Map.of()
Map<Integer,String> map = Map.of(1, "Apple", 2, "Bat", 3, "Cat")
```

Map.Entry: (with values and an empty Map Entry)

```
Map<Integer,String> emptyEntry = Map.ofEntries()
Map.Entry<Integer,String> mapEntry1 = Map.entry(1,"Apple")
Map.Entry<Integer,String> mapEntry2 = Map.entry(2,"Bat")
Map.Entry<Integer,String> mapEntry3 = Map.entry(3,"Cat")
Map<Integer,String> mapEntry = Map.ofEntries(mapEntry1,mapEntry2,mapEntry3)
```

## Stack-Walking API

The stack-walking API, released as part of Java 9, offers an efficient way to access the execution stack. (The execution stack represents the chain of method calls – it starts with the *public static void main(String[])* method or the run method of a thread, contains a frame for each method that was called but did not yet return, and ends at the execution point of the StackWalker call.)

[Deep dive into stack-walking API](https://www.sitepoint.com/deep-dive-into-java-9s-stack-walking-api/)

## Process API updates

JEP 102: Process API Updates enhances the java.lang.Process class and introduces the java.lang.ProcessHandle interface with its nested Info interface to overcome limitations that often force developers to resort to native code; for example, to obtain the native process ID (PID).

[Deep dive into process API](https://www.javaworld.com/article/3176874/java-language/java-9s-other-new-enhancements-part-3.html)

## Reactive Stream Flow API

Reactive Streams is a standard for asynchronous stream processing with non-blocking back pressure. This specification is defined in the Reactive Manifesto, and there are various implementations of it, for example, RxJava or Akka-Streams.

**Reactive API Overview**

To build a Flow, we can use three main abstractions and compose them into asynchronous processing logic.

**Every Flow needs to process events that are published to it by a Publisher instance**; the Publisher has one method – subscribe(). If any of the subscribers want to receive events published by it, they need to subscribe to the given Publisher.

**The receiver of messages needs to implement the Subscriber interface.** Typically this is the end for every Flow processing because the instance of it does not send messages further.
We can think about Subscriber as a Sink. This has four methods that need to be overridden – onSubscribe(), onNext(), onError(), and onComplete(). We’ll be looking at those in the next section.

**If we want to transform incoming message and pass it further to the next Subscriber, we need to implement the Processor interface.** This acts both as a Subscriber because it receives messages, and as the Publisher because it processes those messages and sends them for further processing.

[Deep dive into Java 9 Reactive Streams](http://www.baeldung.com/java-9-reactive-streams)

## Changes in Java 9 with HTTP/2.0

The original HTTP-handling API in Java was written back when HTTP/1.1 was a new, shiny thing. It was written to be agnostic, supporting many different types of connections using the same API. Over time, the uses of HTTP have evolved but the Java API has not kept pace with it. So for Java 9, a new API has been introduced that is cleaner and clearer to use and that also adds support for HTTP/2.

The new API handles HTTP connections through three classes.

* HttpClient handles the creation and sending of requests.
* HttpRequest is used to construct a request to be sent via the HttpClient.
* HttpResponse holds the response from the request that has been sent.

The new API helps HTTP connections be more easily maintained. It's faster and allows for a more responsive application without the need for third-party libraries.

```
        try {
            HttpClient httpClient = HttpClient.newHttpClient(); //Create a HttpClient
            System.out.println(httpClient.version());
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI("https://www.google.com/")).GET().build(); //Create a GET request for the given URI
            Map < String, List < String >> headers = httpRequest.headers().map();
            headers.forEach((k, v) - > System.out.println(k + "-" + v));
            HttpResponse < String > httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandler.asString());
        } catch (Exception e) {
            System.out.println("message " + e);
        }
```

Also we could perform in asynchronous way:

```
CompletableFuture > httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandler.asString());
```

## Other Java 9 updates and improvements

* New Doclet API - allowed client applications to customize the output of Javadocs
* Applet API Deprecated
* New Versioning Scheme
* Javadoc Search and HTML5
* G1 as Default Garbage Collector - Prior to Java 9, the default garbage collector was typically the Parallel GC on server VMs and the Serial GC on client ones. On Java 9, server VMs will use G1 as the default, which was introduced in Java 7. G1 is a parallel and low-pause garbage collector that works especially well for multi-core machines with big heap sizes.
* New HotSpot Diagnostic Commands
* Create PKCS12 Keystores by Default  - starting Java 9, keystores are created using the PKCS12 format instead of JKS because it offers stronger cryptographic algorithms.
* Multi-Release JAR Files - allows bundling code targeting multiple Java releases within the same Jar file.
* Remove the JVM TI hprof Agent
* Remove the jhat Tool
* Compile for Older Platform Versions
* Multi-Resolution Images
* Compact Strings - An internal optimization is applied to the String class to reduce memory consumption. The idea is that most String objects contain characters that do not need 2 bytes to represent.
* Encapsulate Internal APIS
* Enhanced Deprecation
* Spin-Wait Hints
