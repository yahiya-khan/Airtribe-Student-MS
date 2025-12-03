package airtribe.studentms.service;

import airtribe.studentms.entity.Student;
import java.util.*;

public class StudentService {
    private final Map<String, Student> students = new LinkedHashMap<>();

    public void addStudent(Student s) {
        students.put(s.getId(), s);
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
}
