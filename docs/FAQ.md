# Java Bootcamp FAQ

> 💬 **Common questions from bootcamp participants.** Can't find your question? Ask in #java-bootcamp!

## Table of Contents

### Bootcamp Logistics
1. [How do I submit my project?](#1-how-do-i-submit-my-project)
2. [Can I change my project after starting?](#2-can-i-change-my-project-after-starting)
3. [What if I miss a session?](#3-what-if-i-miss-a-session)

### Java Fundamentals
4. [Type conversion errors](#4-type-conversion-errors)
5. [When to use int vs long?](#5-when-to-use-int-vs-long)
6. [Why do I see 027 = 23?](#6-why-do-i-see-027--23)
7. [Final vs Immutability](#7-final-vs-immutability)
8. [Can I modify a final List?](#8-can-i-modify-a-final-list)

### Development Setup
9. [JDK 21 vs JDK 25?](#9-jdk-21-vs-jdk-25)
10. [IntelliJ vs VS Code?](#10-intellij-vs-vs-code)
11. [Gradle build fails](#11-gradle-build-fails)

### Project-Specific
12. [How much is "enough" for the certificate?](#12-how-much-is-enough-for-the-certificate)
13. [Should I use Spring Boot for a basic project?](#13-should-i-use-spring-boot-for-a-basic-project)
14. [How to structure my participant folder?](#14-how-to-structure-my-participant-folder)

---

## Bootcamp Logistics

### 1. How do I submit my project?

Follow these steps:
1. Complete your project in `/participants/<your-name>/project/`
2. Write a clear README.md with setup instructions
3. Create a pull request following [HOW_TO_SUBMIT.md](./HOW_TO_SUBMIT.md)
4. Wait for mentor review

**Related:** [How to Submit Guide](./HOW_TO_SUBMIT.md)

---

### 2. Can I change my project after starting?

Yes! Learning is the goal, not the specific project. Just:
- Update your project README
- Keep your existing work (don't delete learning history)
- Mention the pivot in your final submission

**Tip:** Sometimes "failing forward" with a project teaches more than succeeding with an easy one.

---

### 3. What if I miss a session?

No problem:
1. Check [Session Notes](./SESSION_NOTES.md) for summaries
2. Review any guides created from that session
3. Ask questions in #java-bootcamp
4. Sessions build on each other, but aren't mandatory

**Key Resources:**
- [Session Notes](./SESSION_NOTES.md)
- [Learning Path](./LEARNING_PATH.md)

---

## Java Fundamentals

### 4. Type conversion errors

**Error:** `incompatible types: possible lossy conversion from double to int`

**Why this happens:**
```java
double price = 19.99;
int dollars = price;  // ❌ Compile error
```

Java won't automatically convert `double` → `int` because you'd lose the decimal part (19.99 becomes 19).

**Solutions:**
```java
// Option 1: Keep as double
double dollars = price;  // ✅ No data loss

// Option 2: Explicitly cast (you're accepting data loss)
int dollars = (int) price;  // ✅ dollars = 19

// Option 3: Round instead of truncate
int dollars = (int) Math.round(price);  // ✅ dollars = 20
```

**Going the other way is fine:**
```java
int count = 42;
double value = count;  // ✅ Automatic widening conversion
```

**Related Guide:** [Numeric Literals](./guides/numeric-literals-simplified.md)

---

### 5. When to use int vs long?

**Quick answer:** Use `int` for 99% of cases. Only use `long` when you know you need it.

**Use `int` when:**
- Counting things (users, items, loops)
- IDs in small systems
- Whole numbers under ~2 billion

**Use `long` when:**
- Timestamps (`System.currentTimeMillis()`)
- File sizes
- Database IDs in large systems
- Money in cents (for precision)

**Example:**
```java
// ✅ Good uses of int
int studentCount = 150;
int retryAttempts = 3;

// ✅ Good uses of long
long timestamp = System.currentTimeMillis();  // Would overflow int
long fileSize = 5_368_709_120L;  // 5GB in bytes
```

**Related Guide:** [Numeric Literals - Skip Section](./guides/numeric-literals-simplified.md#skip-these-for-now)

---

### 6. Why do I see 027 = 23?

This confused multiple people in Session 2!

**The problem:**
```java
int value = 027;
System.out.println(value);  // Prints: 23 (not 27!)
```

**Why:** A leading zero (`0`) tells Java it's **octal** (base-8), not decimal (base-10).

27 in octal = 2×8¹ + 7×8⁰ = 16 + 7 = 23 in decimal

**The fix:** Never use leading zeros unless you specifically need octal (you don't).

```java
int value = 27;  // ✅ This is 27
```

**Related Guide:** [Numeric Literals - Warning Section](./guides/numeric-literals-simplified.md#one-important-warning-)

---

### 7. Final vs Immutability

**Common misconception:** "`final` makes something immutable"

**Reality:** `final` only locks the **reference**, not the **content**.

```java
final List<String> names = new ArrayList<>();
names.add("Adriana");      // ✅ Allowed - modifying content
names = new ArrayList<>(); // ❌ Not allowed - changing reference
```

**True immutability** means the content can't change:
```java
String name = "Adriana";
name.replace("A", "B");  // Creates NEW string, original unchanged
```

**To get true immutability:**
- Use Records (Java 16+)
- Use String, Integer, etc. (built-in immutables)
- Or manually: final class + final fields + no setters

**Related Guide:** [Final vs Immutability Section](./guides/final-keyword-java-21.md#final-vs-immutability--an-important-distinction)

---

### 8. Can I modify a final List?

Yes! This trips up many beginners.

```java
final List<String> participants = new ArrayList<>();
participants.add("Olena");     // ✅ Allowed
participants.clear();          // ✅ Allowed
participants = new ArrayList<>();  // ❌ Not allowed
```

**Think of it like this:**
- `final` = "This variable always points to the same List object"
- It does NOT mean "the List contents are frozen"

**To actually freeze the contents:**
```java
List<String> participants = List.of("Olena", "Lulu", "Val");
participants.add("Someone");  // ❌ UnsupportedOperationException
```

**Related Guide:** [Final on Variables](./guides/final-keyword-java-21.md#1-final-on-a-variable)

---

## Development Setup

### 9. JDK 21 vs JDK 25?

**The bootcamp officially uses JDK 21**, but JDK 25 works fine too.

**Why JDK 21?**
- Long-Term Support (LTS) release
- Stable and widely adopted
- All modern features we teach (Records, Sealed Classes, Pattern Matching)

**Can I use JDK 25?**
Yes, it's backward compatible. Just make sure your `build.gradle.kts` specifies:
```kotlin
java {
    sourceCompatibility = JavaVersion.VERSION_21
}
```

**Check your version:**
```bash
java -version
```

**Download JDK 21:**
- [OpenJDK](https://jdk.java.net/21/)
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java21)
- [Adoptium](https://adoptium.net/)

---

### 10. IntelliJ vs VS Code?

Both work! Choose based on your preference:

**IntelliJ IDEA (Community Edition):**
- ✅ Best Java tooling out-of-the-box
- ✅ Excellent refactoring and code completion
- ✅ Built-in Gradle support
- ❌ Heavier on resources

**VS Code:**
- ✅ Lightweight and fast
- ✅ Great if you already use it for other languages
- ⚠️ Requires Extension Pack for Java
- ⚠️ Less powerful refactoring

**Mentor recommendation:** IntelliJ for this bootcamp (better learning experience).

**VS Code setup:** Install [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

---

### 11. Gradle build fails

**Common issues:**

#### "JAVA_HOME not set"
```bash
# Mac/Linux
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Windows
# Set in System Environment Variables
```

#### "Could not find or load main class"
```bash
# Clean and rebuild
./gradlew clean build
```

#### "Permission denied: ./gradlew"
```bash
# Make it executable
chmod +x gradlew
```

#### Still stuck?
1. Check your Java version: `java -version`
2. Share the full error in #java-bootcamp
3. Include your OS and IDE

---

## Project-Specific

### 12. How much is "enough" for the certificate?

**Minimum viable submission:**
- ✅ Working Java project (even if small)
- ✅ Demonstrates learning (code quality > complexity)
- ✅ README with setup instructions
- ✅ Basic tests (at least one)

**Not required:**
- ❌ Complex features
- ❌ Perfect code
- ❌ Production-ready quality

**The goal is learning, not perfection.**

Examples of "enough":
- A working CLI tool with file I/O
- A simple REST API with 2-3 endpoints
- A basic application using core Java concepts

**Related:** [Project Ideas](./PROJECT_IDEAS.md)

---

### 13. Should I use Spring Boot for a basic project?

**Short answer:** No, unless you're specifically learning Spring Boot.

**Beginner path:**
1. Start with core Java (no frameworks)
2. Build a command-line app
3. Get comfortable with OOP, collections, file I/O
4. **Then** move to Spring Boot for your next project

**Why avoid Spring Boot initially?**
- Adds complexity that hides Java fundamentals
- Harder to debug when you don't know core Java
- Basic projects don't need a web framework

**When to use Spring Boot:**
- Intermediate projects (REST APIs, web apps)
- After you're comfortable with core Java
- When you specifically want to learn it

**Related:** [Project Ideas - Basic vs Intermediate](./PROJECT_IDEAS.md#-project-difficulty-levels)

---

### 14. How to structure my participant folder?

**Recommended structure:**
```
participants/<your-name>/
├── project/
│   ├── src/
│   │   ├── main/java/...
│   │   └── test/java/...
│   ├── build.gradle.kts  (if using Gradle)
│   ├── README.md
│   └── ...
└── notes/  (optional)
    └── session-notes.md
```

**Your README should include:**
1. Project name and description
2. How to run it
3. Features implemented
4. Technologies used
5. What you learned

**See example:**
- [participants/sonali/project/](../participants/sonali/project/)

**Related Guide:** [How to Submit](./HOW_TO_SUBMIT.md)

---

## 🙋 Didn't Find Your Question?

**Ask in the community:**
1. Post in #java-bootcamp Slack channel
2. Mention it in the next session
3. Open a GitHub discussion

**We'll add it to this FAQ!**

---

*This FAQ is community-maintained. Found a mistake? Submit a PR!*

*Last updated: 2026-02-19*
