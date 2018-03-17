##Streams improvements

**Stream::takeWhile**

```
Stream<T> takeWhile(Predicate<? super T> predicate);
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
Stream<T> dropWhile(Predicate<? super T> predicate);
```

It does just the opposite of takeWhile: Called on an ordered stream it will return a new one that consists of the first element that failed the predicate and all following ones. Or, closer to its name, it drops elements while the predicate holds and returns the rest.

```
List<String> strings = Stream.of("this is a list of strings".split(" "))
                .dropWhile(s -> !s.equals("of"))
                .collect(Collectors.toList());

System.out.println(strings);//of list
```

