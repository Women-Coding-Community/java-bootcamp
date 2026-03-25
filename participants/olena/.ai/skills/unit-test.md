Skill: unit-test
Write unit tests for Java classes following project conventions (JUnit Jupiter 5, Arrange-Act-Assert).

When to apply
Run this workflow when the user asks you to write tests, add unit tests, or /unit-test.

Step 1 — Read the class under test
Read the target class fully before writing anything. Identify:

- Public methods and their signatures
- Edge cases and boundary conditions (null, empty, invalid input)
- What each method is responsible for (single responsibility)

Step 2 — Locate or create the test file
Test file lives in: src/test/java/<same-package-as-class>/<ClassName>Test.java

If the file already exists, read it first to avoid duplicating existing tests.

Step 3 — Write the tests

Conventions (match existing project style):
- Class name: <ClassName>Test — no public modifier
- Method names: shouldDoSomethingWhenCondition — camelCase with should prefix
- Structure every test with three labeled sections:
  //Arrange
  //Act
  //Assert
- One assertion focus per test — do not assert multiple unrelated things in one test
- Use JUnit Jupiter assertions: assertTrue, assertFalse, assertEquals, assertThrows, assertNull, assertNotNull
- Import: import static org.junit.jupiter.api.Assertions.*;
- Annotate each test with @Test from org.junit.jupiter.api.Test

Test coverage checklist:
- Happy path — valid input produces expected result
- Invalid input — malformed or out-of-range values are rejected or throw
- Boundary values — empty string, null, minimum/maximum
- Edge cases specific to the class logic

Step 4 — Run the tests
Run: ./gradlew test

If any test fails, read the failure output, fix the test or identify if it reveals a real bug, and re-run.

Step 5 — Report
List each test written with its method name and what scenario it covers.
Call out any gaps in coverage that were not addressed.

Rules
- Never modify the class under test to make tests pass — fix the test instead
- Never use mocking libraries (project has no mocking dependency)
- Keep tests independent — no shared mutable state between tests
- Do not leave failing tests committed
