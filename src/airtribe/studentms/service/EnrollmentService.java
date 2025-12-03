package airtribe.studentms.service;

import airtribe.studentms.entity.Enrollment;
import airtribe.studentms.entity.Student;

import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService ss, CourseService cs) {
        this.studentService = ss;
        this.courseService = cs;
    }

    public void enrollStudent(String studentId, String courseCode) {
        // simple validation
        if (studentService.getStudent(studentId) == null) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        // courseService.getAllCourses doesn't give code lookup in this minimal version; skip heavy validation
        enrollments.add(new Enrollment(studentId, courseCode));
    }

    public void recordGrade(String studentId, String courseCode, double grade) {
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode)) {
                e.setGrade(grade);
                Student s = studentService.getStudent(studentId);
                if (s != null) s.setGrade(courseCode, grade);
                return;
            }
        }
        throw new IllegalArgumentException("Enrollment not found for student " + studentId + " in course " + courseCode);
    }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    /**
     * Calculate average grade for a student (grades recorded in enrollments).
     * Returns 0.0 if no graded enrollments found.
     */
    public double calculateAverage(String studentId) {
        List<Double> grades = enrollments.stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.isGraded())
                .map(Enrollment::getGrade)
                .collect(Collectors.toList());
        if (grades.isEmpty()) return 0.0;
        double sum = grades.stream().mapToDouble(Double::doubleValue).sum();
        return sum / grades.size();
    }

    public List<String> listCoursesForStudent(String studentId) {
        return enrollments.stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .map(Enrollment::getCourseCode)
                .collect(Collectors.toList());
    }
}
