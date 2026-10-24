# CodeAlpha DevOps Internship - Task 3
# Java Application Using Gradle: Student Management System

[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge&logo=gradle)](https://gradle.org/)
[![Java](https://img.shields.io/badge/Java-17%20%7C%2021-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![JUnit 5](https://img.shields.io/badge/Testing-JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![Git](https://img.shields.io/badge/VCS-Git%20%26%20GitHub-F05032?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com/)

---

## 1. Project Overview
The **Student Management System** is a modular, object-oriented console application developed in Java and managed via **Gradle**. This project fulfills **Task 3 of the CodeAlpha DevOps Internship**, highlighting modern build automation, continuous testing, dependency management, and reproducible packaging without requiring manual Gradle installation on client machines.

---

## 2. Objectives
- **Build Automation**: Automate application compilation, dependency resolution, testing, and packaging using Gradle.
- **Portability via Gradle Wrapper**: Ensure cross-platform build consistency using `gradlew` and `gradlew.bat`.
- **Quality Assurance**: Implement comprehensive unit testing with **JUnit 5** to validate CRUD operations and edge cases.
- **Object-Oriented Design**: Maintain clean separation between the entity layer (`Student`), service business logic (`StudentService`), and presentation layer (`Main`).
- **DevOps Readiness**: Structure source code and build descriptors following industry standards for CI/CD pipeline integration and GitHub collaboration.

---

## 3. Features
- **Add New Student**: Register students with automatic ID duplication check and strict input validation.
- **Display All Students**: View formatted tabular records with ID, Name, Email, Course, and GPA.
- **Search Student by ID**: Fast retrieval of specific student records using in-memory indexed mapping.
- **Update Student Details**: Safely modify student attributes with validation.
- **Delete Student**: Remove student records by ID with status confirmation.
- **Input & Business Validation**:
  - Positive integer validation for IDs.
  - Name validation (letters and spaces only, 2–50 characters).
  - RFC-compliant email address validation.
  - GPA range validation (between `0.0` and `4.0`).
  - Non-empty course names.
- **Graceful Error Handling**: Descriptive user-facing messages preventing crashes from invalid inputs.

---

## 4. Technologies Used
| Technology | Version / Description |
| :--- | :--- |
| **Programming Language** | Java (JDK 17 LTS / JDK 21 LTS compatible) |
| **Build & Dependency Tool** | Gradle 8.8 (using Gradle Wrapper) |
| **Testing Framework** | JUnit 5 (JUnit Jupiter 5.10.2 + BOM) |
| **Version Control** | Git & GitHub |
| **IDE / Editor** | Visual Studio Code / IntelliJ IDEA / Terminal |
| **Operating System** | Windows / Linux / macOS cross-compatible |

---

## 5. Prerequisites
1. **Java Development Kit (JDK)**: JDK 17 or higher (JDK 21 LTS recommended).
   - Verify via PowerShell/Terminal:
     ```powershell
     java -version
     ```
2. **Git**: Installed and configured for version control.
   - Verify:
     ```powershell
     git --version
     ```
3. **No standalone Gradle required**: The bundled Gradle Wrapper (`gradlew.bat` / `gradlew`) automatically downloads and manages the correct Gradle distribution.

---

## 6. Project Structure
```text
student-management-gradle/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/codealpha/
│   │           ├── Main.java              # Console user interface & application loop
│   │           ├── Student.java           # Student model / entity POJO
│   │           └── StudentService.java    # Business logic, in-memory store & validation
│   └── test/
│       └── java/
│           └── com/codealpha/
│               └── StudentServiceTest.java # JUnit 5 automated test suite
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar             # Executable wrapper binary
│       └── gradle-wrapper.properties      # Wrapper distribution metadata (Gradle 8.8)
├── build.gradle                           # Core Gradle build script (plugins, dependencies, tasks)
├── settings.gradle                        # Root project naming descriptor
├── gradlew                                # Linux/macOS wrapper shell script
├── gradlew.bat                            # Windows wrapper batch script
├── .gitignore                             # Ignored build artifacts & IDE metadata
└── README.md                              # Complete project documentation
```

---

## 7. Installation and Execution Steps

### Step 1: Clone or Open the Repository
```powershell
git clone https://github.com/aparnaabi248-git/CodeAlpha_JavaGradle.git
cd CodeAlpha_JavaGradle
```

### Step 2: Build the Application
On Windows PowerShell:
```powershell
.\gradlew.bat build
```
*(On Linux/macOS, use `./gradlew build`)*

### Step 3: Run the Application
```powershell
.\gradlew.bat run
```

---

## 8. Gradle Commands Explained

| Command (Windows) | Action & DevOps Purpose |
| :--- | :--- |
| `.\gradlew.bat clean` | Deletes the `build/` directory, wiping all compiled `.class` files, reports, and generated JARs for a fresh clean state. |
| `.\gradlew.bat build` | Assembles the project, compiles source and test classes, executes automated unit tests, and packages the binary into a JAR. |
| `.\gradlew.bat test` | Compiles test sources and runs all JUnit 5 test suites, producing both console test summaries and rich HTML reports. |
| `.\gradlew.bat run` | Compiles and executes the application main class (`com.codealpha.Main`) with interactive standard console input. |

---

## 9. Testing Instructions & Test Report Verification

### Running Automated Tests
Run all unit tests through the wrapper:
```powershell
.\gradlew.bat test
```

### Expected Test Output
```text
> Task :compileJava
> Task :classes
> Task :compileTestJava
> Task :testClasses
> Task :test

StudentService Unit Tests > Should fail when student ID is zero or negative > [1] 0 PASSED
StudentService Unit Tests > Should fail when student ID is zero or negative > [2] -1 PASSED
StudentService Unit Tests > Should fail when student ID is zero or negative > [3] -100 PASSED
StudentService Unit Tests > Should return false when attempting to update a missing student PASSED
StudentService Unit Tests > Should update an existing student's details successfully PASSED
StudentService Unit Tests > Should fail when email format is invalid > [1] not-an-email PASSED
StudentService Unit Tests > Should delete an existing student PASSED
StudentService Unit Tests > Should fail when student name is invalid or empty PASSED
StudentService Unit Tests > Should return empty optional for non-existent student ID PASSED
StudentService Unit Tests > Should fail when GPA is out of range [0.0 - 4.0] PASSED
StudentService Unit Tests > Should successfully create a Student object and verify fields PASSED
StudentService Unit Tests > Should fail when course is empty PASSED
StudentService Unit Tests > Should fail when adding student with duplicate ID PASSED
StudentService Unit Tests > Should add a valid student and verify count PASSED
StudentService Unit Tests > Should find an existing student by ID PASSED

BUILD SUCCESSFUL in 10s
```

### Inspecting Build and Test Artifacts
- **Compiled Classes**: Stored under `build/classes/java/main/` and `build/classes/java/test/`.
- **Packaged Executable JAR**: Located at `build/libs/student-management-gradle-1.0.0.jar`.
- **HTML Test Report**: Generated at `build/reports/tests/test/index.html`.
  
To open and inspect the interactive test report in your browser on Windows:
```powershell
Start-Process "build\reports\tests\test\index.html"
```
The report presents:
- Total test count, failure count, and execution duration.
- 100% success rate indicator.
- Individual test case results, parameterized inputs, and class breakdowns.

---

## 10. Sample Input and Output

### Interactive Menu
```text
==================================================
   Welcome to Student Management System (Gradle)  
==================================================
--------------------------------------------------
1. Add New Student
2. Display All Students
3. Search Student by ID
4. Update Student Details
5. Delete Student
6. Exit
--------------------------------------------------
Enter your choice (1-6): 2
```

### Display All Students (Tabular Output)
```text
--- All Registered Students ---
+-----+----------------------+--------------------------+-----------------------+------+
| ID  | Name                 | Email                    | Course                | GPA  |
+-----+----------------------+--------------------------+-----------------------+------+
| 101 | Aparna Sharma        | aparna@example.com       | Computer Science      | 3.85 |
| 102 | Rahul Verma          | rahul@example.com        | Information Technology | 3.60 |
| 103 | Sneha Patel          | sneha@example.com        | Data Science          | 3.92 |
+-----+----------------------+--------------------------+-----------------------+------+
Total Students: 3
```

### Add Student Validation Example
```text
--- Add New Student ---
Enter Student ID (positive integer): 104
Enter Full Name: Anita Roy
Enter Email Address: invalid-email
Enter Course: Electrical Engineering
Enter GPA (0.0 to 4.0): 3.75

[ERROR] Validation failed: Invalid email format. Example: student@example.com
```

---

## 11. Screenshots to Capture for Submission
For your internship submission report, take screenshots of the following steps:
1. **Java and Git Verification**: `java -version` and `git --version` in terminal.
2. **Project Directory Structure**: VS Code file explorer displaying the directory tree with `build.gradle`, wrapper files, and source packages.
3. **Clean and Build Execution**: Output of `.\gradlew.bat clean` and `.\gradlew.bat build`.
4. **Test Execution**: Output of `.\gradlew.bat test` showing all passing unit tests.
5. **Interactive Console Run**: Running `.\gradlew.bat run`, displaying students, adding a new student, and input validation.
6. **HTML Test Report in Browser**: Browser view of `build/reports/tests/test/index.html` showing 100% successful test status.
7. **GitHub Repository**: The pushed repository on GitHub (`https://github.com/aparnaabi248-git/CodeAlpha_JavaGradle`).

---

## 12. GitHub Repository Push Instructions
Follow these steps to push your local code to your GitHub repository:

```powershell
# 1. Initialize local Git repository (if not already initialized)
git init

# 2. Stage all project files (wrapper binaries, sources, build scripts, docs)
git add .

# 3. Commit staged files
git commit -m "feat: complete student management system with gradle build automation and junit5 tests"

# 4. Set default branch to main
git branch -M main

# 5. Add remote origin (ensure you created the repo on GitHub first)
git remote add origin https://github.com/aparnaabi248-git/CodeAlpha_JavaGradle.git

# 6. Push to GitHub
git push -u origin main
```

> **Note**: Create the repository `CodeAlpha_JavaGradle` on GitHub (`https://github.com/new`) under your account `aparnaabi248-git` without checking "Add a README" or ".gitignore" before running the push command.

---

## 13. Conclusion
This project successfully showcases the fundamentals of Java software engineering combined with modern DevOps build automation practices:
- Gradle handles reproducible builds, dependency retrieval, and execution via the portable Gradle Wrapper.
- Rigorous JUnit 5 automated test execution ensures code reliability and continuous regression detection.
- Clean Git repository setup and professional documentation prepare the codebase for automated CI/CD pipelines in enterprise DevOps workflows.
