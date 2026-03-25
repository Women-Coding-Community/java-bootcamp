# Member Directory — CLI Tool

A command-line application built in Java to manage WCC (Women Coding Community) member profiles.

Members are stored in a CSV file and loaded automatically on startup. The app supports adding, viewing, updating, deleting, searching, and sorting members from an interactive terminal menu.

## Features

- Add new member profiles (name, email, location, skills)
- View all members in a formatted table
- Update existing member information (looked up by email)
- Delete members by email
- Search members by location
- Search members by skill
- Sort members by name (A–Z)
- Sort members by join date
- Email format validation with re-prompt on invalid input
- Persist data to `members.csv` on every change
- Load data from `members.csv` on startup

## Project Structure

```
src/
├── main/java/com/wcc/platform/
│   ├── Main.java                      # Entry point
│   ├── cli/
│   │   └── MemberCli.java             # Interactive menu loop
│   ├── model/
│   │   ├── Member.java                # Member data model
│   │   ├── MemberRepository.java      # Repository interface
│   │   └── CsvMemberRepository.java   # CSV-backed implementation
│   └── validation/
│       └── EmailValidator.java        # Email regex validation
└── test/java/com/wcc/platform/
    └── EmailValidatorTest.java        # Unit tests
```

## Technologies

- Java 17
- Gradle 9 (Groovy DSL)
- JUnit Jupiter 5.10.2

## How to Run

Make sure Java 17 is set as the SDK.

**Build:**
```bash
./gradlew build
```

**Run:**
```bash
./gradlew run
```

**Test:**
```bash
./gradlew test
```

## Using the Application

Once started, the application displays an interactive menu:

```
===== Member Directory =====
1. Add Member
2. View Members
3. Update Member
4. Delete Member
5. Search by Location
6. Search by Skill
7. Sort by Name
8. Sort by Join Date
0. Exit
```

Follow the on-screen prompts. Member data is automatically saved to `members.csv` after any change.

## Data Storage

Members are saved in `members.csv` in the working directory with the format:

```
name,email,location,skills,joinDate
Jane Doe,jane@example.com,London,Java|Python,2024-01-15
```

Skills are stored as pipe-separated values within the CSV field.
