package com.codealpha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Service class for managing in-memory Student entities.
 * Handles CRUD operations and validation logic.
 */
public class StudentService {

    private final Map<Integer, Student> students = new LinkedHashMap<>();

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z\\s]{2,50}$"
    );

    /**
     * Adds a new student after validating their information.
     *
     * @param student The student to add.
     * @throws IllegalArgumentException if validation fails or ID already exists.
     */
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student object cannot be null.");
        }
        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("A student with ID " + student.getId() + " already exists.");
        }
        validateStudent(student);
        students.put(student.getId(), student);
    }

    /**
     * Returns a list of all registered students.
     *
     * @return List of all students.
     */
    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(new ArrayList<>(students.values()));
    }

    /**
     * Searches for a student by their unique ID.
     *
     * @param id The student ID.
     * @return An Optional containing the student if found, or empty otherwise.
     */
    public Optional<Student> findStudentById(int id) {
        return Optional.ofNullable(students.get(id));
    }

    /**
     * Updates an existing student's details after validating the new data.
     *
     * @param id     The student ID to update.
     * @param name   The new name.
     * @param email  The new email.
     * @param course The new course.
     * @param gpa    The new GPA.
     * @return true if updated successfully, false if student not found.
     * @throws IllegalArgumentException if validation fails.
     */
    public boolean updateStudent(int id, String name, String email, String course, double gpa) {
        Student existing = students.get(id);
        if (existing == null) {
            return false;
        }

        // Validate individual fields before modifying
        validateDetails(name, email, course, gpa);

        existing.setName(name.trim());
        existing.setEmail(email.trim());
        existing.setCourse(course.trim());
        existing.setGpa(gpa);
        return true;
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id The student ID.
     * @return true if removed, false if student did not exist.
     */
    public boolean deleteStudent(int id) {
        return students.remove(id) != null;
    }

    /**
     * Returns the total number of students.
     *
     * @return count of students.
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Clears all students from the in-memory store.
     */
    public void clear() {
        students.clear();
    }

    /**
     * Validates all fields of a student object.
     *
     * @param student The student to validate.
     * @throws IllegalArgumentException if any field is invalid.
     */
    public void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (student.getId() <= 0) {
            throw new IllegalArgumentException("Student ID must be a positive integer greater than 0.");
        }
        validateDetails(student.getName(), student.getEmail(), student.getCourse(), student.getGpa());
    }

    /**
     * Validates name, email, course, and GPA values.
     */
    public void validateDetails(String name, String email, String course, double gpa) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            throw new IllegalArgumentException("Student name must contain only letters and spaces (2 to 50 characters).");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Invalid email format. Example: student@example.com");
        }
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty.");
        }
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
        }
    }
}
