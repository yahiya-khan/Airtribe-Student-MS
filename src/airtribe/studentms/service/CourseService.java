package airtribe.studentms.service;

import airtribe.studentms.entity.Course;
import java.util.*;

public class CourseService {
    private final Map<String, Course> courses = new LinkedHashMap<>();

    public void addCourse(Course c) {
        courses.put(c.getCode(), c);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
}
