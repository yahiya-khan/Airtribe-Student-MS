package airtribe.studentms.entity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

public class Student extends Person {
    private final Map<String, Double> grades = new HashMap<>();
    private String program = "UNDERGRAD";

    public Student(String id, String name, LocalDate dob) {
        super(id, name, dob);
    }

    public void setProgram(String program) { this.program = program; }
    public String getProgram() { return program; }

    public void setGrade(String courseCode, double grade) {
        grades.put(courseCode, grade);
    }

    public Map<String, Double> getGrades() {
        return Collections.unmodifiableMap(grades);
    }

    public OptionalDouble averageGrade() {
        return grades.values().stream().mapToDouble(Double::doubleValue).average();
    }

    @Override
    public String toString() {
        return String.format("%s | Program: %s | Avg: %s", super.toString(), program,
                averageGrade().isPresent() ? String.format("%.2f", averageGrade().getAsDouble()) : "N/A");
    }
}
