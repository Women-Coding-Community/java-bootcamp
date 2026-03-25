---
name: unit-test
description: Write JUnit 5 unit tests for a Java class following project conventions — Arrange-Act-Assert structure, should-prefix naming, JUnit Jupiter assertions. Use when asked to write tests or add unit test coverage.
allowed-tools: Read, Write, Edit, Bash, Glob
---

# Unit Test Skill

> **Canonical runbook**: `.ai/skills/unit-test.md`
> This file is the Claude Code adapter. The workflow logic is defined in the canonical skill
> so it can be shared with other agents (Codex, Copilot, Cursor). Any changes to the workflow
> should be made in `.ai/skills/unit-test.md` first.

You are now in unit-test mode. Follow these steps **in order**.

## Step 1: Read the class under test

Read the full source file of the class to test. Identify:
- All public methods and their signatures
- Edge cases: null, empty string, invalid format, boundary values
- What each method is responsible for

## Step 2: Locate or create the test file

Test file path: `src/test/java/<same-package>/<ClassName>Test.java`

If the file already exists, read it first to avoid duplicating existing tests.

## Step 3: Write the tests

**Naming conventions:**
- Test class: `<ClassName>Test` — no `public` modifier on the class
- Test methods: `shouldDoSomethingWhenCondition` — camelCase, `should` prefix

**Structure — every test must follow Arrange-Act-Assert:**
```java
@Test
void shouldDescribeExpectedBehaviour() {
    //Arrange
    <set up inputs>

    //Act
    <call the method>

    //Assert
    <assert the result>
}
```

**Assertions — use JUnit Jupiter only (no AssertJ, no Hamcrest):**
```java
import static org.junit.jupiter.api.Assertions.*;

assertTrue(result);
assertFalse(result, "failure message");
assertEquals(expected, actual);
assertThrows(ExceptionType.class, () -> methodCall());
assertNull(result);
assertNotNull(result);
```

**Coverage checklist per method:**
- [ ] Happy path — valid input returns expected result
- [ ] Invalid input — bad format or wrong value is rejected
- [ ] Boundary values — empty string, null, min/max
- [ ] Edge cases specific to the logic

## Step 4: Run the tests

```bash
./gradlew test
```

If a test fails, read the error, fix the test (or flag a real bug), and re-run. Do not leave failing tests.

## Step 5: Report

List every test written with its method name and the scenario it covers.
Call out any coverage gaps that were not addressed.

## Rules

- Never modify the class under test to make tests pass
- Use Mockito (mockito-core 5.11.0) only for interface/CLI-layer tests where real objects cannot be used (e.g. MemberRepository in MemberCliTest). Prefer real objects for unit tests of plain classes.
- Tests must be independent — no shared mutable state between test methods
- One focused assertion per test
- Never commit failing tests
