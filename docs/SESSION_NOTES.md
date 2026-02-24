# Java Bootcamp Session Notes

> 📅 **Live session summaries, topics discussed, and resources.**

## How to Use This Document

- **Missed a session?** Read the summary and check related guides
- **Preparing for next session?** Review prerequisites
- **Want to revisit a topic?** Find the session and follow the links

---

## Session Schedule

| Session | Date | Topic | Status | Recording |
|---------|------|-------|--------|-----------|
| Session 1 | TBD | Bootcamp Kickoff & Setup | ✅ Completed | [Link](#session-1-bootcamp-kickoff--setup) |
| Session 2 | TBD | Data Types & Numeric Literals | ✅ Completed | [Link](#session-2-data-types--numeric-literals) |
| Session 3 | TBD | The `final` Keyword & Debugging | ✅ Completed | [Link](#session-3-the-final-keyword--debugging) |
| Session 4 | TBD | Collections & Generics | 📅 Upcoming | - |
| Session 5 | TBD | Object-Oriented Programming | 📅 Planned | - |

---

## Session 1: Bootcamp Kickoff & Setup

**Date:** 05.02.2026
**Meetup:** https://www.meetup.com/women-coding-community/events/312920485
**Records:** 
- https://www.youtube.com/watch?v=45aXzUkRwxs
- https://www.youtube.com/watch?v=5UJYHXLgR7s
- https://drive.google.com/file/d/1tXFWKV16XDcz41JSbgWfcP3k1VS5xuhk

### Topics Covered
- ✅ Bootcamp structure and expectations
- ✅ How to fork and submit projects
- ✅ JDK 21 installation and verification
- ✅ IDE setup (IntelliJ IDEA)
- ✅ First Gradle build

### Key Takeaways
1. **Consistency over speed** - Code regularly, even if just 30 minutes
2. **Community support** - Use #java-bootcamp for questions
3. **Start simple** - Basic projects are perfectly valid

### Questions Raised

**Q: "Can I use JDK 25 instead of 21?"**
A: Yes, but ensure your Gradle config targets Java 21 for compatibility.
*→ Added to [FAQ #9](./FAQ.md#9-jdk-21-vs-jdk-25)*

**Q: "Do I need to know Spring Boot to start?"**
A: No! Start with core Java. Spring Boot is for intermediate projects.
*→ Added to [FAQ #13](./FAQ.md#13-should-i-use-spring-boot-for-a-basic-project)*

### Resources
- [How to Fork Guide](./HOW_TO_FORK.md)
- [How to Submit Guide](./HOW_TO_SUBMIT.md)
- [Project Ideas](./PROJECT_IDEAS.md)

### Action Items
- [ ] Participants: Fork repo and create your folder
- [ ] Participants: Install JDK 21 and verify with `java -version`
- [ ] Participants: Choose a project from [Project Ideas](./PROJECT_IDEAS.md)

### Next Session Preview
We'll dive into Java data types and numeric literals - understanding `int`, `double`, and common type conversion pitfalls.

---

## Session 2: Data Types & Numeric Literals

**Date:** TBD
**Duration:** 90 minutes
**Attendees:** ~XX participants

### Topics Covered
- ✅ Primitive types: `int`, `double`, `long`, `float`
- ✅ Type conversion and casting
- ✅ Common beginner mistakes
- ✅ When to use which type

### Key Takeaways
1. **Keep it simple** - For 90% of code, `int` and `double` are sufficient
2. **Leading zeros are dangerous** - `027` is octal (base-8), not 27!
3. **Type safety matters** - Java won't let you lose data accidentally

### Demo Code
```java
// Type conversion examples from session
int age = 25;
double price = 19.99;

// ❌ This fails - can't fit decimal into int
int dollars = price;  // Compile error

// ✅ Explicit cast - you're accepting data loss
int dollars = (int) price;  // dollars = 19

// ✅ Automatic widening - safe conversion
double value = age;  // value = 25.0
```

### Questions Raised

**Q: "Why does 027 print as 23?"**
A: Leading zero means octal notation! Never use leading zeros unless you specifically need octal.
*→ Added to [FAQ #6](./FAQ.md#6-why-do-i-see-027--23)*

**Q: "When should I use `long` instead of `int`?"**
A: Timestamps, file sizes, large database IDs. For most counting, `int` is fine.
*→ Added to [FAQ #5](./FAQ.md#5-when-to-use-int-vs-long)*

**Q: "What's the `f` suffix in `3.14f`?"**
A: Marks it as a `float` instead of `double`. You can skip this for now - `double` is almost always better.
*→ Covered in [Numeric Literals Guide - Skip Section](./guides/numeric-literals-simplified.md#skip-these-for-now)*

### Guides Created
📖 **[Understanding Numeric Literals in Java](./guides/numeric-literals-simplified.md)**
- Focuses on the 80/20 rule for beginners
- Clearly marks what you can skip
- Includes practice exercises with solutions

### Resources
- [Official Java Docs: Primitive Data Types](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)
- [JetBrains Academy: Primitive Types](https://hyperskill.org/learn/step/3522)

### Action Items
- [ ] Complete practice exercises in [Numeric Literals Guide](./guides/numeric-literals-simplified.md#practice-exercises-)
- [ ] Experiment with type conversion in your project
- [ ] Share any confusing error messages in #java-bootcamp

### Next Session Preview
We'll explore the `final` keyword - constants, immutability, and modern Java 21 features like Records and Sealed Classes.

---

## Session 3: The `final` Keyword & Debugging

**Date:** TBD
**Duration:** 90 minutes
**Attendees:** ~XX participants

### Topics Covered
- ✅ Four uses of `final`: variable, parameter, method, class
- ✅ `final` vs immutability (common misconception!)
- ✅ Modern alternatives: Records, Sealed Classes
- ✅ Live debugging of WCC platform project

### Key Takeaways
1. **`final` ≠ immutable** - It locks the reference, not the content
2. **Use Records for data classes** - They're implicitly final and immutable
3. **Sealed classes > final classes** - When you want controlled inheritance
4. **Interview topic** - Understanding all four uses shows depth

### Demo Code

**From WCC Platform Debugging:**
```java
// Problem we debugged
final List<String> participants = new ArrayList<>();
participants.add("Olena");  // ✅ Allowed - modifying content
participants = new ArrayList<>();  // ❌ Not allowed - changing reference
```

**Modern Java 21 Approach:**
```java
// Old way - lots of boilerplate
public class Point {
    private final int x;
    private final int y;
    // Constructor, getters, equals, hashCode, toString...
}

// New way - Record does it all
public record Point(int x, int y) {}
// Implicitly final class, implicitly final fields
```

### Questions Raised

**Q: "If `final List` can be modified, how do I truly freeze it?"**
A: Use `List.of()` for immutable lists:
```java
List<String> names = List.of("Alice", "Bob");
names.add("Charlie");  // ❌ UnsupportedOperationException
```
*→ Added to [FAQ #8](./FAQ.md#8-can-i-modify-a-final-list)*

**Q: "Should I mark all method parameters as final?"**
A: Optional - some teams do it for clarity, others find it verbose. Check your team's style guide.
*→ Covered in [Final Keyword Guide - Best Practices](./guides/final-keyword-java-21.md#-avoid-final-when)*

**Q: "When should I use Records vs regular classes?"**
A: Records for simple data carriers (DTOs, API responses, value objects). Regular classes when you need complex behavior or mutability.
*→ Covered in [Final Keyword Guide - Records Section](./guides/final-keyword-java-21.md#61-records--fields-are-implicitly-final)*

### Real-World Context
We debugged an issue in the WCC platform where a `final` variable's content was unexpectedly changed. This led to a great discussion about `final` vs immutability and when to use each.

**Bug:** Configuration values were changing mid-request
**Root cause:** `final` reference to a mutable object
**Fix:** Switched to a Record for true immutability

### Guides Created
📖 **[The `final` Keyword in Java 21](./guides/final-keyword-java-21.md)**
- All four uses of `final` with examples
- `final` vs immutability distinction
- Modern Java 21 alternatives (Records, Sealed Classes)
- Interview preparation tips

### Resources
- [Oracle Docs: Final Keyword](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4.3.3)
- [Java Records (JEP 395)](https://openjdk.org/jeps/395)
- [Sealed Classes (JEP 409)](https://openjdk.org/jeps/409)

### Action Items
- [ ] Complete the `FinalExplorer` exercise in the [guide](./guides/final-keyword-java-21.md#-try-it-yourself)
- [ ] Identify places in your project where Records could replace regular classes
- [ ] Experiment with Sealed Classes for controlled hierarchies

### Next Session Preview
Collections and Generics - ArrayList, HashMap, and how to work with generic types safely.

---

## Session 4: Collections & Generics

**Date:** TBD
**Duration:** 90 minutes
**Status:** 📅 Upcoming

### Planned Topics
- ArrayList vs Arrays
- HashMap and HashSet
- When to use which collection
- Introduction to Generics
- Common collection pitfalls

### Prerequisites
- Comfort with classes and objects
- Understanding of `final` (Session 3)
- Basic loop knowledge

### Preparation
- Review [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/)
- Think about how you're storing data in your project

---

## Session 5: Object-Oriented Programming

**Date:** TBD
**Duration:** 90 minutes
**Status:** 📅 Planned

### Planned Topics
- Class design principles
- Inheritance and composition
- Interfaces vs abstract classes
- Encapsulation best practices

---

## 💡 How to Contribute

**After each session:**
1. Mentors: Update this document with summary, questions, and resources
2. Create/update guides as needed
3. Add common questions to [FAQ](./FAQ.md)
4. Link to recordings when available

**Template for new sessions:**
```markdown
## Session X: [Topic]

**Date:** YYYY-MM-DD
**Duration:** XX minutes
**Attendees:** ~XX participants

### Topics Covered
- ✅ Topic 1
- ✅ Topic 2

### Key Takeaways
1. Main point 1
2. Main point 2

### Questions Raised
**Q: "Question?"**
A: Answer
*→ Link to FAQ or guide*

### Guides Created
📖 **[Guide Name](link)**

### Resources
- [Resource 1](link)

### Action Items
- [ ] Task 1

### Next Session Preview
Brief preview...
```

---

*This document is updated after each session. Last updated: 2026-02-19*
