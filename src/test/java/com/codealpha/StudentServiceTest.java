package com.codealpha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StudentService Unit Tests")
class StudentServiceTest {

    private StudentService service;

    @BeforeEach
    void setUp() {
        service = new StudentService();
    }

    @Test
    @DisplayName("Should successfully create a Student object and verify fields")
    void testStudentCreation() {
        Student student = new Student(1, "Alice Smith", "alice@example.com", "Computer Science", 3.8);

        assertEquals(1, student.getId());
        assertEquals("Alice Smith", student.getName());
        assertEquals("alice@example.com", student.getEmail());
        assertEquals("Computer Science", student.getCourse());
        assertEquals(3.8, student.getGpa(), 0.001);
        assertTrue(student.toString().contains("Alice Smith"));
    }

    @Test
    @DisplayName("Should add a valid student and verify count")
    void testAddStudentSuccess() {
        Student student = new Student(101, "John Doe", "john@example.com", "Software Engineering", 3.75);
        service.addStudent(student);

        assertEquals(1, service.getStudentCount());
        List<Student> all = service.getAllStudents();
        assertEquals(1, all.size());
        assertEquals("John Doe", all.get(0).getName());
    }

    @Test
    @DisplayName("Should fail when adding a null student")
    void testAddNullStudentThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(null);
        });
        assertTrue(ex.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Should fail when adding student with duplicate ID")
    void testAddDuplicateIdThrowsException() {
        Student s1 = new Student(101, "Alice", "alice@example.com", "Physics", 3.5);
        Student s2 = new Student(101, "Bob", "bob@example.com", "Chemistry", 3.6);

        service.addStudent(s1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(s2);
        });
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    @DisplayName("Should find an existing student by ID")
    void testFindExistingStudent() {
        Student student = new Student(201, "Michael Scott", "michael@dundermifflin.com", "Management", 3.2);
        service.addStudent(student);

        Optional<Student> found = service.findStudentById(201);
        assertTrue(found.isPresent());
        assertEquals("Michael Scott", found.get().getName());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent student ID")
    void testFindMissingStudentReturnsEmpty() {
        Optional<Student> found = service.findStudentById(999);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should update an existing student's details successfully")
    void testUpdateStudentSuccess() {
        Student student = new Student(301, "Dwight Schrute", "dwight@dundermifflin.com", "Agriculture", 3.9);
        service.addStudent(student);

        boolean updated = service.updateStudent(301, "Dwight K Schrute", "dwight.schrute@schrutefarms.com", "Agribusiness", 4.0);

        assertTrue(updated);
        Optional<Student> opt = service.findStudentById(301);
        assertTrue(opt.isPresent());
        assertEquals("Dwight K Schrute", opt.get().getName());
        assertEquals("dwight.schrute@schrutefarms.com", opt.get().getEmail());
        assertEquals("Agribusiness", opt.get().getCourse());
        assertEquals(4.0, opt.get().getGpa(), 0.001);
    }

    @Test
    @DisplayName("Should return false when attempting to update a missing student")
    void testUpdateMissingStudentReturnsFalse() {
        boolean updated = service.updateStudent(404, "Ghost", "ghost@example.com", "Math", 3.0);
        assertFalse(updated);
    }

    @Test
    @DisplayName("Should delete an existing student")
    void testDeleteStudentSuccess() {
        Student student = new Student(501, "Jim Halpert", "jim@dundermifflin.com", "Marketing", 3.4);
        service.addStudent(student);
        assertEquals(1, service.getStudentCount());

        boolean deleted = service.deleteStudent(501);
        assertTrue(deleted);
        assertEquals(0, service.getStudentCount());
        assertTrue(service.findStudentById(501).isEmpty());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent student")
    void testDeleteMissingStudentReturnsFalse() {
        boolean deleted = service.deleteStudent(888);
        assertFalse(deleted);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("Should fail when student ID is zero or negative")
    void testInvalidIdThrowsException(int invalidId) {
        Student student = new Student(invalidId, "Test Name", "test@example.com", "CS", 3.0);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(student);
        });
        assertTrue(ex.getMessage().contains("positive integer"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "A", "John123", "NameWith#Special"})
    @DisplayName("Should fail when student name is invalid or empty")
    void testInvalidNameThrowsException(String invalidName) {
        Student student = new Student(10, invalidName, "valid@example.com", "CS", 3.5);
        assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(student);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"not-an-email", "user@", "user@domain", "user@.com", "@domain.com"})
    @DisplayName("Should fail when email format is invalid")
    void testInvalidEmailThrowsException(String invalidEmail) {
        Student student = new Student(20, "Valid Name", invalidEmail, "CS", 3.5);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(student);
        });
        assertTrue(ex.getMessage().contains("Invalid email format"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 4.01, 5.0, -4.0})
    @DisplayName("Should fail when GPA is out of range [0.0 - 4.0]")
    void testInvalidGpaThrowsException(double invalidGpa) {
        Student student = new Student(30, "Valid Name", "valid@example.com", "CS", invalidGpa);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(student);
        });
        assertTrue(ex.getMessage().contains("GPA must be between 0.0 and 4.0"));
    }

    @Test
    @DisplayName("Should fail when course is empty")
    void testEmptyCourseThrowsException() {
        Student student = new Student(40, "Valid Name", "valid@example.com", "   ", 3.5);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addStudent(student);
        });
        assertTrue(ex.getMessage().contains("Course name cannot be empty"));
    }
}
