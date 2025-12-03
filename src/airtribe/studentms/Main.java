package airtribe.studentms;

import airtribe.studentms.entity.Student;
import airtribe.studentms.entity.Course;
import airtribe.studentms.service.StudentService;
import airtribe.studentms.service.CourseService;
import airtribe.studentms.service.EnrollmentService;
import airtribe.studentms.util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * menu for student management system
 * Interactive CLI for Student Management System.
 * Options:
 * 1 - Add Student
 * 2 - Add Course
 * 3 - Enroll Student
 * 4 - Record Grade
 * 5 - List Students
 * 6 - List Courses
 * 7 - List Enrollments
 * 8 - Student Average
 * 0 - Exit
 */
public class Main {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("Welcome to Airtribe Student Management System");

        // sample seed data (optional)
        seedSampleData();

         try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Choose option: ");
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        handleAddStudent(scanner);
                        break;
                    case "2":
                        handleAddCourse(scanner);
                        break;
                    case "3":
                        handleEnroll(scanner);
                        break;
                    case "4":
                        handleRecordGrade(scanner);
                        break;
                    case "5":
                        listStudents();
                        break;
                    case "6":
                        listCourses();
                        break;
                    case "7":
                        listEnrollments();
                        break;
                    case "8":
                        handleStudentAverage(scanner);
                        break;
                    case "0":
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                        break;
                    default:
                        System.out.println("Unknown option — try again.");
                }
                System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("=== MENU ===");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Enroll Student in Course");
        System.out.println("4. Record Grade");
        System.out.println("5. List Students");
        System.out.println("6. List Courses");
        System.out.println("7. List Enrollments");
        System.out.println("8. Student Average");
        System.out.println("0. Exit");
    }

    private static void handleAddStudent(Scanner scanner) {
        System.out.println("--- Add Student ---");
        System.out.print("Enter student id (e.g. S1001): ");
        String id = scanner.nextLine().trim();
        if (!InputValidator.isValidId(id)) {
            System.out.println("Invalid id format.");
            return;
        }

        System.out.print("Enter full name: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dobText = scanner.nextLine().trim();
        try {
            LocalDate dob = LocalDate.parse(dobText);
            Student s = new Student(id, name, dob);
            studentService.addStudent(s);
            System.out.println("Student added: " + s);
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        } catch (Exception ex) {
            System.out.println("Failed to add student: " + ex.getMessage());
        }
    }

    private static void handleAddCourse(Scanner scanner) {
        System.out.println("--- Add Course ---");
        System.out.print("Enter course code (e.g. CS101): ");
        String code = scanner.nextLine().trim();
        if (code.isBlank()) { System.out.println("Course code required."); return; }

        System.out.print("Enter course title: ");
        String title = scanner.nextLine().trim();
        if (title.isBlank()) { System.out.println("Course title required."); return; }

        System.out.print("Enter credits (integer): ");
        String creditsText = scanner.nextLine().trim();
        try {
            int credits = Integer.parseInt(creditsText);
            Course c = new Course(code, title, credits);
            courseService.addCourse(c);
            System.out.println("Course added: " + c);
        } catch (NumberFormatException ex) {
            System.out.println("Credits must be an integer.");
        }
    }

    private static void handleEnroll(Scanner scanner) {
        System.out.println("--- Enroll Student ---");
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        System.out.print("Course code: ");
        String code = scanner.nextLine().trim();
        try {
            enrollmentService.enrollStudent(sid, code);
            System.out.printf("Enrolled %s -> %s%n", sid, code);
        } catch (Exception ex) {
            System.out.println("Enroll failed: " + ex.getMessage());
        }
    }

    private static void handleRecordGrade(Scanner scanner) {
        System.out.println("--- Record Grade ---");
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        System.out.print("Course code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Grade (0-100): ");
        String gText = scanner.nextLine().trim();
        try {
            double grade = Double.parseDouble(gText);
            enrollmentService.recordGrade(sid, code, grade);
            System.out.println("Grade recorded.");
        } catch (NumberFormatException ex) {
            System.out.println("Grade must be a number.");
        } catch (Exception ex) {
            System.out.println("Record grade failed: " + ex.getMessage());
        }
    }

    private static void listStudents() {
        System.out.println("--- Students ---");
        studentService.getAllStudents().forEach(s -> System.out.println(" - " + s));
    }

    private static void listCourses() {
        System.out.println("--- Courses ---");
        courseService.getAllCourses().forEach(c -> System.out.println(" - " + c));
    }

    private static void listEnrollments() {
        System.out.println("--- Enrollments ---");
        enrollmentService.getEnrollments().forEach(e -> System.out.println(" - " + e));
    }

     private static void handleStudentAverage(Scanner scanner) {
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        double avg = enrollmentService.calculateAverage(sid);
        System.out.printf("Average for %s: %.2f%n", sid, avg);
    }

    private static void seedSampleData() {
        // optional seed to get started quickly
        try {
            studentService.addStudent(new Student("S1001", "MR.khan", LocalDate.of(2003, 5, 17)));
            studentService.addStudent(new Student("S1002", "Tushar ", LocalDate.of(2002, 11, 12)));
            courseService.addCourse(new Course("CS101", "Java Programming", 4));
            courseService.addCourse(new Course("MATH201", "AI Engineering", 3));
            enrollmentService.enrollStudent("S1001", "CS101");
            enrollmentService.recordGrade("S1001", "CS101", 85.0);
        } catch (Exception ignored) {}
    }
}
