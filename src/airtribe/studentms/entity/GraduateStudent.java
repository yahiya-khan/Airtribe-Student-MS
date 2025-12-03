package airtribe.studentms.entity;

import java.time.LocalDate;

public class GraduateStudent extends Student {
    private String thesisTopic;

    public GraduateStudent(String id, String name, LocalDate dob) {
        super(id, name, dob);
        setProgram("GRAD");
    }

    public String getThesisTopic() { return thesisTopic; }
    public void setThesisTopic(String thesisTopic) { this.thesisTopic = thesisTopic; }

    @Override
    public String toString() {
        return super.toString() + (thesisTopic != null ? " | Thesis: " + thesisTopic : "");
    }
}
