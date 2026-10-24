package com.codealpha;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main application entry point for Student Management System.
 * Provides a menu-driven console interface for managing students.
 */
public class Main {

    private static final StudentService studentService = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Seed with sample initial records for demonstration
        seedInitialData();

        boolean running = true;
        System.out.println("==================================================");
        System.out.println("   Welcome to Student Management System (Gradle)  ");
        System.out.println("==================================================");

        while (running) {
            printMenu();
            System.out.print("Enter your choice (1-6): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleAddStudent();
                    break;
                case "2":
                    handleDisplayAllStudents();
                    break;
                case "3":
                    handleSearchStudent();
                    break;
                case "4":
                    handleUpdateStudent();
                    break;
                case "5":
                    handleDeleteStudent();
                    break;
                case "6":
                    running = false;
                    System.out.println("\nThank you for using Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice. Please select an option between 1 and 6.\n");
            }
        }
    }

    private static void seedInitialData() {
        try {
            studentService.addStudent(new Student(101, "Aparna Sharma", "aparna@example.com", "Computer Science", 3.85));
            studentService.addStudent(new Student(102, "Rahul Verma", "rahul@example.com", "Information Technology", 3.60));
            studentService.addStudent(new Student(103, "Sneha Patel", "sneha@example.com", "Data Science", 3.92));
        } catch (Exception ignored) {
        }
    }

    private static void printMenu() {
        System.out.println("--------------------------------------------------");
        System.out.println("1. Add New Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Details");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.println("--------------------------------------------------");
    }

    private static void handleAddStudent() {
        System.out.println("\n--- Add New Student ---");
        try {
            System.out.print("Enter Student ID (positive integer): ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Course: ");
            String course = scanner.nextLine().trim();

            System.out.print("Enter GPA (0.0 to 4.0): ");
            double gpa = Double.parseDouble(scanner.nextLine().trim());

            Student student = new Student(id, name, email, course, gpa);
            studentService.addStudent(student);
            System.out.println("\n[SUCCESS] Student added successfully!\n");
        } catch (NumberFormatException e) {
            System.out.println("\n[ERROR] Invalid number format entered. ID and GPA must be valid numbers.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("\n[ERROR] Validation failed: " + e.getMessage() + "\n");
        }
    }

    private static void handleDisplayAllStudents() {
        System.out.println("\n--- All Registered Students ---");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in the system.\n");
            return;
        }

        System.out.println("+-----+----------------------+--------------------------+-----------------------+------+");
        System.out.printf("| %-3s | %-20s | %-24s | %-21s | %-4s |\n", "ID", "Name", "Email", "Course", "GPA");
        System.out.println("+-----+----------------------+--------------------------+-----------------------+------+");

        for (Student s : students) {
            System.out.printf("| %-3d | %-20s | %-24s | %-21s | %-4.2f |\n",
                    s.getId(), s.getName(), s.getEmail(), s.getCourse(), s.getGpa());
        }
        System.out.println("+-----+----------------------+--------------------------+-----------------------+------+");
        System.out.println("Total Students: " + students.size() + "\n");
    }

    private static void handleSearchStudent() {
        System.out.println("\n--- Search Student by ID ---");
        try {
            System.out.print("Enter Student ID to search: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Optional<Student> studentOpt = studentService.findStudentById(id);
            if (studentOpt.isPresent()) {
                Student s = studentOpt.get();
                System.out.println("\n[FOUND] Student Details:");
                System.out.println("  ID     : " + s.getId());
                System.out.println("  Name   : " + s.getName());
                System.out.println("  Email  : " + s.getEmail());
                System.out.println("  Course : " + s.getCourse());
                System.out.printf("  GPA    : %.2f\n\n", s.getGpa());
            } else {
                System.out.println("\n[INFO] No student found with ID: " + id + "\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n[ERROR] Invalid input. Student ID must be an integer.\n");
        }
    }

    private static void handleUpdateStudent() {
        System.out.println("\n--- Update Student Details ---");
        try {
            System.out.print("Enter Student ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Optional<Student> studentOpt = studentService.findStudentById(id);
            if (studentOpt.isEmpty()) {
                System.out.println("\n[ERROR] Cannot update. No student found with ID: " + id + "\n");
                return;
            }

            Student existing = studentOpt.get();
            System.out.println("Found: " + existing.getName() + " (" + existing.getCourse() + ")");

            System.out.print("Enter New Name (leave blank to keep current '" + existing.getName() + "'): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = existing.getName();
            }

            System.out.print("Enter New Email (leave blank to keep current '" + existing.getEmail() + "'): ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                email = existing.getEmail();
            }

            System.out.print("Enter New Course (leave blank to keep current '" + existing.getCourse() + "'): ");
            String course = scanner.nextLine().trim();
            if (course.isEmpty()) {
                course = existing.getCourse();
            }

            System.out.print("Enter New GPA (leave blank to keep current '" + existing.getGpa() + "'): ");
            String gpaInput = scanner.nextLine().trim();
            double gpa = gpaInput.isEmpty() ? existing.getGpa() : Double.parseDouble(gpaInput);

            boolean updated = studentService.updateStudent(id, name, email, course, gpa);
            if (updated) {
                System.out.println("\n[SUCCESS] Student details updated successfully!\n");
            } else {
                System.out.println("\n[ERROR] Failed to update student.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n[ERROR] Invalid numeric format entered.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("\n[ERROR] Validation failed: " + e.getMessage() + "\n");
        }
    }

    private static void handleDeleteStudent() {
        System.out.println("\n--- Delete Student ---");
        try {
            System.out.print("Enter Student ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean deleted = studentService.deleteStudent(id);
            if (deleted) {
                System.out.println("\n[SUCCESS] Student with ID " + id + " has been deleted.\n");
            } else {
                System.out.println("\n[ERROR] Cannot delete. No student found with ID: " + id + "\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n[ERROR] Invalid input. Student ID must be an integer.\n");
        }
    }
}
