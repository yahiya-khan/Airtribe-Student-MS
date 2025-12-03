package airtribe.studentms.entity;

public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private double grade = Double.NaN;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }

    public boolean isGraded() { return !Double.isNaN(grade); }

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }

    @Override
    public String toString() {
        return studentId + " -> " + courseCode + " | Grade: " +
                (isGraded() ? grade : "Not graded");
    }
}
