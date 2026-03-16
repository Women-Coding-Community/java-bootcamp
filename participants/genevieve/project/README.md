# 🤖 Simple Chat Bot (Java)
A console-based digital assistant built as part of the JetBrains Academy Java Track. This project demonstrates basic Java syntax, handling user input, mathematical logic, and control flow.

## 🚀 Features
* **Personalized Greeting:** The bot introduces itself and asks for the user's name.
* **Age Prediction:** Uses a remainder-based mathematical algorithm to "guess" your age.
* **Interactive Counter:** Demonstrates loop structures by counting to any number provided by the user.
* **Knowledge Check:** A built-in multiple-choice quiz to test programming fundamentals.

## 🛠 Technologies & Environment
* **Language:** Java 23
* **SDK:** Amazon Corretto 23 (aarch64)
* **IDE:** IntelliJ IDEA
* **Build System:** Gradle (Hyperskill integration)

## 📖 How to Run
1. **Clone the repository:**
```bash
   git clone https://github.com/genevieve-richards/java-bootcamp
```
2. **Open in IntelliJ IDEA:**
   Ensure your Project Structure is set to **SDK 23** and **Language Level 23**.
3. **Execute:**
   Run the `SimpleBot.main()` method located in `src/bot/SimpleBot.java`.

## 🧠 Logic Preview
The age guessing feature uses the following formula:

age = (remainder3 × 70 + remainder5 × 21 + remainder7 × 15) mod 105

## ✅ Unit Tests
Unit testing is not implemented in this version. Planned for future iterations.

## 📝 Setup Requirements
* **JDK 23** (Required for compatibility with project metadata)
* IntelliJ IDEA (Latest version recommended)