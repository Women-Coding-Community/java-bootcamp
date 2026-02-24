# The `final` Keyword in Java 21

> 📝 **Bootcamp Note:** This topic came up during Session 3 while we were debugging the WCC platform project. It's a foundational Java concept that even experienced engineers get asked about in interviews. In Java 21, understanding `final` also means knowing when *not* to use it — because modern Java gives you better tools for some of the same problems.

---

## What does `final` mean?

The `final` keyword is about **defining contracts** — it tells the compiler and other developers: *"this should not be changed, extended, or overridden."* Where you use it changes exactly what is being locked down.

There are **four places** you can use `final`:

| Where | What it locks |
|---|---|
| Variable | Cannot be reassigned |
| Parameter | Cannot be changed inside the method |
| Method | Cannot be overridden by a subclass |
| Class | Cannot be extended (inherited) |

---

## 1. `final` on a Variable

When you mark a variable as `final`, you're saying: **you cannot reassign it after it's set**.

```java
final int year = 2026;
year = 2027; // ❌ Compiler error — cannot reassign a final variable
```

### ⚠️ Important nuance: `final` ≠ immutable

For **objects**, `final` prevents you from pointing to a *new* object — but you **can still change the object's internal state**:

```java
final List<String> participants = new ArrayList<>();
participants = new ArrayList<>();  // ❌ Cannot reassign the reference
participants.add("Olena");         // ✅ Can still modify the contents
```

Think of it like this: **`final` locks the address in memory, not the content at that address.**

---

## 2. `final` on a Constant (`static final`)

The most common use of `final` in real projects is paired with `static` to define **constants** — values that belong to the class itself and never change.

```java
public class AppConfig {
    public static final int MAX_RETRIES = 3;
    public static final String API_VERSION = "v1";
}
```

By convention, constants use **UPPER_SNAKE_CASE**. You'll see this pattern everywhere in Java code — including in the WCC platform project.

```java
// Usage
if (retryCount > AppConfig.MAX_RETRIES) {
    throw new RuntimeException("Too many retries");
}
```

---

## 3. `final` on a Method Parameter

When a parameter is marked `final`, it **cannot be reassigned inside the method**.

```java
public void greet(final String name) {
    name = "Someone else"; // ❌ Compiler error
    System.out.println("Hello, " + name);
}
```

### Why do this?
It protects the **caller's contract** and prevents accidental mutation of an incoming value inside long methods. It's also a useful signal to readers: *"I'm using this value as-is."*

> 💡 **Best practice note:** Using `final` on parameters is optional and sometimes considered noise by teams. Some style guides encourage it for clarity; others skip it to reduce verbosity. Check your team's conventions — in our WCC project, we tend to be explicit.

---

## 4. `final` on a Method

When a method is `final`, **subclasses cannot override it**. This is used to lock critical behaviour in place even when a class *is* extendable.

```java
public class Member {
    public final String getAccountId() {
        return accountId; // No subclass can change how this works
    }
}

public class Mentor extends Member {
    @Override
    public String getAccountId() { // ❌ Compiler error — cannot override final method
        return "custom-id";
    }
}
```

### When to use it
- When a method implements core logic that **must stay consistent** across all subclasses
- When the method is part of a **security or integrity contract**
- Java's own `String` class uses this extensively

---

## 5. `final` on a Class

When a class is `final`, **no other class can extend it**.

```java
public final class Member {
    // ...
}

public class Mentor extends Member { // ❌ Compiler error — Member is final
    // ...
}
```

Java's own `String` class is `final` — that's why you can always trust how `String` behaves; no one can secretly subclass it and change its behaviour.

---

## 6. Modern Java 21: When `final` Has Better Alternatives

Java 21 introduced features that handle some traditional `final` use cases more elegantly. Understanding the relationship is important.

### 6.1 Records — Fields Are Implicitly `final`

Introduced in Java 16, **Records** are data-carrying classes where all fields are automatically `final`. You don't need to write `final` yourself.

```java
// Old way — manual final fields
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }
    // equals(), hashCode(), toString() — you'd write all of these too
}

// Java 21 way — Record does it all
public record Point(int x, int y) {}
// x and y are implicitly final
// Constructor, getters, equals(), hashCode(), toString() auto-generated
```

Records are also **implicitly `final` classes** — they can't be extended. Use records when you need a simple, immutable data container (DTOs, API responses, value objects).

```java
// Example from our bootcamp context
public record MentorSummary(String name, String socialNetwork, String profileUrl) {}
```

### 6.2 Sealed Classes — Controlled Inheritance (Better than `final` class in many cases)

Before Java 17, if you wanted to control inheritance, your only option was `final` (no subclasses at all) or leaving the class open to anyone. **Sealed classes** give you a middle ground: **you choose exactly which classes can extend yours**.

```java
// final — blocks all inheritance
public final class Shape { } // No subclasses allowed at all

// sealed — controlled inheritance (Java 17+, stable in Java 21)
public sealed class Shape permits Circle, Rectangle, Triangle {
    // Only Circle, Rectangle, and Triangle can extend Shape
}

public final class Circle extends Shape {
    private final double radius;
    // ...
}

public final class Rectangle extends Shape {
    private final double width;
    private final double height;
    // ...
}
```

Subclasses of a sealed class must declare one of:
- `final` — no further subclasses
- `sealed` — further controlled subclasses
- `non-sealed` — open again for anyone to extend

### Why this matters in Java 21

Sealed classes work beautifully with **pattern matching in switch** (also finalised in Java 21). The compiler knows all possible subtypes, so it can check you haven't missed a case:

```java
public double calculateArea(Shape shape) {
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
        case Triangle t  -> 0.5 * t.base() * t.height();
        // No default needed — compiler knows these are ALL the shapes
    };
}
```

This is a pattern you'll start seeing in modern Spring Boot projects.

---

## `final` vs Immutability — an Important Distinction

These are **not the same thing**, and confusing them is a common mistake.

| | `final` | Immutability |
|---|---|---|
| What it protects | The **reference** (the address) | The **content** (the values inside) |
| Can the internal state change? | Yes, if it's an object | No |
| Example | `final List<String> names` | `String`, `Integer`, Records |

```java
// final — the reference is locked, but contents can change
final List<String> names = new ArrayList<>();
names.add("Adriana");      // ✅ Allowed
names = new ArrayList<>(); // ❌ Not allowed

// Immutable — the content itself cannot change
String name = "Adriana";
name.replace("A", "B");    // Creates a NEW string, original unchanged
```

To create a truly immutable class in Java, you need:
1. Declare the class `final`
2. Make all fields `private final`
3. No setters
4. Return copies (not references) of mutable objects from getters

Or simply — **use a Record**. It does all of the above for you.

---

## Best Practices Summary

### ✅ Do use `final` for:

- **Constants** — `public static final String BASE_URL = "..."` Always.
- **Local variables you won't reassign** — makes intent clear and code easier to follow
- **Method parameters in complex methods** — prevents accidental mutation
- **Methods with critical logic** — when a subclass should never change a specific behaviour
- **Classes that must never be subclassed** — utility classes, value objects

### ❌ Avoid `final` when:

- **You need testability** — `final` classes and methods cannot be mocked easily in unit tests. Be deliberate before making something final in production code.
- **You want controlled (not blocked) inheritance** — use `sealed` instead
- **You're adding `final` to every parameter** without reason — it becomes noise and reduces readability

### 🔄 Prefer modern alternatives when:

| Old approach | Modern Java 21 approach |
|---|---|
| Class with all `private final` fields + constructor | `record` |
| `final` class when you want *some* subclasses | `sealed` class |
| Manual constants with verbose setup | `static final` (still the right tool here) |

---

## Quick Reference: Where Can You Use `final`?

```java
// 1. Static constant
public static final int MAX_CONNECTIONS = 10;

// 2. Instance variable
private final String id;

// 3. Local variable
final String prefix = "WCC-";

// 4. Method parameter
public void process(final String input) { ... }

// 5. Method
public final String getId() { return id; }

// 6. Class
public final class BootcampConfig { ... }
```

---

## 📚 Try it yourself

Experiment with each use case. Uncomment the lines to see what errors the compiler gives you — reading compiler errors is part of learning Java!

```java
import java.util.ArrayList;
import java.util.List;

public class FinalExplorer {

    // Constant — the Java convention for constants
    public static final String BOOTCAMP_NAME = "WCC Java Bootcamp";

    public static void main(String[] args) {

        // 1. Final variable
        final int year = 2026;
        // year = 2027; // ← Uncomment to see compiler error

        // 2. Final with a list — reference is locked, content is not
        final List<String> participants = new ArrayList<>();
        participants.add("Olena");
        participants.add("Lulu");
        participants.add("Val");
        // participants = new ArrayList<>(); // ← Uncomment to see compiler error

        System.out.println(BOOTCAMP_NAME + " - " + year);
        System.out.println(participants);
    }
}

// 3. Record — implicitly final fields + implicitly final class
record SessionNote(String topic, String presenter) {}

// 4. Sealed class — controlled inheritance
sealed class Participant permits Mentor, Mentee {}
final class Mentor extends Participant {}
final class Mentee extends Participant {}
// class RandomPerson extends Participant {} // ← Uncomment to see compiler error
```

Push your experiment to your participant folder and note the errors! 🚀

---

> 💡 **Interview tip:** A strong answer to *"Explain the `final` keyword in Java"* covers all four uses (variable, parameter, method, class), explains the `final` vs immutability distinction, and — in 2025+ interviews — mentions Records and Sealed Classes as modern alternatives. That's a senior-level answer.