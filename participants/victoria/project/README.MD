# Mentorship Matcher

A Spring Boot web application that matches mentors with mentees based on skills and learning goals. Built as part of the WCC Java Bootcamp.

## Features

- **Mentor Registration**: Register mentors with their expertise areas and maximum mentee capacity
- **Mentee Registration**: Register mentees with their learning goals and experience level
- **Smart Matching**: Algorithm matches mentees to mentors based on skill compatibility
- **Match Management**: View, activate, and manage mentor-mentee relationships
- **Data Persistence**: H2 file-based database ensures data survives server restarts

## Tech Stack

- **Java 23** - Language
- **Spring Boot 4.0.2** - Web framework
- **Spring Data JPA** - Database access
- **H2 Database** - Embedded file-based persistence
- **Thymeleaf** - Server-side templating
- **Bootstrap 5.3.2** - UI styling
- **Gradle** - Build tool

## Prerequisites

- Java 23 (Eclipse Adoptium recommended)
- Gradle 9.x (or use the included Gradle wrapper)

## Running the Application

### From the project root directory:

```powershell
# Set JAVA_HOME (Windows PowerShell)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-23.0.2.7-hotspot"

# Run the application
.\gradlew.bat bootRun
```

### From terminal (Unix/Mac):

```bash
export JAVA_HOME=/path/to/jdk-23
./gradlew bootRun
```

The application will start at **http://localhost:8080**

## Running Tests

```powershell
# Set JAVA_HOME (Windows PowerShell)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-23.0.2.7-hotspot"

# Run all tests
.\gradlew.bat test --no-daemon
```

Test results are generated at `C:\Temp\gradle-build\java-bootcamp\reports\tests\test\index.html`

### Test Coverage

| Test Class | Tests | Description |
|------------|-------|-------------|
| `MentorTest` | 16 | Model tests: constructor, equality, expertise matching, mentee capacity |
| `MenteeTest` | 15 | Model tests: constructor, equality, learning goals, match status |
| `MatchTest` | 17 | Model tests: constructor, equality, lifecycle (activate/cancel/complete), file format |
| `MentorshipServiceTest` | 18 | Service tests: mentor/mentee/match CRUD operations, statistics |
| `DtoValidationTest` | 22 | Validation tests: registration form input validation |
| **Total** | **88** | |

### Test Structure

```
participants/victoria/project/src/test/java/com/wcc/bootcamp/java/mentorship/
├── model/
│   ├── MentorTest.java              # Mentor entity tests
│   ├── MenteeTest.java              # Mentee entity tests
│   └── MatchTest.java               # Match entity tests
├── service/
│   └── MentorshipServiceTest.java   # Business logic tests (with Mockito)
└── dto/
    └── DtoValidationTest.java       # Form validation tests
```

> **Note**: Controller tests are not included because `@WebMvcTest` and `@AutoConfigureMockMvc` are not available in Spring Boot 4.0.2's `spring-boot-starter-test`.

## Application Pages

| URL | Description |
|-----|-------------|
| `/` | Home page with navigation |
| `/mentors` | List all registered mentors |
| `/mentors/register` | Register a new mentor |
| `/mentors/{id}` | View mentor profile |
| `/mentees` | List all registered mentees |
| `/mentees/register` | Register a new mentee |
| `/mentees/{id}` | View mentee profile |
| `/matches` | View active matches |
| `/matches/find` | Find potential matches |

## Database

The application uses H2 database with file-based persistence:

- **Location**: `./data/mentorship-db.mv.db`
- **Console**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:file:./data/mentorship-db`
- **Username**: `sa`
- **Password**: *(empty)*

Data persists between server restarts. To reset the database, delete the `data/` folder.

## Project Structure

```
participants/victoria/project/src/main/java/com/wcc/bootcamp/java/mentorship/
├── MentorshipWebApplication.java    # Main Spring Boot application
├── controller/
│   ├── HomeController.java          # Home page
│   ├── MentorController.java        # Mentor CRUD operations
│   ├── MenteeController.java        # Mentee CRUD operations
│   └── MatchController.java         # Matching operations
├── model/
│   ├── Mentor.java                  # Mentor entity
│   ├── Mentee.java                  # Mentee entity
│   └── Match.java                   # Match entity
├── repository/
│   ├── MentorRepository.java        # Mentor data access
│   ├── MenteeRepository.java        # Mentee data access
│   └── MatchRepository.java         # Match data access
├── service/
│   └── MentorshipService.java       # Business logic
└── dto/
    ├── MentorRegistrationForm.java  # Form binding for mentors
    └── MenteeRegistrationForm.java  # Form binding for mentees
```

## How Matching Works

1. The algorithm compares each mentee's learning goals against each mentor's expertise areas
2. Skills are matched using case-insensitive partial matching (e.g., "java" matches "Java programming")
3. A compatibility score (0-100%) is calculated based on the percentage of mentee goals that match mentor expertise
4. Matches are ranked by compatibility score
5. Only mentors with available capacity are shown as potential matches

## Author

Victoria - WCC Java Bootcamp Participant
