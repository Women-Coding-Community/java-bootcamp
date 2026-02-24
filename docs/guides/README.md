# Java Guides Index

> 📚 **Comprehensive Java guides created during bootcamp sessions.** Each guide is designed to be practical, beginner-friendly, and immediately applicable to your projects.

## Quick Navigation

- **New to Java?** → Start with [Fundamentals](#-fundamentals)
- **Interview prep?** → Check [High-Value Topics](#-interview-focus-topics)
- **Working on a project?** → See [By Project Type](#-guides-by-project-type)
- **Missed a session?** → View [By Session](#-guides-by-session)

---

## 🟢 Fundamentals

Core Java concepts every developer needs to master.

### [Understanding Numeric Literals](./numeric-literals-simplified.md)
**Difficulty:** 🟢 Beginner
**Session:** Session 2
**Topics:** `int`, `double`, type conversion, casting, common pitfalls

**You'll learn:**
- Which numeric types to use (and which to skip for now)
- How to avoid the infamous "027 = 23" octal trap
- When to use `int` vs `long` vs `double`
- Type conversion best practices

**Includes:**
- ✅ Practice exercises with solutions
- ✅ Real-world examples
- ✅ Clear "skip for now" sections

**Best for:** First week of learning Java, debugging type errors

---

### [The `final` Keyword in Java 21](./final-keyword-java-21.md)
**Difficulty:** 🟡 Intermediate
**Session:** Session 3
**Topics:** Constants, immutability, Records, Sealed Classes

**You'll learn:**
- All four uses of `final` (variable, parameter, method, class)
- The critical distinction between `final` and immutability
- Modern Java 21 alternatives: Records and Sealed Classes
- When `final` is the right tool (and when it's not)

**Includes:**
- ✅ Interview preparation section
- ✅ Real WCC platform debugging example
- ✅ Hands-on coding exercise
- ✅ Modern Java 21 patterns

**Best for:** Understanding constants, preparing for interviews, working with immutable data

---

## 📅 Coming Soon

### Variables and Scope
**Topics:** Local vs instance vs class variables, scope rules, shadowing

### Collections Framework Deep Dive
**Topics:** ArrayList, HashMap, HashSet, when to use which collection

### Object-Oriented Programming
**Topics:** Class design, inheritance, polymorphism, interfaces

### Streams and Lambda Expressions
**Topics:** Functional programming in Java, stream operations, best practices

---

## 🎯 Interview Focus Topics

Guides that are particularly valuable for technical interviews:

| Guide | Why It's Important | Common Questions |
|-------|-------------------|------------------|
| [The `final` Keyword](./final-keyword-java-21.md) | Shows depth of Java knowledge | "What does `final` mean?" "Explain immutability" |
| [Numeric Literals](./numeric-literals-simplified.md) | Demonstrates type system understanding | "Difference between `int` and `long`?" |
| Collections (Coming) | Most used data structures | "ArrayList vs LinkedList?" |
| OOP Principles (Coming) | Foundation of Java | "Explain inheritance vs composition" |

---

## 📂 Guides by Project Type

### Building a Basic Project (CLI app)
**Essential guides:**
1. ✅ [Numeric Literals](./numeric-literals-simplified.md) - Understanding data types
2. ✅ [The `final` Keyword](./final-keyword-java-21.md) - Constants and configuration
3. 🔜 Collections - Storing and managing data
4. 🔜 File I/O - Reading and writing data

**Your focus:** Core Java, no frameworks

---

### Building an Intermediate Project (Spring Boot)
**Essential guides:**
1. ✅ All basic guides (above)
2. 🔜 OOP Principles - Designing your domain model
3. 🔜 Collections & Streams - Data processing
4. 🔜 Exceptions - Error handling
5. 🔜 Spring Boot Basics (planned)

**Your focus:** Core Java + Spring Boot framework

---

## 🗓️ Guides by Session

| Session   | Date       | Guide Created                                        | Topics Covered                                    |
|-----------|------------|------------------------------------------------------|---------------------------------------------------|
| Session 1 | 2026-02-05 | (Setup)                                              | Bootcamp kickoff, environment setup               |
| Session 2 | 2026-02-12 | [Numeric Literals](./numeric-literals-simplified.md) | Data types, type conversion, Debug, Setup Project |
| Session 3 | 2026-02-19 | [The `final` Keyword](./final-keyword-java-21.md)    | Serializastion, Constants, immutability, Records  |
| Session 4 | 2026-02-26 | 🔜 Coming                                            |                                                   |
| Session 5 | 2026-03-07 | 🔜 Coming                                            |                                                   |
| Session 6 | 2026-03-12 | 🔜 Coming - Hybrid                                   |                                                   |
| Session 7 | 2026-03-13 | 🔜 Coming - Hybrid                                |                                                   |

**Missed a session?** See detailed summaries in [Session Notes](../SESSION_NOTES.md)

---

## 💡 How to Use These Guides

### For Self-Study
1. Start with the [Learning Path](../LEARNING_PATH.md) to see the recommended order
2. Read the guide at your own pace
3. Complete the practice exercises
4. Try applying concepts in your project
5. Ask questions in #java-bootcamp

### For Interview Prep
1. Focus on [Interview Focus Topics](#-interview-focus-topics)
2. Understand not just "what" but "why"
3. Complete the coding exercises
4. Explain concepts out loud to yourself
5. Read the "Best Practices" sections carefully

### For Project Work
1. Use [Guides by Project Type](#-guides-by-project-type) to find relevant topics
2. Keep guides open as reference while coding
3. Apply patterns from the real-world examples
4. Check FAQ when you hit issues

---

## 🤝 Contributing

### Found a mistake?
- Open an issue with the guide name and section
- Or submit a PR with the fix
- Small typos? Just PR directly

### Want to add a guide?
See the [Guide Template](#guide-template-for-mentors) below

### Have a suggestion?
- Post in #java-bootcamp
- Mention it in a session
- Open a GitHub discussion

---

## 📋 Guide Template (For Mentors)

When creating a new guide, use this structure:

```markdown
# [Topic Name]

> 📝 **Bootcamp Note:** Context about when/why this topic came up

## What is [Topic]?

Brief explanation of the concept

## Why It Matters

Real-world relevance, why we're teaching this

## The Basics

Core concepts with code examples

## Common Pitfalls

Mistakes to avoid (with ❌ and ✅ examples)

## Modern Java Approach (if applicable)

How Java 21+ makes this better/different

## Practice Exercises

Hands-on practice with solutions

## Real-World Examples

Examples from WCC platform or common projects

## Best Practices Summary

✅ Do / ❌ Don't section

## Resources

Links to official docs, related guides

---

*This guide is part of [WCC Java Bootcamp](../../README.md)*
```

### Checklist for New Guides
- [ ] Clear learning objectives stated upfront
- [ ] Code examples with both ❌ wrong and ✅ correct versions
- [ ] Practice exercises with hidden solutions
- [ ] Connection to real-world use cases
- [ ] Links to related guides and FAQ entries
- [ ] Beginner-friendly language (avoid jargon or explain it)
- [ ] Updated in [Guides Index](./README.md)
- [ ] Linked from [Learning Path](../LEARNING_PATH.md)
- [ ] Mentioned in [Session Notes](../SESSION_NOTES.md)
- [ ] Common questions added to [FAQ](../FAQ.md)

---

## 📊 Guide Stats

- **Published guides:** 2
- **Guides in progress:** 0
- **Planned guides:** 4
- **Total FAQ entries:** 14
- **Last updated:** 2026-02-19

---

*These guides are community-maintained and continuously improved based on participant feedback.*

**Have questions?** Check the [FAQ](../FAQ.md) or ask in #java-bootcamp!
