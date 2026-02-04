# How to Submit Your Project

This guide walks you through submitting your completed Java project to the bootcamp repository.

## ✅ Before You Submit - Checklist

- [ ] Project is functional
- [ ] Project includes a README.md
- [ ] No sensitive information (passwords, API keys) in the code

## 📁 Required Directory Structure

Your submission must follow this structure:

```
participants/<your-name>/project/
├── README.md                 # Your project documentation (REQUIRED)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/wcc/<your-project>/
│       │       └── *.java    # Your Java source files
│       └── resources/        # Configuration files, if any
├── build.gradle              # Gradle build file (if applicable)
├── settings.gradle           # Gradle settings (if applicable)
└── gradlew                   # Gradle wrapper (if applicable)
```

## 📝 README Requirements

Your project's README.md must include:

### 1. Project Title and Description
```markdown
# Project Name

A brief description of what your project does and which WCC feature it addresses.
```

### 2. Features
List the main features you've implemented:

### 3. Technologies Used
```markdown
## Technologies
- Java 17
- Spring Boot (if applicable)
- Any other libraries or frameworks
```

### 4. How to Run
- Provide clear instructions on how to set up and run your project.

## 🚀 Submission Process

### Step 1: Prepare Your Project

1. **Create your participant folder** (if not already created):
   ```bash
   mkdir -p participants/<your-name>/project
   ```

2. **Copy/Move your project files** into this directory

3. **Create your README.md** with all required sections

4. **Test that everything works**:
   ```bash
   cd participants/<your-name>/project
   ./gradlew build
   ./gradlew run
   ```

### Step 2: Commit Your Changes

```bash
# Make sure you're in the root of the repository
cd /path/to/java-bootcamp

# Check current status
git status

# Add your project files
git add participants/<your-name>/

# Commit with a descriptive message
git commit -m "Add <project-name> by <your-name>"

# Example:
# git commit -m "Add Event Management System by Jane Doe"
```

### Step 3: Push to Your Fork

```bash
# Push to your fork on GitHub
git push origin main

# Or if you're working on a branch:
git push origin <your-branch-name>
```

### Step 4: Create a Pull Request

1. **Go to your fork** on GitHub (https://github.com/<your-username>/java-bootcamp)

2. **Click "Contribute"** or "Pull Request" button

3. **Click "Open Pull Request"**

4. **Fill in the PR template:**
    - **Title**: `[Submission] <Your Name> - <Project Name>`
    - **Description**: Include:
        - Brief project description
        - Which project idea you chose (or if it's custom)
        - Complexity level (Basic/Intermediate/Advanced)
        - Any challenges you faced
        - What you learned

## 📊 Project Levels

### Basic Projects
- Core Java concepts
- Simple command-line applications
- Basic file handling
- Fundamental OOP

### Intermediate Projects
- Spring Boot applications
- REST APIs
- Database integration
- Unit testing

### Advanced Projects
- Microservices architecture
- Complex data processing
- Advanced design patterns
- Integration with external services

## ❓ Common Questions

### Q: Can I submit multiple projects?
**A:** Yes! Create separate folders for each project under your participant directory:
```
participants/<your-name>/
├── project-1/
├── project-2/
└── project-3/
```

### Q: Can I update my project after it's merged?
**A:** Yes! You can submit improvements via new Pull Requests.

### Q: What if I'm not finished but want feedback?
**A:** Create a Draft Pull Request and mark it as "Work in Progress (WIP)" in the title.

### Q: My build fails. What should I do?
**A:**
- Check the error messages in your terminal
- Ensure all dependencies are properly configured
- Verify your Gradle configuration
- Ask for help in the GitHub Issues

### Q: Can I collaborate with others?
**A:** Yes! Just make sure all contributors are credited in the README and PR description.

---

**Good luck with your submission!** 🎉 We're excited to see what you've built!