## ⭐ What Are Java Streams?
A Stream is not a data structure.
It's a pipeline used to process data in a declarative way.
Think of it as:

> “A sequence of elements + a set of operations (like map/filter) that transform those elements.”

**Streams allow you to write code like:**
```java
List<String> names = List.of("Sonali", "Alex", "John");
names.stream()
     .filter(n -> n.startsWith("S"))
     .map(String::toUpperCase)    
     .forEach(System.out::println);

OUTPUT : SONALI

```

**Stream Pipeline Structure**
- Every stream has a 3‑step flow:
    > source → intermediate operations → terminal operation
    ``` java
        List<String> emails = employees.stream()    // source
        .filter(e -> e.isActive())           // intermediate
        .map(Employee::getEmail)             // intermediate
        .collect(Collectors.toList());       // terminal

    ```

### Key Operations — map, filter, flatMap

- 🔹 filter() — Keep only elements that match a condition
``` java
List<Integer> nums = List.of(1, 2, 3, 4, 5);
List<Integer> even = nums.stream()    
                         .filter(n -> n % 2 == 0)    
                         .toList();
```                         
**Use when**:
You want to remove unwanted items.

- 🔹 map() — Transform each element into something else
``` java
List<String> names = List.of("sonali", "alex");
List<String> upper = names.stream()
                          .map(String::toUpperCase)
                          .toList();
```
**Use when:**
You want to change elements (e.g., convert object → another object).

- 🔹 flatMap() — Flatten nested lists
**If you have lists inside lists:**
``` java
List<List<String>> list = List.of(List.of("a", "b"),List.of("c", "d"));
List<String> flat = list.stream().flatMap(List::stream).toList();

Output:
[a, b, c, d]
```
**Use when:**
You have nested structures: List<List<T>>, Stream<Stream<T>>.

### Terminal Operations — collect, forEach, reduce

- collect() — Convert Stream → List/Set/Map (or aggregate structures)
``` java

Collect to a List
List<String> names = List.of("Alex", "John", "Sonali");
List<String> upperNames = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
        
OR
use - .toList() in latest java versions.
List<String> upperNames = names.stream()
        .map(String::toUpperCase)
        .toList();        

Collect to a Set (remove duplicates)

List<Integer> nums = List.of(1, 2, 2, 3, 3, 3);
Set<Integer> unique = nums.stream()
        .collect(Collectors.toSet());

```
**Use when:**
You want to transform a stream into a collection or summary result.

- forEach() — Perform an action on each element
``` java

List<String> names = List.of("Alex", "John", "Sonali");
names.stream()
     .forEach(name -> System.out.println("Hello " + name));

```
**Use when:**
You want to iterate and perform side effects like printing, logging, saving to DB, etc.

- reduce() — Reduce elements to a single result
``` java
public abstract T reduce(
    T identity,
    java.util.function.BinaryOperator<T> accumulator
)
example -
List<Integer> nums = List.of(1, 2, 3, 4, 5);
int sum = nums.stream()
        .reduce(0, (a, b) -> a + b);
OR

List<String> words = List.of("Java", "Streams", "Are", "Powerful");
String sentence = words.stream()
        .reduce("", (a, b) -> a + " " + b).trim();
        
Output - Java Streams Are Powerful
```
Note: In Stream.reduce(identity, accumulator), the identity is the default, starting, or initial value for the reduction process.

| Operation | Purpose                                    | Typical Use                              |
|-----------|--------------------------------------------|------------------------------------------|
| collect() | Convert stream → List/Set/Map or aggregate | Create collections, grouping, statistics |
| forEach() | Perform an action for each element         | Logging, printing, saving to DB          |
| reduce()  | Combine all elements into one result       | Sum, max/min, string join, aggregation   |


### 🧠 When Not to Use Streams
**Avoid streams when:**
- ❌ Logic is too complex → loops are clearer
- ❌ You need index-based operations
- ❌ You need mutation of external state
- ❌ Performance is critical (some operations slower than loops)
